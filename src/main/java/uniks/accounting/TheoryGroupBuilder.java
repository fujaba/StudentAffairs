package uniks.accounting;

import org.fulib.yaml.Yamler;
import uniks.accounting.segroup.*;
import uniks.accounting.theorygroup.Presentation;
import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.theorygroup.TheoryGroup;
import uniks.accounting.theorygroup.TheoryStudent;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TheoryGroupBuilder
{
   public static final String EVENT_TYPE = "eventType";
   public static final String HEAD = "head";
   public static final String THEORY_GROUP_CREATED = "theoryGroupCreated";
   public static final String STUDENT_CREATED = "studentCreated";
   public static final String STUDENT_ID = "studentId";
   public static final String NAME = "name";
   public static final String TOPIC = "topic";
   public static final String TERM = "term";
   public static final String TASK = "task";
   public static final String POINTS = "points";
   public static final String ENROLLED = "enrolled";
   public static final String STUDENT_ENROLLED = "studentEnrolled";
   public static final String COURSE_NAME = "courseName";
   public static final String LECTURER_NAME = "lecturerName";
   public static final String DATE = "date";
   public static final String GIT_URL = "gitUrl";
   public static final String GRADE = "grade";
   public static final String EXAMINATION_GRADED = "examinationGraded";
   public static final String MARTIN = "Martin";
   public static final String SEMINAR_CREATED = "seminarCreated";
   public static final String PRESENTATION_CREATED = "presentationCreated";
   public static final String PRESENTATION_GRADED = "presentationGraded";
   public static final String SLIDES = "slides";
   public static final String SCHOLARSHIP = "scholarship";
   public static final String CONTENT = "content";
   public static final String STUDENT_HIRED_AS_TA = "studentHiredAsTA";
   public static final String TEACHING_ASSISTANT_FOR = "teachingAssistantFor";


   private TheoryGroup theoryGroup;
   private EventSource eventSource = new EventSource();

   public TheoryGroup getTheoryGroup()
   {
      return theoryGroup;
   }

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
         if (THEORY_GROUP_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateTheoryGroup();
         }
         else if (STUDENT_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateStudent(map.get(NAME), map.get(STUDENT_ID));
         }
         else if (STUDENT_HIRED_AS_TA.equals(map.get(EVENT_TYPE)))
         {
            TheoryStudent student = getOrCreateStudent(map.get(NAME), map.get(STUDENT_ID));
            studentHired(student, map.get(LECTURER_NAME));
         }
         else if (SEMINAR_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateSeminar(map.get(TOPIC), map.get(TERM));
         }
         else if (PRESENTATION_CREATED.equals(map.get(EVENT_TYPE)))
         {
            TheoryStudent student = getOrCreateStudent(map.get(STUDENT_ID), map.get(NAME));
            Seminar seminar = getOrCreateSeminar(map.get(TOPIC), map.get(TERM));
            getOrCreatePresentation(student, seminar);
         }
         else if (PRESENTATION_GRADED.equals(map.get(EVENT_TYPE)))
         {
            TheoryStudent student = getOrCreateStudent(map.get(STUDENT_ID), map.get(NAME));
            Seminar seminar = getOrCreateSeminar(map.get(TOPIC), map.get(TERM));
            Presentation presentation = getOrCreatePresentation(student, seminar);
            gradePresentation(presentation, map.get(SLIDES), map.get(SCHOLARSHIP), map.get(CONTENT));
         }
         else if (STUDENT_ENROLLED.equals(map.get(EVENT_TYPE)))
         {
            TheoryStudent student = getOrCreateStudent(map.get(STUDENT_ID), map.get(NAME));
            Seminar seminar = getOrCreateSeminar(map.get(COURSE_NAME), map.get(DATE));
            Presentation presentation = getOrCreatePresentation(student, seminar);
            enroll(presentation);
         }
         else if (EXAMINATION_GRADED.equals(map.get(EVENT_TYPE)))
         {
            TheoryStudent student = getOrCreateStudent(map.get(STUDENT_ID), map.get(NAME));
            Seminar seminar = getOrCreateSeminar(map.get(COURSE_NAME), map.get(DATE));
            Presentation presentation = getOrCreatePresentation(student, seminar);
            gradePresentation(presentation);
         }
      }
   }

   public void studentHired(TheoryStudent student, String lecturer)
   {
      if (lecturer.equals(student.getTa_4()))
      {
         return;
      }

      student.setTa_4(lecturer);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_HIRED_AS_TA).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(student.getName())).append("\n")
            .append("  " + TEACHING_ASSISTANT_FOR + ": ").append(Yamler.encapsulate(lecturer)).append("\n")
            .append("\n");

      eventSource.append(buf);

      return;
   }


   public void enroll(Presentation presentation)
   {
      if (ENROLLED.equals(presentation.getOfficeStatus()))
      {
         return;
      }

      presentation.setOfficeStatus(ENROLLED);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_ENROLLED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(presentation.getStudent().getStudentId())).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(presentation.getStudent().getName())).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(presentation.getSeminar().getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(presentation.getSeminar().getTerm())).append("\n")
            .append("\n");

      eventSource.append(buf);

      return;
   }

   public void gradePresentation(Presentation presentation, String slides, String scholarship, String content)
   {

      int slidesPoints = Integer.parseInt(slides);
      int scholarshipPoints = Integer.parseInt(scholarship);
      int contentPoints = Integer.parseInt(content);

      if (presentation.getSlides() == slidesPoints
      && presentation.getScholarship() == scholarshipPoints
      && presentation.getContent() == contentPoints)
      {
         return;
      }

      presentation
            .setSlides(slidesPoints)
            .setScholarship(scholarshipPoints)
            .setContent(contentPoints);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(PRESENTATION_GRADED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(presentation.getStudent().getStudentId())).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(presentation.getStudent().getName())).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(presentation.getSeminar().getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(presentation.getSeminar().getTerm())).append("\n")
            .append("  " + SLIDES + ": ").append(Yamler.encapsulate("" + presentation.getSlides())).append("\n")
            .append("  " + SCHOLARSHIP + ": ").append(Yamler.encapsulate("" + presentation.getScholarship())).append("\n")
            .append("  " + CONTENT + ": ").append(Yamler.encapsulate("" + presentation.getContent())).append("\n")
            .append("\n");

      eventSource.append(buf);
   }


   public Presentation getOrCreatePresentation(TheoryStudent student, Seminar seminar)
   {
      for (Presentation p : student.getPresentations())
      {
         if (p.getSeminar() == seminar)
         {
            return p;
         }
      }

      Presentation presentation = new Presentation()
            .setStudent(student)
            .setSeminar(seminar);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(PRESENTATION_CREATED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(student.getName())).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(seminar.getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(seminar.getTerm())).append("\n")
            .append("\n");

      return presentation;
   }


   public void gradePresentation(Presentation presentation)
   {
      double totalPoints = presentation.getSlides()
            + presentation.getScholarship()
            + presentation.getContent();


      double missed = 30.0 - totalPoints;
      char grade = (char) ('A' + (missed / 4));

      if (grade > 'F') grade = 'F';

      if (("" + grade).equals(presentation.getGrade())) return;

      presentation.setGrade("" + grade);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(EXAMINATION_GRADED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(presentation.getStudent().getStudentId())).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(presentation.getStudent().getName())).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(presentation.getSeminar().getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(presentation.getSeminar().getTerm())).append("\n")
            .append("  " + GRADE + ": ").append(grade).append("\n\n");

      eventSource.append(buf);
   }




   public Seminar getOrCreateSeminar(String topic, String term)
   {
      Seminar seminar = null;

      for (Seminar s : theoryGroup.getSeminars())
      {
         if (s.getTopic().equals(topic) && s.getTerm().equals(term))
         {
            seminar = s;
            return seminar;
         }
      }

      seminar = new Seminar()
            .setTopic(topic)
            .setTerm(term)
            .setGroup(theoryGroup);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(SEMINAR_CREATED).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(topic)).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(term)).append("\n\n");

      eventSource.append(buf);

      return seminar;
   }


   public TheoryStudent getOrCreateStudent(String name, String studentId)
   {
      TheoryStudent student = null;

      for (TheoryStudent s : theoryGroup.getStudents())
      {
         if (s.getStudentId().equals(studentId))
         {
            student = s;
            break;
         }
      }

      if (student != null && student.getName().equals(name))
      {
         return student;
      }

      student = new TheoryStudent()
            .setStudentId(studentId)
            .setName(name)
            .setGroup(theoryGroup);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_CREATED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(studentId)).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");

      eventSource.append(buf);

      return student;
   }


   public TheoryGroup getOrCreateTheoryGroup()
   {
      if (theoryGroup != null && theoryGroup.getHead().equals(MARTIN)) return theoryGroup;

      if (theoryGroup == null)
      {
         theoryGroup = new TheoryGroup();
      }

      theoryGroup.setHead(MARTIN);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(THEORY_GROUP_CREATED).append("\n")
            .append("  " + HEAD + ": ").append(Yamler.encapsulate(MARTIN)).append("\n\n");

      eventSource.append(buf);

      return theoryGroup;
   }
}
