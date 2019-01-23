package uniks.accounting;

import org.fulib.yaml.Yamler;
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
   public static final String TEACHING_ASSISTANT_FOR = "teachingAssistantFor";

   public static final String DATABASE_TA_POOL_EVENTS_YAML = "database/TAPoolEvents.yaml";
   
   private TAPool taPool;

   public TAPool getTaPool()
   {
      return taPool;
   }

   private EventSource eventSource;
   private EventFiler eventFiler;
   
   public TAPoolBuilder() {
      this.eventSource = new EventSource();
      this.eventFiler = new EventFiler(eventSource).setHistoryFileName(DATABASE_TA_POOL_EVENTS_YAML);
   }

   public EventSource getEventSource()
   {
      return eventSource;
   }

   public EventFiler getEventFiler() {
      return eventFiler;
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
            getOrCreateStudent(map.get(NAME), map.get(STUDENT_ID), map.get(TEACHING_ASSISTANT_FOR));
         }
      }
   }

   public TAStudent getOrCreateStudent(String name, String studentId, String lecturer)
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
         return student;
      }

      if (student == null)
      {
         student = new TAStudent()
                 .setStudentId(studentId)
                 .setName(name)
                 .setTeachingAssistantFor(lecturer);
         taPool.withStudents(student);
      }

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_HIRED_AS_TA).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(student.getName())).append("\n")
            .append("  " + TEACHING_ASSISTANT_FOR + ": ").append(Yamler.encapsulate(lecturer)).append("\n")
            .append("\n");
      eventSource.append(buf);
      
      return student;
   }

   public TAPool getOrCreateTAPool()
   {
      if (taPool != null) return taPool;

      taPool = new TAPool();

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(TA_POOL_CREATED).append("\n")
            .append("\n");

      eventSource.append(buf);
      
      return taPool;
   }
}
