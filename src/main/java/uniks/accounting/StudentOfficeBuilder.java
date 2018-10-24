package uniks.accounting;

import org.fulib.yaml.Yamler;
import uniks.accounting.segroup.Achievement;
import uniks.accounting.segroup.SEClass;
import uniks.accounting.segroup.SEStudent;
import uniks.accounting.studentOffice.*;
import uniks.accounting.studentOffice.tables.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class StudentOfficeBuilder
{

   public static final String OPCODE = "opcode";
   public static final String BUILD_STUDENT_OFFICE = "buildStudentOffice";
   public static final String BUILD_STUDY_PROGRAM = "buildStudyProgram";
   public static final String BUILD_COURSE = "buildCourse";
   public static final String PROGRAM_NAME = "programName";
   public static final String NAME = "name";
   public static final String BUILD_EXAMINATION = "buildExamination";
   public static final String COURSE = "course";
   public static final String LECTURER = "lecturer";
   public static final String DATE = "date";
   public static final String BUILD_LECTURER = "buildLecturer";
   public static final String STUDENT_ID = "studentId";
   public static final String MAJOR_SUBJECT = "majorSubject";
   public static final String BUILD_STUDENT = "buildStudent";
   public static final String ENROLL = "enroll";
   public static final String COURSE_NAME = "courseName";
   public static final String LECTURER_NAME = "lecturerName";
   public static final String CHOOSE_MAJOR_SUBJECT = "chooseMajorSubject";
   public static final String GRADE_EXAMINATION = "gradeExamination";
   public static final String GRADE = "grade";

   private StudentOffice studentOffice;

   public StudentOffice getStudentOffice()
   {
      return studentOffice;
   }

   private StringBuffer eventSource = new StringBuffer();

   public String getEventSource()
   {
      return eventSource.toString();
   }

   public void build(String yaml)
   {
      Yamler yamler = new Yamler();
      ArrayList<LinkedHashMap<String, String>> list = yamler.decodeList(yaml);

      for (LinkedHashMap<String, String> map : list)
      {
         if (BUILD_STUDENT_OFFICE.equals(map.get(OPCODE)))
         {
            buildStudentOffice(map.get(NAME));
         }
         else if (BUILD_STUDY_PROGRAM.equals(map.get(OPCODE)))
         {
            buildStudyProgram(map.get(NAME));
         }
         else if (BUILD_COURSE.equals(map.get(OPCODE)))
         {
            StudyProgram program = studentOffice.getPrograms(map.get(PROGRAM_NAME));
            buildCourse(program, map.get(NAME));
         }
         else if (BUILD_LECTURER.equals(map.get(OPCODE)))
         {
            buildLecturer(map.get(NAME));
         }
         else if (BUILD_EXAMINATION.equals(map.get(OPCODE)))
         {
            CourseTable courseTable = new StudentOfficeTable(studentOffice).expandPrograms().expandCourses()
                  .filter(c -> c.getTitle().equals(map.get(COURSE)));
            Course course = courseTable.get(0);
            Lecturer lecturer = studentOffice.getLecturers(map.get(LECTURER));
            buildExamination(course, lecturer, map.get(DATE));
         }
         else if (BUILD_STUDENT.equals(map.get(OPCODE)))
         {
            StudyProgram program = studentOffice.getPrograms(map.get(MAJOR_SUBJECT));
            buildStudent(map.get(NAME), map.get(STUDENT_ID));
         }
         else if (CHOOSE_MAJOR_SUBJECT.equals(map.get(OPCODE)))
         {
            UniStudent student = studentOffice.getStudents(map.get(STUDENT_ID));
            StudyProgram program = studentOffice.getPrograms(map.get(MAJOR_SUBJECT));
            chooseMajorSubject(student, program);
         }
         else if (ENROLL.equals(map.get(OPCODE)))
         {
            UniStudent student = studentOffice.getStudents(map.get(STUDENT_ID));
            CourseTable courseTable = new StudentOfficeTable(studentOffice).expandPrograms().expandCourses()
                  .filter(c -> c.getTitle().equals(map.get(COURSE_NAME)));
            Course course = courseTable.get(0);
            Lecturer lecturer = studentOffice.getLecturers(map.get(LECTURER_NAME));
            Examination exam = buildExamination(course, lecturer, map.get(DATE));

            enroll(student, exam);
         }
         else if (GRADE_EXAMINATION.equals(map.get(OPCODE)))
         {
            UniStudent student = studentOffice.getStudents(map.get(STUDENT_ID));
            CourseTable courseTable = new StudentOfficeTable(studentOffice).expandPrograms().expandCourses()
                  .filter(c -> c.getTitle().equals(map.get(COURSE_NAME)));
            Course course = courseTable.get(0);
            Lecturer lecturer = studentOffice.getLecturers(map.get(LECTURER_NAME));
            Examination exam = buildExamination(course, lecturer, map.get(DATE));
            Enrollment enrollment = student.getEnrollments(exam);
            gradeExamination(enrollment, map.get(GRADE));
         }
      }
   }


   public void gradeExamination(Enrollment enrollment, String grade)
   {
      enrollment.setGrade(grade);

      UniStudent student = enrollment.getStudent();
      Examination exam = enrollment.getExam();

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(GRADE_EXAMINATION).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + COURSE_NAME + ": ").append(Yamler.encapsulate(exam.getTopic().getTitle())).append("\n")
            .append("  " + DATE + ": ").append(Yamler.encapsulate(exam.getDate())).append("\n")
            .append("  " + LECTURER_NAME + ": ").append(Yamler.encapsulate(exam.getLecturer().getName())).append("\n")
            .append("  " + GRADE + ": ").append(grade).append("\n\n");

      eventSource.append(buf);

   }

   public Enrollment enroll(UniStudent student, Examination exam)
   {
      for(Enrollment e : student.getEnrollments())
      {
         if (e.getExam() == exam)
         {
            return e;
         }
      }

      Enrollment enrollment = new Enrollment()
            .setStudent(student)
            .setExam(exam);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(ENROLL).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + COURSE_NAME + ": ").append(Yamler.encapsulate(exam.getTopic().getTitle())).append("\n")
            .append("  " + LECTURER_NAME + ": ").append(Yamler.encapsulate(exam.getLecturer().getName())).append("\n")
            .append("  " + DATE + ": ").append(Yamler.encapsulate(exam.getDate())).append("\n\n");

      eventSource.append(buf);

      return enrollment;
   }

   public UniStudent buildStudent(String name, String studentId)
   {
      UniStudent stud = studentOffice.getStudents(studentId);

      if (stud != null && stud.getName().equals(name)) return stud;

      if (stud == null)
      {
         stud = new UniStudent()
               .setStudentId(studentId)
               .setDepartment(studentOffice);
      }

      stud.setName(name);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(BUILD_STUDENT).append("\n")
            .append("  " + STUDENT_ID + ": ").append(studentId).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");

      eventSource.append(buf);

      return stud;
   }

   public void chooseMajorSubject(UniStudent student, StudyProgram program)
   {
      student.setMajorSubject(program);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(CHOOSE_MAJOR_SUBJECT).append("\n")
            .append("  " + STUDENT_ID + ": ").append(student.getStudentId()).append("\n")
            .append("  " + MAJOR_SUBJECT + ": ").append(Yamler.encapsulate(program.getSubject())).append("\n\n");

      eventSource.append(buf);
   }

   public StudentOffice buildStudentOffice(String name)
   {
      if (studentOffice == null)
      {
         studentOffice = new StudentOffice();

         StringBuilder buf = new StringBuilder()
               .append("- " + OPCODE + ": ").append(BUILD_STUDENT_OFFICE).append("\n")
               .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");

         eventSource.append(buf);
      }

      studentOffice.setDepartment(name);

      return studentOffice;
   }


   public StudyProgram buildStudyProgram(String name)
   {
      StudyProgram studyProgram = studentOffice.getPrograms(name);

      if (studyProgram == null)
      {
         studyProgram = new StudyProgram()
               .setDepartment(studentOffice)
               .setSubject(name);

         StringBuilder buf = new StringBuilder()
               .append("- " + OPCODE + ": ").append(BUILD_STUDY_PROGRAM).append("\n")
               .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");
         eventSource.append(buf);
      }

      return studyProgram;
   }


   public Course buildCourse(StudyProgram studyProgram, String name)
   {
      Course course = studyProgram.getCourses(name);

      if (course == null)
      {
         course = new Course()
               .setTitle(name)
               .withPrograms(studyProgram);

         StringBuilder buf = new StringBuilder()
               .append("- " + OPCODE + ": ").append(BUILD_COURSE).append("\n")
               .append("  " + PROGRAM_NAME + ": ").append(Yamler.encapsulate(studyProgram.getSubject())).append("\n")
               .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");
         eventSource.append(buf);
      }

      return course;
   }

   public Lecturer buildLecturer(String name)
   {
      Lecturer result = studentOffice.getLecturers(name);

      if (result != null) return result;

      result = new Lecturer()
            .setName(name)
            .setDepartment(studentOffice);

      StringBuilder buf = new StringBuilder()
            .append("- " + OPCODE + ": ").append(BUILD_LECTURER).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");
      eventSource.append(buf);

      return result;
   }

   public Examination buildExamination(Course course, Lecturer lecturer, String date)
   {
      Examination exam = null;
      Examination firstFitExam = null;
      for (Examination x : course.getExams())
      {
         if (x.getLecturer() == lecturer && x.getDate().equals(date))
         {
            exam = x;
            break;
         }

         if ( date.lastIndexOf('-') <= 5
               && x.getLecturer() == lecturer
               && x.getDate().compareTo(date) >= 0)
         {
            if (firstFitExam == null)
            {
               firstFitExam = x;
            }
            else
            {
               if (firstFitExam.getDate().compareTo(x.getDate()) > 0)
               {
                  firstFitExam = x;
               }
            }
         }
      }

      if (firstFitExam != null) return firstFitExam;

      if (exam == null)
      {
         exam = new Examination()
               .setLecturer(lecturer)
               .setTopic(course)
               .setDate(date);

         StringBuilder buf = new StringBuilder()
               .append("- " + OPCODE + ": ").append(BUILD_EXAMINATION).append("\n")
               .append("  " + COURSE + ": ").append(course.getTitle()).append("\n")
               .append("  " + LECTURER + ": ").append(lecturer.getName()).append("\n")
               .append("  " + DATE + ": ").append(Yamler.encapsulate(date)).append("\n\n");
         eventSource.append(buf);
      }
      return exam;
   }


}
