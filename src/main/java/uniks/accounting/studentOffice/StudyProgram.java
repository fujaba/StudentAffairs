package uniks.accounting.studentOffice;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class StudyProgram  
{

   public static final String PROPERTY_subject = "subject";

   private String subject;

   public String getSubject()
   {
      return subject;
   }

   public StudyProgram setSubject(String value)
   {
      if (value == null ? this.subject != null : ! value.equals(this.subject))
      {
         String oldValue = this.subject;
         this.subject = value;
         firePropertyChange("subject", oldValue, value);
      }
      return this;
   }


   private StudentOffice department = null;

   public StudentOffice getDepartment()
   {
      return this.department;
   }

   public StudyProgram setDepartment(StudentOffice value)
   {
      if (this.department != value)
      {
         StudentOffice oldValue = this.department;
         if (this.department != null)
         {
            this.department = null;
            oldValue.withoutPrograms(this);
         }
         this.department = value;
         if (value != null)
         {
            value.withPrograms(this);
         }
         firePropertyChange("department", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<Course> EMPTY_courses = new java.util.ArrayList<Course>()
   { @Override public boolean add(Course value){ throw new UnsupportedOperationException("No direct add! Use xy.withCourses(obj)"); }};


   private java.util.ArrayList<Course> courses = null;

   public java.util.ArrayList<Course> getCourses()
   {
      if (this.courses == null)
      {
         return EMPTY_courses;
      }

      return this.courses;
   }

   public StudyProgram withCourses(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withCourses(i);
            }
         }
         else if (item instanceof Course)
         {
            if (this.courses == null)
            {
               this.courses = new java.util.ArrayList<Course>();
            }
            if ( ! this.courses.contains(item))
            {
               this.courses.add((Course)item);
               ((Course)item).withPrograms(this);
               firePropertyChange("courses", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public StudyProgram withoutCourses(Object... value)
   {
      if (this.courses == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutCourses(i);
            }
         }
         else if (item instanceof Course)
         {
            if (this.courses.contains(item))
            {
               this.courses.remove((Course)item);
               ((Course)item).withoutPrograms(this);
               firePropertyChange("courses", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<UniStudent> EMPTY_students = new java.util.ArrayList<UniStudent>()
   { @Override public boolean add(UniStudent value){ throw new UnsupportedOperationException("No direct add! Use xy.withStudents(obj)"); }};


   private java.util.ArrayList<UniStudent> students = null;

   public java.util.ArrayList<UniStudent> getStudents()
   {
      if (this.students == null)
      {
         return EMPTY_students;
      }

      return this.students;
   }

   public StudyProgram withStudents(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withStudents(i);
            }
         }
         else if (item instanceof UniStudent)
         {
            if (this.students == null)
            {
               this.students = new java.util.ArrayList<UniStudent>();
            }
            if ( ! this.students.contains(item))
            {
               this.students.add((UniStudent)item);
               ((UniStudent)item).setMajorSubject(this);
               firePropertyChange("students", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public StudyProgram withoutStudents(Object... value)
   {
      if (this.students == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutStudents(i);
            }
         }
         else if (item instanceof UniStudent)
         {
            if (this.students.contains(item))
            {
               this.students.remove((UniStudent)item);
               ((UniStudent)item).setMajorSubject(null);
               firePropertyChange("students", item, null);
            }
         }
      }
      return this;
   }


   protected PropertyChangeSupport listeners = null;

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null)
      {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getSubject());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setDepartment(null);

      this.withoutCourses(this.getCourses().clone());


      this.withoutStudents(this.getStudents().clone());


   }


   public Course getCourses(String name)
   {
      for (Course c : this.getCourses())
      {
         if (c.getTitle().equals(name))
         {
            return c;
         }
      }

      return null;
   }
}