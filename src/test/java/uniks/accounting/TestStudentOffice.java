
package uniks.accounting;

import org.fulib.Fulib;
import org.fulib.FulibTools;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.yaml.YamlIdMap;
import org.fulib.yaml.YamlObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.fulib.builder.ClassModelBuilder.*;

public class TestStudentOffice
{
   @Test
   public void testStudentsSubscribeToModeling() throws URISyntaxException, IOException
   {
      String fileName = yam2ObjectDiagram("initStudentsSubscribeToModeling");

      System.out.println(fileName);
   }

   @Test
   public void testStudentOfficeModel()
   {
      ClassModelBuilder mb = Fulib.classModelBuilder("uniks.accounting.studentOffice");

      ClassBuilder studentOffice = mb.buildClass("StudentOffice")
            .buildAttribute("department", STRING);
      ClassBuilder studyProgram = mb.buildClass("StudyProgram")
            .buildAttribute("subject", STRING);
      ClassBuilder course = mb.buildClass("Course")
            .buildAttribute("title", STRING);
      ClassBuilder examination = mb.buildClass("Examination")
            .buildAttribute("date", STRING);
      ClassBuilder enrollment = mb.buildClass("Enrollment")
            .buildAttribute("grade", STRING);
      ClassBuilder lecturer = mb.buildClass("Lecturer")
            .buildAttribute("name", STRING);
      ClassBuilder uniStudent = mb.buildClass("UniStudent")
            .buildAttribute("name", STRING)
            .buildAttribute("studentId", STRING);

      studentOffice.buildAssociation(studyProgram, "programs", MANY, "department", ONE);
      studentOffice.buildAssociation(uniStudent, "students", MANY, "department", ONE);
      studentOffice.buildAssociation(lecturer, "lecturers", MANY, "department", ONE);
      studyProgram.buildAssociation(course, "courses", MANY, "programs", MANY);
      course.buildAssociation(examination, "exams", MANY, "topic", ONE);
      examination.buildAssociation(lecturer, "lecturer", ONE, "examinations", MANY);
      enrollment.buildAssociation(examination, "exam", ONE, "enrollments", MANY);
      uniStudent.buildAssociation(studyProgram, "majorSubject", ONE, "students", MANY);
      uniStudent.buildAssociation(enrollment, "enrollments", MANY, "student", ONE);

      String fileName = FulibTools.classDiagrams().dumpSVG(mb.getClassModel(), "tmp/StudentOfficeModel.svg");
      System.out.println(fileName);

      Fulib.generator().generate(mb.getClassModel());
      Fulib.tablesGenerator().generate(mb.getClassModel());
   }


   @Test
   public void testAlbertsModelingClass() throws URISyntaxException, IOException
   {
      String fileName = yam2ObjectDiagram("initAlbertsModelingClass");

      System.out.println(fileName);
   }



   @Test
   public void testSEGroupModel()
   {
      ClassModelBuilder mb = Fulib.classModelBuilder("uniks.accounting.segroup");

      ClassBuilder seGroup = mb.buildClass("SEGroup")
            .buildAttribute("head", STRING);
      ClassBuilder seStudent = mb.buildClass("SEStudent")
            .buildAttribute("studentId", STRING)
            .buildAttribute("teachingAssistantFor", STRING);
      ClassBuilder seClass = mb.buildClass("SEClass")
            .buildAttribute("topic", STRING)
            .buildAttribute("term", STRING);
      ClassBuilder achievement = mb.buildClass("Achievement")
            .buildAttribute("grade", STRING)
            .buildAttribute("officeStatus", STRING);
      ClassBuilder solution = mb.buildClass("Solution")
            .buildAttribute("gitUrl", STRING)
            .buildAttribute("points", DOUBLE);
      ClassBuilder assignment = mb.buildClass("Assignment")
            .buildAttribute("task", STRING)
            .buildAttribute("points", DOUBLE);

      seGroup.buildAssociation(seClass, "classes", MANY, "group", ONE);
      seGroup.buildAssociation(seStudent, "students", MANY, "group", ONE);
      seClass.buildAssociation(assignment, "assignments", MANY, "seClass", ONE);
      seClass.buildAssociation(achievement, "participations", MANY, "seClass", ONE);
      seStudent.buildAssociation(achievement, "achievements", MANY, "student", ONE);
      achievement.buildAssociation(solution, "solutions", MANY, "achievement", ONE);
      solution.buildAssociation(assignment, "assignment", ONE, "solutions", MANY);

      String fileName = FulibTools.classDiagrams().dumpSVG(mb.getClassModel(), "tmp/SEGroupModel.svg");
      System.out.println(fileName);

      Fulib.generator().generate(mb.getClassModel());
      Fulib.tablesGenerator().generate(mb.getClassModel());
   }

   @Test
   public void testTheoryGroupModel()
   {
      ClassModelBuilder mb = Fulib.classModelBuilder("uniks.accounting.theorygroup");

      ClassBuilder theoryGroup = mb.buildClass("TheoryGroup")
            .buildAttribute("head", STRING);
      ClassBuilder theoryStudent = mb.buildClass("TheoryStudent")
            .buildAttribute("studentId", STRING)
            .buildAttribute("name", STRING)
            .buildAttribute("ta_4", STRING);
      ClassBuilder seminar = mb.buildClass("Seminar")
            .buildAttribute("topic", STRING)
            .buildAttribute("term", STRING);
      ClassBuilder presentation = mb.buildClass("Presentation")
            .buildAttribute("slides", INT)
            .buildAttribute("scholarship", INT)
            .buildAttribute("content", INT)
            .buildAttribute("total", INT)
            .buildAttribute("grade", STRING)
            .buildAttribute("officeStatus", STRING);

      theoryGroup.buildAssociation(seminar, "seminars", MANY, "group", ONE);
      theoryGroup.buildAssociation(theoryStudent, "students", MANY, "group", ONE);
      theoryStudent.buildAssociation(presentation, "presentations", MANY, "student", ONE);
      seminar.buildAssociation(presentation, "presentations", MANY, "seminar", ONE);

      String fileName = FulibTools.classDiagrams().dumpSVG(mb.getClassModel(), "tmp/TheoryGroupModel.svg");
      System.out.println(fileName);

      Fulib.generator().generate(mb.getClassModel());
      Fulib.tablesGenerator().generate(mb.getClassModel());
   }


   @Test
   public void testTAPool()
   {
      ClassModelBuilder mb = Fulib.classModelBuilder("uniks.accounting.tapool");

      ClassBuilder taPool = mb.buildClass("TAPool");
      ClassBuilder taStudent = mb.buildClass("TAStudent")
            .buildAttribute("studentId", STRING)
            .buildAttribute("name", STRING)
            .buildAttribute("teachingAssistantFor", STRING);

      taPool.buildAssociation(taStudent, "students", MANY, "pool", ONE);

      String fileName = FulibTools.classDiagrams().dumpSVG(mb.getClassModel(), "tmp/TAPoolModel.svg");
      System.out.println(fileName);

      Fulib.generator().generate(mb.getClassModel());
      Fulib.tablesGenerator().generate(mb.getClassModel());
   }



   private String yam2ObjectDiagram(String yamlName) throws URISyntaxException, IOException
   {
      URL url = ClassLoader.getSystemResource(yamlName + ".yaml");
      Path initData = Paths.get(url.toURI());
      byte[] bytes = Files.readAllBytes(initData);
      String yamlString = new String(bytes);

      YamlIdMap idMap = new YamlIdMap("uniks.accounting");

      Object root = idMap.decode(yamlString);

      YamlObject seGroup = (YamlObject) root;

      return FulibTools.objectDiagrams().dumpSVG("tmp/" + yamlName + ".svg", root);
   }


}
