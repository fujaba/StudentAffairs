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

public class StudentOfficeService implements Runnable
{

   public static final String OPCODE = "opcode";
   public static final String UNIKS_FB_16_STUDENT_OFFICE = "/uniks/fb16/studentOffice";
   public static final String ANSWER_TOPIC = "answerTopic";
   public static final String LAST_KNOWN_NUMBER = "lastKnownNumber";


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
   private StudentOfficeBuilder ob = null;
   private MqttClient mqttClient;

   public void run()
   {
      // load data
      ob = new StudentOfficeBuilder();
      ob.getEventSource().addEventListener(e -> handleMyOwnEvent(e));

      // connect to mqtt broker
      String broker       = "tcp://localhost:1883";
      String clientId     = "de.uniks.StudentOffice";
      MemoryPersistence persistence = new MemoryPersistence();

      try {
         mqttClient = new MqttClient(broker, clientId, persistence);
         MqttConnectOptions connOpts = new MqttConnectOptions();
         connOpts.setCleanSession(true);
         System.out.println("Connecting to broker: "+broker);
         mqttClient.connect(connOpts);
         System.out.println("Student Office Connected");

         mqttClient.setCallback(mqttCallback);
         mqttClient.subscribe(UNIKS_FB_16_STUDENT_OFFICE);

         System.out.println("The Student Office is going to publish an event");
         mqttClient.publish(UNIKS_FB_16_STUDENT_OFFICE, "- StudentService: running".getBytes(), 2, false);

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
         String opCodes = reportTopic.getValue();
         if (opCodes.equals("*") || opCodes.indexOf(e.get(OPCODE)) >= 0)
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
         if ("pull".equals(map.get(OPCODE)))
         {
            String answerTopic = map.get(ANSWER_TOPIC);
            eventReportTopics.put(answerTopic, "*");
            int lastKnownNumber = Integer.parseInt(map.get(LAST_KNOWN_NUMBER));
            String[] relevantOpcodes = new String[0];
            String opCodes = map.get("relevantOpCodes");
            if (opCodes != null)
            {
               relevantOpcodes = opCodes.split(" ");
               eventReportTopics.put(answerTopic, " " + opCodes + " ");
            }
            SortedMap<Integer, LinkedHashMap<String, String>> eventMaps = ob.getEventSource().pull(lastKnownNumber, relevantOpcodes);
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
            ob.build(EventSource.encodeYaml(map));
         }
      }

   }
}
