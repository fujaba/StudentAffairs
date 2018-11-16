package uniks.accounting;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.fulib.yaml.Yamler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SEGroupService implements Runnable
{

   public static final String EVENT_TYPE = "eventType";
   public static final String UNIKS_FB_16_SE_GROUP = "/uniks/fb16/seGroup";
   public static final String ANSWER_TOPIC = "answerTopic";
   public static final String LAST_KNOWN_NUMBER = "lastKnownNumber";
   public static final String RELEVANT_EVENT_TYPES = "relevantEventTypes";
   public static final String PULL = "pull";


   private final MqttCallback mqttCallback = new MqttCallback()
   {
      @Override
      public void connectionLost(Throwable cause)
      {

      }

      @Override
      public void messageArrived(String topic, MqttMessage message) throws Exception
      {
         String payload = new String(message.getPayload());
         handleMqttMessage(payload);
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken token)
      {

      }
   };

   private LinkedBlockingQueue<String> inbox = new LinkedBlockingQueue<>();
   private SEGroupBuilder gb = null;
   private MqttClient mqttClient;

   public void run()
   {
      // load data
      gb = new SEGroupBuilder();
      gb.getEventSource().addEventListener(e -> handleMyOwnEvent(e));

      // connect to mqtt broker
      String broker       = "tcp://localhost:1883";
      String clientId     = "de.uniks.SEGroup";
      MemoryPersistence persistence = new MemoryPersistence();

      try {
         mqttClient = new MqttClient(broker, clientId, persistence);
         MqttConnectOptions connOpts = new MqttConnectOptions();
         connOpts.setCleanSession(true);
         mqttClient.connect(connOpts);
         System.out.println("SEGroup Connected");

         mqttClient.setCallback(mqttCallback);
         mqttClient.subscribe(UNIKS_FB_16_SE_GROUP);

         mqttClient.publish(UNIKS_FB_16_SE_GROUP, "- SEGroup: running".getBytes(), 2, false);

         while (true)
         {
            try
            {
               String take = inbox.take();

               handleMqttMessage(take);
            }
            catch (InterruptedException e)
            {
               Logger.getGlobal().log(Level.WARNING, "waiting for message has been interrupted", e);
            }

         }

      } catch(MqttException me) {
         System.out.println("reason "+me.getReasonCode());
         System.out.println("msg "+me.getMessage());
         System.out.println("loc "+me.getLocalizedMessage());
         System.out.println("cause "+me.getCause());
         System.out.println("excep "+me);
         me.printStackTrace();
      }
   }



   private void handleMyOwnEvent(LinkedHashMap<String, String> e)
   {
      String yaml = EventSource.encodeYaml(e);
      // publish to interested
      for (Map.Entry<String,String> reportTopic : eventReportTopics.entrySet())
      {
         String topic = reportTopic.getKey();
         String eventTypes = reportTopic.getValue();
         if (eventTypes.equals("*") || eventTypes.indexOf(e.get(EVENT_TYPE)) >= 0)
         {
            try
            {
               mqttClient.publish(topic, yaml.getBytes(), 2, false);
            }
            catch (MqttException e1)
            {
               e1.printStackTrace();
            }
         }
      }
   }



   private LinkedHashMap<String,String> eventReportTopics = new LinkedHashMap<>();

   private void handleMqttMessage(String yaml)
   {
      Yamler yamler = new Yamler();
      ArrayList<LinkedHashMap<String, String>> list = yamler.decodeList(yaml);

      for (LinkedHashMap<String, String> map : list)
      {
         if (PULL.equals(map.get(EVENT_TYPE)))
         {
            String answerTopic = map.get(ANSWER_TOPIC);
            eventReportTopics.put(answerTopic, "*");
            int lastKnownNumber = Integer.parseInt(map.get(LAST_KNOWN_NUMBER));
            String[] relevantEventTypes = new String[0];
            String eventTypes = map.get(RELEVANT_EVENT_TYPES);

            if (eventTypes != null)
            {
               relevantEventTypes = eventTypes.split(" ");
               eventReportTopics.put(answerTopic, " " + eventTypes + " ");
            }

            SortedMap<Integer, LinkedHashMap<String, String>> eventMaps = gb.getEventSource().pull(lastKnownNumber, relevantEventTypes);

            if (eventMaps.size() == 0) continue;

            String yamlList = EventSource.encodeYaml(eventMaps);
            try
            {
               mqttClient.publish(answerTopic, yamlList.getBytes(), 2, false);
            }
            catch (MqttException e)
            {
               e.printStackTrace();
            }
         }
         else
         {
            gb.applyEvents(EventSource.encodeYaml(map));
         }
      }

   }
}
