package uniks.accounting;

import org.fulib.yaml.Yamler;
import uniks.accounting.segroup.Assignment;
import uniks.accounting.segroup.SEClass;
import uniks.accounting.segroup.SEGroup;
import uniks.accounting.segroup.SEStudent;
import uniks.accounting.studentOffice.UniStudent;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SEGroupBuilder
{
   public static final String OPCODE = "opcode";
   public static final String HEAD = "head";
   public static final String BUILD_SE_GROUP = "buildSEGroup";
   public static final String BUILD_STUDENT = "buildStudent";
   public static final String STUDENT_ID = "studentId";
   public static final String NAME = "name";
   public static final String BUILD_SE_CLASS = "buildSEClass";
   public static final String TOPIC = "topic";
   public static final String TERM = "term";
   public static final String TASK = "task";
   public static final String POINTS = "points";
   public static final String BUILD_ASSIGNMENT = "buildAssignment";


   private SEGroup seGroup;
   private StringBuffer eventSource = new StringBuffer();

   public SEGroup getSeGroup()
   {
      return seGroup;
   }

   public StringBuffer getEventSource()
   {
      return eventSource;
   }

   public void build(String yaml)
   {
      Yamler yamler = new Yamler();
      ArrayList<LinkedHashMap<String, String>> list = yamler.decodeList(yaml);

      for (LinkedHashMap<String, String> map : list)
      {
         if (BUILD_SE_GROUP.equals(map.get(OPCODE)))
         {
            buildSEGroup(map.get(HEAD));
         }
         else if (BUILD_STUDENT.equals(map.get(OPCODE)))
         {
            buildStudent(map.get(NAME), map.get(STUDENT_ID));
         }
         else if (BUILD_SE_CLASS.equals(map.get(OPCODE)))
         {
            buildSEClass(map.get(TOPIC), map.get(TERM));
         }
      }
   }

   public Assignment buildAssignment(SEClass seClass, String task, double points)
   {
      Assignment assignment = seClass.getAssignments(task);

      if (assignment != null && assignment.getPoints() == points) return assignment;

      if (assignment == null)
      {
         assignment = new Assignment()
               .setTask(task)
               .setSeClass(seClass);
      }

      assignment.setPoints(points);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(BUILD_ASSIGNMENT).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(seClass.getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(seClass.getTerm())).append("\n")
            .append("  " + TASK + ": ").append(Yamler.encapsulate(task)).append("\n")
            .append("  " + POINTS + ": ").append(Yamler.encapsulate(String.format("%.1f", points))).append("\n\n");

      eventSource.append(buf);

      return assignment;
   }

   public SEClass buildSEClass(String topic, String term)
   {
      SEClass seClass = seGroup.getClasses(topic, term);

      if (seClass != null) return seClass;

      seClass = new SEClass()
            .setTopic(topic)
            .setTerm(term)
            .setGroup(seGroup);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(BUILD_SE_CLASS).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(topic)).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(term)).append("\n\n");

      eventSource.append(buf);

      return seClass;
   }


   public SEStudent buildStudent(String name, String studentId)
   {
      SEStudent student = seGroup.getStudents(studentId);

      if (student != null) return student;

      student = new SEStudent()
            .setStudentId(studentId)
            .setGroup(seGroup);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(BUILD_STUDENT).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(studentId)).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");

      eventSource.append(buf);

      return student;
   }


   public SEGroup buildSEGroup(String head)
   {
      if (seGroup != null && seGroup.getHead().equals(head)) return seGroup;

      if (seGroup == null)
      {
         seGroup = new SEGroup();
      }

      seGroup.setHead(head);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(BUILD_SE_GROUP).append("\n")
            .append("  " + HEAD + ": ").append(Yamler.encapsulate(head)).append("\n\n");

      eventSource.append(buf);

      return seGroup;
   }
}
