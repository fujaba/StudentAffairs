package uniks.accounting;

import org.fulib.yaml.Yamler;
import uniks.accounting.segroup.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class SEGroupBuilder
{
   public static final String EVENT_TYPE = "eventType";
   public static final String HEAD = "head";
   public static final String BUILD_SE_GROUP = "buildSEGroup";
   public static final String STUDENT_CREATED = "studentCreated";
   public static final String STUDENT_ID = "studentId";
   public static final String NAME = "name";
   public static final String BUILD_SE_CLASS = "buildSEClass";
   public static final String TOPIC = "topic";
   public static final String TERM = "term";
   public static final String TASK = "task";
   public static final String POINTS = "points";
   public static final String BUILD_ASSIGNMENT = "buildAssignment";
   public static final String ENROLLED = "enrolled";
   public static final String BUILD_ACHIEVEMENT = "buildAchievement";
   public static final String STUDENT_ENROLLED = "studentEnrolled";
   public static final String COURSE_NAME = "courseName";
   public static final String LECTURER_NAME = "lecturerName";
   public static final String DATE = "date";
   public static final String BUILD_SOLUTION = "buildSolution";
   public static final String GIT_URL = "gitUrl";
   public static final String GRADE_SOLUTION = "gradeSolution";
   public static final String GRADE = "grade";
   public static final String EXAMINATION_GRADED = "examinationGraded";


   private SEGroup seGroup;
   private EventSource eventSource = new EventSource();

   public SEGroup getSeGroup()
   {
      return seGroup;
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
         if (BUILD_SE_GROUP.equals(map.get(EVENT_TYPE)))
         {
            buildSEGroup(map.get(HEAD));
         }
         else if (STUDENT_CREATED.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateStudent(map.get(NAME), map.get(STUDENT_ID));
         }
         else if (BUILD_SE_CLASS.equals(map.get(EVENT_TYPE)))
         {
            getOrCreateSEClass(map.get(TOPIC), map.get(TERM));
         }
         else if (BUILD_ASSIGNMENT.equals(map.get(EVENT_TYPE)))
         {
            SEClass seClass = seGroup.getClasses(map.get(TOPIC), map.get(TERM));
            double points = Double.parseDouble(map.get(POINTS));
            buildAssignment(seClass, map.get(TASK), points);
         }
         else if (BUILD_ACHIEVEMENT.equals(map.get(EVENT_TYPE)))
         {
            SEStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            SEClass seClass = seGroup.getClasses(map.get(TOPIC), map.get(TERM));
            getOrCreateAchievement(student, seClass);
         }
         else if (STUDENT_ENROLLED.equals(map.get(EVENT_TYPE)))
         {
            SEStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            SEClass seClass = getOrCreateSEClass(map.get(COURSE_NAME), map.get(DATE));
            Achievement achievement = getOrCreateAchievement(student, seClass);
            enroll(achievement);
         }
         else if (BUILD_SOLUTION.equals(map.get(EVENT_TYPE)))
         {
            SEStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            SEClass seClass = getOrCreateSEClass(map.get(TOPIC), map.get(TERM));
            Achievement achievement = getOrCreateAchievement(student, seClass);
            Assignment assignment = seClass.getAssignments(map.get(TASK));
            buildSolution(achievement, assignment, map.get(GIT_URL));
         }
         else if (GRADE_SOLUTION.equals(map.get(EVENT_TYPE)))
         {
            SEStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            SEClass seClass = getOrCreateSEClass(map.get(TOPIC), map.get(TERM));
            Achievement achievement = getOrCreateAchievement(student, seClass);
            Assignment assignment = seClass.getAssignments(map.get(TASK));
            Solution solution = achievement.getSolutions(assignment);
            gradeSolution(solution, Double.parseDouble(map.get(POINTS)));
         }
         else if (EXAMINATION_GRADED.equals(map.get(EVENT_TYPE)))
         {
            SEStudent student = getOrCreateStudent(map.get(STUDENT_ID));
            SEClass seClass = getOrCreateSEClass(map.get(COURSE_NAME), map.get(DATE));
            Achievement achievement = getOrCreateAchievement(student, seClass);
            gradeExamination(achievement);
         }
      }
   }

   private SEStudent getOrCreateStudent(String s)
   {
      return seGroup.getStudents(s);
   }


   public void gradeExamination(Achievement achievement)
   {
      double sumOfAssignments = 0.0;
      for (Assignment a : achievement.getSeClass().getAssignments())
      {
         sumOfAssignments += a.getPoints();
      }

      double sumOfSolutions = 0.0;
      for (Solution s : achievement.getSolutions())
      {
         sumOfSolutions += s.getPoints();
      }

      int missed = (int) (sumOfAssignments - sumOfSolutions);
      char grade = (char) ('A' + (missed / 10));

      if (grade > 'F') grade = 'F';

      if (("" + grade).equals(achievement.getGrade())) return;

      achievement.setGrade("" + grade);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(EXAMINATION_GRADED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(achievement.getStudent().getStudentId())).append("\n")
            .append("  " + COURSE_NAME + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTopic())).append("\n")
            .append("  " + DATE + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTerm())).append("\n")
            .append("  " + LECTURER_NAME + ": ").append(Yamler.encapsulate(achievement.getSeClass().getGroup().getHead())).append("\n")
            .append("  " + GRADE + ": ").append(grade).append("\n\n");

      eventSource.append(buf);
   }


   public void gradeSolution(Solution solution, double points)
   {
      solution.setPoints(points);

      Achievement achievement = solution.getAchievement();

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(GRADE_SOLUTION).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(achievement.getStudent().getStudentId())).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTerm())).append("\n")
            .append("  " + TASK + ": ").append(Yamler.encapsulate(solution.getAssignment().getTask())).append("\n")
            .append("  " + POINTS + ": ").append(String.format(Locale.ENGLISH, "%.1f", points)).append("\n\n");

      eventSource.append(buf);
   }


   public Solution buildSolution(Achievement achievement, Assignment assignment, String gitUrl)
   {
      Solution solution = achievement.getSolutions(assignment);

      if (solution != null && solution.getGitUrl().equals(gitUrl)) return  solution;

      if (solution == null)
      {
         solution = new Solution()
               .setAchievement(achievement)
               .setAssignment(assignment);
      }

      solution.setGitUrl(gitUrl);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(BUILD_SOLUTION).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(achievement.getStudent().getStudentId())).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTerm())).append("\n")
            .append("  " + TASK + ": ").append(Yamler.encapsulate(assignment.getTask())).append("\n")
            .append("  " + GIT_URL + ": ").append(Yamler.encapsulate(gitUrl)).append("\n\n");

      eventSource.append(buf);

      return solution;
   }


   public Achievement enroll(Achievement achievement)
   {
      achievement.setOfficeStatus(ENROLLED);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_ENROLLED).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(achievement.getStudent().getStudentId())).append("\n")
            .append("  " + COURSE_NAME + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTopic())).append("\n")
            .append("  " + LECTURER_NAME + ": ").append(Yamler.encapsulate(achievement.getSeClass().getGroup().getHead())).append("\n")
            .append("  " + DATE + ": ").append(Yamler.encapsulate(achievement.getSeClass().getTerm())).append("\n\n");

      eventSource.append(buf);

      return achievement;
   }


   public Achievement getOrCreateAchievement(SEStudent student, SEClass seClass)
   {
      Achievement achievement = student.getAchievements(seClass);

      if (achievement != null) return achievement;

      achievement = new Achievement()
            .setStudent(student)
            .setSeClass(seClass);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(BUILD_ACHIEVEMENT).append("\n")
            .append("  " + STUDENT_ID + ": ").append(Yamler.encapsulate(student.getStudentId())).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(seClass.getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(seClass.getTerm())).append("\n\n");

      eventSource.append(buf);

      return achievement;
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
            .append("- " + EVENT_TYPE + ": ").append(BUILD_ASSIGNMENT).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(seClass.getTopic())).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(seClass.getTerm())).append("\n")
            .append("  " + TASK + ": ").append(Yamler.encapsulate(task)).append("\n")
            .append("  " + POINTS + ": ").append(String.format(Locale.ENGLISH, "%.1f", points)).append("\n\n");

      eventSource.append(buf);

      return assignment;
   }

   public SEClass getOrCreateSEClass(String topic, String term)
   {
      SEClass seClass = seGroup.getClasses(topic, term);

      if (seClass != null) return seClass;

      seClass = new SEClass()
            .setTopic(topic)
            .setTerm(term)
            .setGroup(seGroup);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(BUILD_SE_CLASS).append("\n")
            .append("  " + TOPIC + ": ").append(Yamler.encapsulate(topic)).append("\n")
            .append("  " + TERM + ": ").append(Yamler.encapsulate(term)).append("\n\n");

      eventSource.append(buf);

      return seClass;
   }


   public SEStudent getOrCreateStudent(String name, String studentId)
   {
      SEStudent student = seGroup.getStudents(studentId);

      if (student != null) return student;

      student = new SEStudent()
            .setStudentId(studentId)
            .setGroup(seGroup);

      StringBuilder buf = new StringBuilder()
            .append("- " + EVENT_TYPE + ": ").append(STUDENT_CREATED).append("\n")
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
            .append("- " + EVENT_TYPE + ": ").append(BUILD_SE_GROUP).append("\n")
            .append("  " + HEAD + ": ").append(Yamler.encapsulate(head)).append("\n\n");

      eventSource.append(buf);

      return seGroup;
   }
}
