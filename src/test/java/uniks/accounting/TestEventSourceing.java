package uniks.accounting;

import org.fulib.FulibTools;
import org.junit.Assert;
import org.junit.Test;
import uniks.accounting.segroup.*;
import uniks.accounting.studentOffice.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

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
   public void testBuildViaEvents()
   {
      String log = null;

      StudentOfficeBuilder ob = new StudentOfficeBuilder();

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

      log = officeEventSource.encodeYaml(pullMap);


      SEGroupBuilder gb = new SEGroupBuilder();
      SEGroup seGroup = gb.buildSEGroup(ALBERT);
      gb.buildStudent("Alica", M_4242);
      SEClass modelingClass = gb.buildSEClass(MODELING, "2018-10");
      Assignment midTerm = gb.buildAssignment(modelingClass, MID_TERM, 23.0);
      Assignment finals = gb.buildAssignment(modelingClass, "finals", 42.0);

      SortedMap<Integer, LinkedHashMap<String, String>> sharedEvents = officeEventSource.pull(0, StudentOfficeBuilder.BUILD_STUDENT, StudentOfficeBuilder.ENROLL);
      gb.build(officeEventSource.encodeYaml(sharedEvents));

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

      SEGroupBuilder seClone = new SEGroupBuilder();
      seClone.build(seLog);
      FulibTools.objectDiagrams().dumpSVG("tmp/SEGroupClone.svg", seClone.getSeGroup());

      ob.build(seLog);

      assertThat(fb16.getStudents(M_4242).getName(), equalTo("Alica"));
      assertThat(fb16.getStudents(M_4242).getEnrollments().get(0).getGrade(), equalTo("A"));

      FulibTools.objectDiagrams().dumpSVG("tmp/StudentOffice.svg", ob.getStudentOffice());


      pullMap = officeEventSource.pull(lastPullNumber);
      lastPullNumber = pullMap.lastKey();

      StudentOfficeBuilder clone = new StudentOfficeBuilder();
      clone.build(log);

      FulibTools.objectDiagrams().dumpSVG("tmp/OfficeClone.svg", clone.getStudentOffice());

      clone.build(officeEventSource.encodeYaml(pullMap));

      FulibTools.objectDiagrams().dumpSVG("tmp/OfficeCloneGroupMerge.svg", clone.getStudentOffice());

      assertThat(clone.getStudentOffice().getStudents().size(), equalTo(2));

   }

   private Boolean filterBuildStudent(Map.Entry<Integer, LinkedHashMap<String, String>> entry)
   {
      LinkedHashMap<String, String> map = entry.getValue();
      String opCode = map.get(StudentOfficeBuilder.OPCODE);
      if (opCode != null && opCode.equals(StudentOfficeBuilder.BUILD_STUDENT))
      {
         return true;
      }
      return false;
   }
}
