package uniks.accounting;

import org.fulib.FulibTools;
import org.junit.Assert;
import org.junit.Test;
import uniks.accounting.segroup.SEGroup;
import uniks.accounting.studentOffice.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestEventSourceing
{

   public static final String CS = "CS";
   public static final String ALBERT = "Albert";
   public static final String MODELING = "modeling";

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
      UniStudent alice = ob.buildStudent("Alice", "m4242");
      ob.chooseMajorSubject(alice, cs);
      ob.enroll(alice, exam);

      FulibTools.objectDiagrams().dumpSVG("tmp/StudentOffice.svg", ob.getStudentOffice());

      log = ob.getEventSource();

      StudentOfficeBuilder clone = new StudentOfficeBuilder();
      clone.build(log);

      FulibTools.objectDiagrams().dumpSVG("tmp/OfficeClone.svg", clone.getStudentOffice());

      Assert.assertThat(clone.getStudentOffice().getStudents().size(), equalTo(1));

      SEGroupBuilder gb = new SEGroupBuilder();
      SEGroup seGroup = gb.buildSEGroup(ALBERT);
      gb.buildSEClass(MODELING, "2018-10");

      gb.build(log);

      FulibTools.objectDiagrams().dumpSVG("tmp/SEGroup.svg", seGroup);


   }
}
