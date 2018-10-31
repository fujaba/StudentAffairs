package uniks.accounting;

import io.moquette.server.Server;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.fulib.FulibTools;
import org.junit.Test;
import uniks.accounting.segroup.*;
import uniks.accounting.studentOffice.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TestEventSourceing
{

   public static final String CS = "CS";
   public static final String ALBERT = "Albert";
   public static final String MODELING = "modeling";
   public static final String M_4242 = "m4242";
   public static final String MID_TERM = "mid_term";
   public static final String M_2323 = "m2323";
   public static final String CONFIG_STUDENT_OFFICE_EVENTS_YAML = "config/studentOfficeEvents.yaml";
   public static final String CONFIG_SE_GROUP_EVENTS_YAML = "config/seGroupEvents.yaml";
   public static final String CONFIG_SEGROUP_GRADING_YAML = "config/SEGroupGrading.yaml";
   private MqttClient mqttClient;

   @Test
   public void testBuildExam()
   {
      StudentOfficeBuilder ob = new StudentOfficeBuilder();
      StudentOffice fb16 = ob.buildStudentOffice("fb16");
      Lecturer albert = ob.buildLecturer("Albert");
      StudyProgram cs = ob.buildStudyProgram("CS");
      Course math = ob.buildCourse(cs, "math");
      Examination exam04 = ob.buildExamination(math, albert, "2019-04-12");
      Examination exam03 = ob.buildExamination(math, albert, "2019-03-12");
      Examination firstFit = ob.buildExamination(math, albert, "2018-10");

      assertThat(exam03 == firstFit, equalTo(true));
   }


   @Test
   public void testBuildViaEvents() throws MqttException, InterruptedException
   {
      String log = null;

      StudentOfficeBuilder ob = new StudentOfficeBuilder();
      // ob.getEventSource().addEventListener(e -> storeEvents(e));

      StudentOffice fb16 = ob.buildStudentOffice("FB16");
      StudyProgram cs = ob.buildStudyProgram(CS);
      Course math = ob.buildCourse(cs, "math");
      Course modeling = ob.buildCourse(cs, MODELING);
      Lecturer albert = ob.buildLecturer(ALBERT);
      Examination exam = ob.buildExamination(modeling, albert, "2019-03-19");
      ob.buildStudent("N.N.", M_4242);
      UniStudent bob = ob.buildStudent("Bob", M_2323);
      UniStudent alice = ob.buildStudent("Alice", M_4242);
      ob.chooseMajorSubject(alice, cs);
      ob.enroll(alice, exam);

      EventSource officeEventSource = ob.getEventSource();
      SortedMap<Integer, LinkedHashMap<String, String>> pullMap = officeEventSource.pull(0);

      assertThat(pullMap.size(), equalTo(10));

      Integer lastPullNumber = pullMap.lastKey();
      assertThat(lastPullNumber, equalTo(11));

      SortedMap<Integer, LinkedHashMap<String, String>> studentEvents = officeEventSource.pull(0, entry -> filterBuildStudent(entry));

      assertThat(studentEvents.size(), equalTo(2));

      log = EventSource.encodeYaml(pullMap);

      storeEvents(CONFIG_STUDENT_OFFICE_EVENTS_YAML, log);

      SEGroupBuilder gb = new SEGroupBuilder();
      SEGroup seGroup = gb.buildSEGroup(ALBERT);
      gb.buildStudent("Alica", M_4242);
      SEClass modelingClass = gb.buildSEClass(MODELING, "2018-10");
      Assignment midTerm = gb.buildAssignment(modelingClass, MID_TERM, 23.0);
      Assignment finals = gb.buildAssignment(modelingClass, "finals", 42.0);

      SortedMap<Integer, LinkedHashMap<String, String>> gbEventList = gb.getEventSource().pull(0);
      storeEvents(CONFIG_SE_GROUP_EVENTS_YAML, EventSource.encodeYaml(gbEventList));

      SortedMap<Integer, LinkedHashMap<String, String>> sharedEvents = officeEventSource.pull(0, StudentOfficeBuilder.BUILD_STUDENT, StudentOfficeBuilder.ENROLL);
      gb.sync(EventSource.encodeYaml(sharedEvents));

      int lastGroupEvent = gb.getEventSource().getEventNumber();

      SEStudent seAlice = seGroup.getStudents(M_4242);
      Achievement aliceModelingAchievement = seAlice.getAchievements(modelingClass);
      Solution midTermAlice = gb.buildSolution(aliceModelingAchievement, midTerm, "gitlab.com/alice/modeling");
      gb.gradeSolution(midTermAlice, 20.0);
      Solution finalsAlice = gb.buildSolution(aliceModelingAchievement, finals, "gitlab.com/alice/modeling");
      gb.gradeSolution(finalsAlice, 40.0);
      gb.gradeExamination(aliceModelingAchievement);


      FulibTools.objectDiagrams().dumpSVG("tmp/SEGroup.svg", seGroup);

      EventSource groupEventSource = gb.getEventSource();
      SortedMap<Integer, LinkedHashMap<String, String>> groupEvents = groupEventSource.pull(0);
      String seLog = EventSource.encodeYaml(groupEvents);

      groupEvents = groupEventSource.pull(lastGroupEvent);

      storeEvents(CONFIG_SEGROUP_GRADING_YAML, EventSource.encodeYaml(groupEvents));

      SEGroupBuilder seClone = new SEGroupBuilder();
      seClone.sync(seLog);
      FulibTools.objectDiagrams().dumpSVG("tmp/SEGroupClone.svg", seClone.getSeGroup());

      // pull just grading
      groupEvents = groupEventSource.pull(0, SEGroupBuilder.GRADE_EXAMINATION);
      seLog = EventSource.encodeYaml(groupEvents);
      ob.sync(seLog);

      assertThat(fb16.getStudents(M_4242).getName(), equalTo("Alice"));
      assertThat(fb16.getStudents(M_4242).getEnrollments().get(0).getGrade(), equalTo("A"));

      FulibTools.objectDiagrams().dumpSVG("tmp/StudentOffice.svg", ob.getStudentOffice());


      pullMap = officeEventSource.pull(lastPullNumber);
      lastPullNumber = pullMap.lastKey();

      StudentOfficeBuilder clone = new StudentOfficeBuilder();
      clone.sync(log);

      FulibTools.objectDiagrams().dumpSVG("tmp/OfficeClone.svg", clone.getStudentOffice());

      clone.sync(officeEventSource.encodeYaml(pullMap));

      FulibTools.objectDiagrams().dumpSVG("tmp/OfficeCloneGroupMerge.svg", clone.getStudentOffice());

      assertThat(clone.getStudentOffice().getStudents().size(), equalTo(2));

   }

   private void storeEvents(String fileName, String yaml)
   {
      File fileStore = new File(fileName);
      if (fileStore.exists())
      {
         fileStore.delete();
         try
         {
            fileStore.createNewFile();
         }
         catch (IOException e1)
         {
            e1.printStackTrace();
         }
      }

      try {
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
         out.print(yaml);
         out.close();
      }
      catch (IOException e2)
      {
         e2.printStackTrace();
      }
   }


   @Test
   public void testBuildViaService() throws MqttException, InterruptedException, IOException
   {
      try
      {
         new Server().startServer();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      // connect to mqtt broker
      String topic        = "/fb16";
      int qos             = 2;
      String broker       = "tcp://localhost:1883";
      String clientId     = "de.uniks.StudentAffairsTester";
      MemoryPersistence persistence = new MemoryPersistence();

      mqttClient = new MqttClient(broker, clientId, persistence);
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      mqttClient.connect(connOpts);
      System.out.println("Student Affairs Tester Connected");

      mqttClient.setCallback(mqttCallback);
      mqttClient.subscribe(StudentOfficeService.UNIKS_FB_16_STUDENT_OFFICE);

      String log = null;

      // start studentOfficeService
      StudentOfficeService studentOfficeService = new StudentOfficeService();
      ExecutorService executorService = Executors.newCachedThreadPool();
      executorService.execute(studentOfficeService);

      String studentServiceRunning = inbox.take();

      // open event file
      byte[] bytes = Files.readAllBytes(Paths.get(CONFIG_STUDENT_OFFICE_EVENTS_YAML));
      String yaml = new String(bytes);

      mqttClient.publish(StudentOfficeService.UNIKS_FB_16_STUDENT_OFFICE, bytes, 2, false);

      String buildOfficeYaml = inbox.take();

      // build se group
      mqttClient.subscribe(SEGroupService.UNIKS_FB_16_SE_GROUP);
      SEGroupService seGroupService = new SEGroupService();
      executorService.execute(seGroupService);

      String seGroupRunning = inbox.take();

      bytes = Files.readAllBytes(Paths.get(CONFIG_SE_GROUP_EVENTS_YAML));
      yaml = new String(bytes);
      mqttClient.publish(SEGroupService.UNIKS_FB_16_SE_GROUP, bytes, 2, false);

      String buildGroupYaml = inbox.take();

      // subscribe at student office
      LinkedHashMap<String, String> pullCommand = new LinkedHashMap<>();
      pullCommand.put(StudentOfficeBuilder.EVENT_TYPE, StudentOfficeService.PULL);
      pullCommand.put(StudentOfficeService.LAST_KNOWN_NUMBER, "0");
      pullCommand.put(StudentOfficeService.RELEVANT_EVENT_TYPES, StudentOfficeBuilder.BUILD_STUDENT + " " +  StudentOfficeBuilder.ENROLL);
      pullCommand.put(StudentOfficeService.ANSWER_TOPIC, SEGroupService.UNIKS_FB_16_SE_GROUP);
      mqttClient.publish(StudentOfficeService.UNIKS_FB_16_STUDENT_OFFICE, EventSource.encodeYaml(pullCommand).getBytes(), 2, false);

      String pullFromOfficeToGroup = inbox.take();

      String buildOfficeYaml2 = inbox.take();

      // subscribe at group
      pullCommand = new LinkedHashMap<>();
      pullCommand.put(StudentOfficeBuilder.EVENT_TYPE, SEGroupService.PULL);
      pullCommand.put(SEGroupService.LAST_KNOWN_NUMBER, "0");
      pullCommand.put(SEGroupService.RELEVANT_EVENT_TYPES, SEGroupBuilder.GRADE_EXAMINATION);
      pullCommand.put(SEGroupService.ANSWER_TOPIC, StudentOfficeService.UNIKS_FB_16_STUDENT_OFFICE);
      mqttClient.publish(SEGroupService.UNIKS_FB_16_SE_GROUP, EventSource.encodeYaml(pullCommand).getBytes(), 2, false);

      String pullFromGroupToOffice = inbox.take();

      // do the grading
      bytes = Files.readAllBytes(Paths.get(CONFIG_SEGROUP_GRADING_YAML));
      yaml = new String(bytes);
      mqttClient.publish(SEGroupService.UNIKS_FB_16_SE_GROUP, bytes, 2, false);

      String buildGrades = inbox.take();

      String gradesToOffice = inbox.take();

      System.out.println();
   }

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
         inbox.add(payload);
         System.out.println("Test log: \n" + topic + ": " + payload);
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken token)
      {

      }
   };

   private LinkedBlockingQueue<String> inbox = new LinkedBlockingQueue<>();

   private void handleOfficeEvent(LinkedHashMap<String, String> event)
   {
      // store
      // publish for student office service
      String yaml = EventSource.encodeYaml(event);
      try
      {
         mqttClient.publish(StudentOfficeService.UNIKS_FB_16_STUDENT_OFFICE, yaml.getBytes(), 2, false);
      }
      catch (MqttException e)
      {
         e.printStackTrace();
      }
   }

   private Boolean filterBuildStudent(Map.Entry<Integer, LinkedHashMap<String, String>> entry)
   {
      LinkedHashMap<String, String> map = entry.getValue();
      String eventType = map.get(StudentOfficeBuilder.EVENT_TYPE);
      if (eventType != null && eventType.equals(StudentOfficeBuilder.BUILD_STUDENT))
      {
         return true;
      }
      return false;
   }
}
