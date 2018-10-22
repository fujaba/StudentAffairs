package uniks.accounting;

import org.fulib.yaml.Yamler;
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
      }
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
