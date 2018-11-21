package uniks.accounting;

import org.fulib.yaml.Yamler;
import uniks.accounting.segroup.SEStudent;
import uniks.accounting.tapool.TAPool;
import uniks.accounting.tapool.TAStudent;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TAPoolBuilder
{
   public static final String EVENT_TYPE = "eventType";
   public static final String TA_POOL_CREATED = "taPoolCreated";
   public static final String STUDENT_HIRED_AS_TA = "studentHiredAsTA";
   public static final String STUDENT_ID = "studentId";
   public static final String NAME = "name";
   public static final String LECTURER_NAME = "lecturerName";

   private TAPool taPool;

   public TAPool getTaPool()
   {
      return taPool;
   }

   private EventSource eventSource = new EventSource();

   public EventSource getEventSource()
   {
      return eventSource;
   }

   public void applyEvents(String yaml)
   {
      Yamler yamler = new Yamler();
      ArrayList<LinkedHashMap<String, String>> list = yamler.decodeList(yaml);

      for (LinkedHashMap<String, String> map : list)
      {
         if (TA_POOL_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateTAPool();
         }
         else if (STUDENT_HIRED_AS_TA.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateTAPool();
            getOrCreateStudent(map.get(NAME), map.get(STUDENT_ID), map.get(LECTURER_NAME));
         }
      }
   }

   private void getOrCreateStudent(String name, String studentId, String lecturer)
   {
      TAStudent student = null;

      for (TAStudent s : taPool.getStudents())
      {
         if (s.getStudentId().equals(studentId))
         {
            student = s;
            break;
         }
      }

      if (student != null && student.getName().equals(name) && student.getTeachingAssistantFor().equals(lecturer))
      {
         return;
      }

      if (student == null)
      {
         student = new TAStudent();
      }

      student.setStudentId(studentId)
            .setName(name)
            .setTeachingAssistantFor(lecturer);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_HIRED_AS_TA).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(student.getName())).append("\n")
            .append("  " + LECTURER_NAME + ": ").append(Yamler.encapsulate(lecturer)).append("\n")
            .append("\n");

   }

   private void getOrCreateTAPool()
   {
      if (taPool != null) return;

      taPool = new TAPool();

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(TA_POOL_CREATED).append("\n")
            .append("\n");
   }
}
