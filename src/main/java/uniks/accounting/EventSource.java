package uniks.accounting;

import org.fulib.yaml.Yamler;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class EventSource
{
   public static final String EVENT_KEY = ".eventKey";
   public static final String EVENT_NUMBER = ".eventNumber";
   public static final String OPCODE = "opcode";
   private Yamler yamler = new Yamler();

   private LinkedHashMap<String,Integer> keyNumMap = new LinkedHashMap<>();
   private TreeMap<Integer, LinkedHashMap<String, String>> numEventMap = new TreeMap<>();

   private int eventNumber = 0;

   public int getEventNumber()
   {
      return eventNumber;
   }

   public SortedMap<Integer, LinkedHashMap<String, String>> pull(int lastKnownNumber)
   {
      SortedMap<Integer, LinkedHashMap<String, String>> tailMap = numEventMap.tailMap(lastKnownNumber + 1);
      return tailMap;
   }

   public SortedMap<Integer, LinkedHashMap<String, String>> pull(int lastKnownNumber, String... relevantOpCodes)
   {
      return pull(lastKnownNumber, e -> filterRelevantOpCodes(e, Arrays.asList(relevantOpCodes)));
   }

   private Boolean filterRelevantOpCodes(Map.Entry<Integer, LinkedHashMap<String, String>> e, List<String> relevantOpCodes)
   {
      LinkedHashMap<String, String> map = e.getValue();
      return relevantOpCodes.contains(map.get(OPCODE));
   }

   public SortedMap<Integer, LinkedHashMap<String, String>> pull(int lastKnownNumber, Function<Map.Entry<Integer, LinkedHashMap<String, String>>,Boolean> filterOp)
   {
      SortedMap<Integer, LinkedHashMap<String, String>> tailMap = numEventMap.tailMap(lastKnownNumber + 1);
      TreeMap<Integer, LinkedHashMap<String, String>> resultMap = new TreeMap<>();
      for (Map.Entry<Integer, LinkedHashMap<String, String>> entry : tailMap.entrySet())
      {
         boolean result = filterOp.apply(entry);

         if (result)
         {
            resultMap.put(entry.getKey(), entry.getValue());
         }
      }

      return resultMap;
   }

   public EventSource append(LinkedHashMap<String, String> event)
   {
      eventNumber++;
      String numberString = "" + eventNumber;

      event.put(EVENT_NUMBER, numberString);

      String key = event.get(EVENT_KEY);
      if (key != null)
      {
         Integer oldNum = keyNumMap.get(key);
         if (oldNum != null) numEventMap.remove(oldNum);
      }

      keyNumMap.put(key, eventNumber);
      numEventMap.put(eventNumber, event);

      for (Consumer<LinkedHashMap<String, String>> listener : eventListeners)
      {
         listener.accept(event);
      }


      return this;
   }


   public EventSource append(StringBuilder buf)
   {
      ArrayList<LinkedHashMap<String, String>> list = yamler.decodeList(buf.toString());

      for (LinkedHashMap<String, String> event : list)
      {
         append(event);
      }

      return this;
   }

   public String encodeYaml()
   {
      return encodeYaml(numEventMap);
   }

   public static String encodeYaml(SortedMap<Integer, LinkedHashMap<String, String>> eventMap)
   {
      StringBuffer buf = new StringBuffer();

      for (Map.Entry<Integer, LinkedHashMap<String, String>> entry : eventMap.entrySet())
      {
         LinkedHashMap<String, String> event = entry.getValue();

         String oneObj = encodeYaml(event);

         buf.append(oneObj);
      }

      return buf.toString();
   }

   public static String encodeYaml(LinkedHashMap<String, String> event)
   {
      StringBuffer buf = new StringBuffer();

      String prefix = "- ";
      for (Map.Entry<String, String> keyValuePair : event.entrySet())
      {
         buf.append(prefix).append(keyValuePair.getKey()).append(": ")
               .append(Yamler.encapsulate(keyValuePair.getValue())).append("\n");
         prefix = "  ";
      }
      buf.append("\n");

      return buf.toString();
   }

   private ArrayList<Consumer<LinkedHashMap<String,String>>> eventListeners = new ArrayList<>();

   public void addEventListener(Consumer<LinkedHashMap<String,String>> listener)
   {
      eventListeners.add(listener);
   }
}
