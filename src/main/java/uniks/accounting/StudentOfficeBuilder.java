package uniks.accounting;

import org.fulib.yaml.Yamler;
import uniks.accounting.studentOffice.*;
import uniks.accounting.studentOffice.tables.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class StudentOfficeBuilder
{

   public static final String EVENT_TYPE = "eventType";
   public static final String STUDENT_OFFICE_CREATED = "studentOfficeCreated";
   public static final String STUDY_PROGRAM_CREATED = "studyProgramCreated";
   public static final String COURSE_CREATED = "courseCreated";
   public static final String PROGRAM_NAME = "programName";
   public static final String NAME = "name";
   public static final String EXAMINATION_CREATED = "examinationCreated";
   public static final String COURSE = "course";
   public static final String LECTURER = "lecturer";
   public static final String DATE = "date";
   public static final String LECTURER_CREATED = "lecturerCreated";
   public static final String STUDENT_ID = "studentId";
   public static final String MAJOR_SUBJECT = "majorSubject";
   public static final String STUDENT_CREATED = "studentCreated";
   public static final String STUDENT_ENROLLED = "studentEnrolled";
   public static final String COURSE_NAME = "courseName";
   public static final String LECTURER_NAME = "lecturerName";
   public static final String CHOOSE_MAJOR_SUBJECT = "chooseMajorSubject";
   public static final String EXAMINATION_GRADED = "examinationGraded";
   public static final String GRADE = "grade";

   public static final String DATABASE_STUDENTS_OFFICE_EVENTS_YAML = "database/StudentsOfficeEvents.yaml";

   private StudentOffice studentOffice;
   private String officeName = "";

   public StudentOffice getStudentOffice()
   {
      return studentOffice;
   }

   private EventSource eventSource;
   private EventFiler eventFiler;

   public StudentOfficeBuilder()
   {
      eventSource = new EventSource();
      eventFiler = new EventFiler(eventSource).setHistoryFileName(DATABASE_STUDENTS_OFFICE_EVENTS_YAML);
   }


   public EventSource getEventSource()
   {
      return eventSource;
   }

   public EventFiler getEventFiler()
   {
      return eventFiler;
   }

   public String getEventLog()
   {
      return eventSource.encodeYaml();
   }

   public void applyEvents(String yaml)
   {
      Yamler yamler = new Yamler();
      ArrayList<LinkedHashMap<String, String>> list = yamler.decodeList(yaml);

      for (LinkedHashMap<String, String> map : list)
      {
         if (STUDENT_OFFICE_CREATED.equals(map.get(EVENT_TYPE)))
         {
            officeName = map.get(NAME);
            studentOffice = getOrCreateStudentOffice(officeName);
         }
         else if (STUDY_PROGRAM_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateStudyProgram(map.get(NAME));
         }
         else if (COURSE_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateStudentOffice(null);

            StudyProgram program = getOrCreateStudyProgram(map.get(PROGRAM_NAME));

            getOrCreateCourse(program, map.get(NAME));
         }
         else if (LECTURER_CREATED.equals(map.get(EVENT_TYPE)))
         {
            buildLecturer(map.get(NAME));
         }
         else if (EXAMINATION_CREATED.equals(map.get(EVENT_TYPE)))
         {
            Course course = getOrCreateCourse(map.get(COURSE));
            Lecturer lecturer = getOrCreateLecturer(map, LECTURER);
            getOrCreateExamination(course, lecturer, map.get(DATE));
         }
         else if (STUDENT_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateStudent(map.get(NAME), map.get(STUDENT_ID));
         }
         else if (CHOOSE_MAJOR_SUBJECT.equals(map.get(EVENT_TYPE)))
         {
            UniStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            StudyProgram program = studentOffice.getPrograms(map.get(MAJOR_SUBJECT));
            chooseMajorSubject(student, program);
         }
         else if (STUDENT_ENROLLED.equals(map.get(EVENT_TYPE)))
         {
            UniStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            Course course = getOrCreateCourse(map.get(COURSE_NAME));
            Lecturer lecturer = getOrCreateLecturer(map, LECTURER_NAME);
            Examination exam = getOrCreateExamination(course, lecturer, map.get(DATE));
            enroll(student, exam);
         }
         else if (EXAMINATION_GRADED.equals(map.get(EVENT_TYPE)))
         {
            UniStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            Course course = getOrCreateCourse(map.get(COURSE_NAME));
            Lecturer lecturer = getOrCreateLecturer(map, LECTURER_NAME);
            Examination exam = getOrCreateExamination(course, lecturer, map.get(DATE));
            Enrollment enrollment = enroll(student, exam);
            gradeExamination(enrollment, map.get(GRADE));
         }
      }
   }

   private Lecturer getOrCreateLecturer(LinkedHashMap<String, String> map, String lecturerName)
   {
      return studentOffice.getLecturers(map.get(lecturerName));
   }

   private UniStudent getOrCreateStudent(String s)
   {

      UniStudent stud = studentOffice.getStudents(s);
      return stud;
   }

   private Course getOrCreateCourse(String  courseName)
   {
      CourseTable courseTable = new StudentOfficeTable(studentOffice).expandPrograms().expandCourses()
            .filter(c -> c.getTitle().equals(courseName));
      return courseTable.get(0);
   }


   public void gradeExamination(Enrollment enrollment, String grade)
   {
      enrollment.setGrade(grade);

      UniStudent student = enrollment.getStudent();
      Examination exam = enrollment.getExam();

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(EXAMINATION_GRADED).append("\n")
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
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_ENROLLED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + COURSE_NAME + ": ").append(Yamler.encapsulate(exam.getTopic().getTitle())).append("\n")
            .append("  " + LECTURER_NAME + ": ").append(Yamler.encapsulate(exam.getLecturer().getName())).append("\n")
            .append("  " + DATE + ": ").append(Yamler.encapsulate(exam.getDate())).append("\n\n");

      eventSource.append(buf);

      return enrollment;
   }

   public UniStudent getOrCreateStudent(String name, String studentId)
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

      LinkedHashMap<String,String> map = new LinkedHashMap<>();
      map.put(EVENT_TYPE, STUDENT_CREATED);
      map.put(STUDENT_ID, studentId);
      map.put(NAME,name);
      map.put(EventSource.EVENT_KEY, STUDENT_CREATED + "/" + studentId);

      eventSource.append(map);

      return stud;
   }

   public void chooseMajorSubject(UniStudent student, StudyProgram program)
   {
      student.setMajorSubject(program);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(CHOOSE_MAJOR_SUBJECT).append("\n")
            .append("  " + STUDENT_ID + ": ").append(student.getStudentId()).append("\n")
            .append("  " + MAJOR_SUBJECT + ": ").append(Yamler.encapsulate(program.getSubject())).append("\n\n");

      eventSource.append(buf);
   }

   public StudentOffice getOrCreateStudentOffice(String name)
   {
      if (studentOffice == null)
      {
         studentOffice = new StudentOffice();

         LinkedHashMap<String,String> map = new LinkedHashMap<>();
         map.put(EVENT_TYPE, STUDENT_OFFICE_CREATED);
         map.put(NAME, name);
         map.put(EventSource.EVENT_KEY, STUDENT_OFFICE_CREATED);

         eventSource.append(map);
      }

      studentOffice.setDepartment(name);

      return studentOffice;
   }


   public StudyProgram getOrCreateStudyProgram(String name)
   {
      getOrCreateStudentOffice(officeName);

      StudyProgram studyProgram = studentOffice.getPrograms(name);

      if (studyProgram == null)
      {
         studyProgram = new StudyProgram()
               .setSubject(name)
               .setDepartment(studentOffice);

         StringBuilder buf = new StringBuilder()
               .append("- " + EVENT_TYPE + ": ").append(STUDY_PROGRAM_CREATED).append("\n")
               .append("  " + NAME + ": ").append(Yamler.encapsulate(""+name)).append("\n\n");
         eventSource.append(buf);
      }

      return studyProgram;
   }


   public Course getOrCreateCourse(StudyProgram studyProgram, String name)
   {
      Course course = studyProgram.getCourses(name);

      if (course == null)
      {
         course = new Course()
               .setTitle(name)
               .withPrograms(studyProgram);

         StringBuilder buf = new StringBuilder()
               .append("- " + EVENT_TYPE + ": ").append(COURSE_CREATED).append("\n")
               .append("  " + PROGRAM_NAME + ": ").append(Yamler.encapsulate(studyProgram.getSubject())).append("\n")
               .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");
         eventSource.append(buf);
      }

      return course;
   }

   public Lecturer buildLecturer(String name)
   {
      getOrCreateStudentOffice(officeName);

      Lecturer result = studentOffice.getLecturers(name);

      if (result != null) return result;

      result = new Lecturer()
            .setName(name)
            .setDepartment(studentOffice);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(LECTURER_CREATED).append("\n")
            .append("  " + NAME + ": ").append(Yamler.encapsulate(name)).append("\n\n");
      eventSource.append(buf);

      return result;
   }

   public Examination getOrCreateExamination(Course course, Lecturer lecturer, String date)
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
               .setDate(date)
               .setTopic(course);

         StringBuilder buf = new StringBuilder()
               .append("- " + EVENT_TYPE + ": ").append(EXAMINATION_CREATED).append("\n")
               .append("  " + COURSE + ": ").append(course.getTitle()).append("\n")
               .append("  " + LECTURER + ": ").append(lecturer.getName()).append("\n")
               .append("  " + DATE + ": ").append(Yamler.encapsulate(date)).append("\n\n");
         eventSource.append(buf);
      }
      return exam;
   }


}
