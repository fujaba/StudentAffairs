package uniks.accounting;

import org.fulib.FulibTools;
import org.junit.Assert;
import org.junit.Test;
import uniks.accounting.segroup.*;
import uniks.accounting.studentOffice.*;
import uniks.accounting.studentOffice.tables.StudentOfficeTable;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestEventSourceing
{

   public static final String CS = "CS";
   public static final String ALBERT = "Albert";
   public static final String MODELING = "modeling";
   public static final String M_4242 = "m4242";
   public static final String MID_TERM = "mid_term";

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

      Assert.assertThat(exam03 == firstFit, equalTo(true));
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
      UniStudent alice = ob.buildStudent("Alice", M_4242);
      ob.chooseMajorSubject(alice, cs);
      ob.enroll(alice, exam);

      log = ob.getEventSource();

      SEGroupBuilder gb = new SEGroupBuilder();
      SEGroup seGroup = gb.buildSEGroup(ALBERT);
      SEClass modelingClass = gb.buildSEClass(MODELING, "2018-10");
      Assignment midTerm = gb.buildAssignment(modelingClass, MID_TERM, 23.0);
      Assignment finals = gb.buildAssignment(modelingClass, "finals", 42.0);

      gb.build(log);

      SEStudent seAlice = seGroup.getStudents(M_4242);
      Achievement aliceModelingAchievement = seAlice.getAchievements(modelingClass);
      Solution midTermAlice = gb.buildSolution(aliceModelingAchievement, midTerm, "gitlab.com/alice/modeling");
      gb.gradeSolution(midTermAlice, 20.0);
      Solution finalsAlice = gb.buildSolution(aliceModelingAchievement, finals, "gitlab.com/alice/modeling");
      gb.gradeSolution(finalsAlice, 40.0);
      gb.gradeExamination(aliceModelingAchievement);


      FulibTools.objectDiagrams().dumpSVG("tmp/SEGroup.svg", seGroup);

      String seLog = gb.getEventSource();

      SEGroupBuilder seClone = new SEGroupBuilder();
      seClone.build(seLog);
      FulibTools.objectDiagrams().dumpSVG("tmp/SEGroupClone.svg", seClone.getSeGroup());

      ob.build(seLog);

      FulibTools.objectDiagrams().dumpSVG("tmp/StudentOffice.svg", ob.getStudentOffice());

      log = ob.getEventSource();

      StudentOfficeBuilder clone = new StudentOfficeBuilder();
      clone.build(log);

      FulibTools.objectDiagrams().dumpSVG("tmp/OfficeClone.svg", clone.getStudentOffice());

      Assert.assertThat(clone.getStudentOffice().getStudents().size(), equalTo(1));

   }
}
