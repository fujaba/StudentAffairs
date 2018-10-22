package uniks.accounting.studentOffice;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class StudentOffice  
{

   public static final String PROPERTY_department = "department";

   private String department;

   public String getDepartment()
   {
      return department;
   }

   public StudentOffice setDepartment(String value)
   {
      if (value == null ? this.department != null : ! value.equals(this.department))
      {
         String oldValue = this.department;
         this.department = value;
         firePropertyChange("department", oldValue, value);
      }
      return this;
   }


   public static final java.util.ArrayList<StudyProgram> EMPTY_programs = new java.util.ArrayList<StudyProgram>()
   { @Override public boolean add(StudyProgram value){ throw new UnsupportedOperationException("No direct add! Use xy.withPrograms(obj)"); }};


   private java.util.ArrayList<StudyProgram> programs = null;

   public java.util.ArrayList<StudyProgram> getPrograms()
   {
      if (this.programs == null)
      {
         return EMPTY_programs;
      }

      return this.programs;
   }

   public StudentOffice withPrograms(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withPrograms(i);
            }
         }
         else if (item instanceof StudyProgram)
         {
            if (this.programs == null)
            {
               this.programs = new java.util.ArrayList<StudyProgram>();
            }
            if ( ! this.programs.contains(item))
            {
               this.programs.add((StudyProgram)item);
               ((StudyProgram)item).setDepartment(this);
               firePropertyChange("programs", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public StudentOffice withoutPrograms(Object... value)
   {
      if (this.programs == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutPrograms(i);
            }
         }
         else if (item instanceof StudyProgram)
         {
            if (this.programs.contains(item))
            {
               this.programs.remove((StudyProgram)item);
               ((StudyProgram)item).setDepartment(null);
               firePropertyChange("programs", item, null);
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

   public StudentOffice withStudents(Object... value)
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
               ((UniStudent)item).setDepartment(this);
               firePropertyChange("students", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public StudentOffice withoutStudents(Object... value)
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
               ((UniStudent)item).setDepartment(null);
               firePropertyChange("students", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<Lecturer> EMPTY_lecturers = new java.util.ArrayList<Lecturer>()
   { @Override public boolean add(Lecturer value){ throw new UnsupportedOperationException("No direct add! Use xy.withLecturers(obj)"); }};


   private java.util.ArrayList<Lecturer> lecturers = null;

   public java.util.ArrayList<Lecturer> getLecturers()
   {
      if (this.lecturers == null)
      {
         return EMPTY_lecturers;
      }

      return this.lecturers;
   }

   public StudentOffice withLecturers(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withLecturers(i);
            }
         }
         else if (item instanceof Lecturer)
         {
            if (this.lecturers == null)
            {
               this.lecturers = new java.util.ArrayList<Lecturer>();
            }
            if ( ! this.lecturers.contains(item))
            {
               this.lecturers.add((Lecturer)item);
               ((Lecturer)item).setDepartment(this);
               firePropertyChange("lecturers", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public StudentOffice withoutLecturers(Object... value)
   {
      if (this.lecturers == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutLecturers(i);
            }
         }
         else if (item instanceof Lecturer)
         {
            if (this.lecturers.contains(item))
            {
               this.lecturers.remove((Lecturer)item);
               ((Lecturer)item).setDepartment(null);
               firePropertyChange("lecturers", item, null);
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

      result.append(" ").append(this.getDepartment());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutPrograms(this.getPrograms().clone());


      this.withoutStudents(this.getStudents().clone());


      this.withoutLecturers(this.getLecturers().clone());


   }


   public StudyProgram getPrograms(String name)
   {
      for (StudyProgram p : this.getPrograms())
      {
         if (p.getSubject().equals(name))
         {
            return p;
         }
      }
      return null;
   }

   public Lecturer getLecturers(String name)
   {
      for (Lecturer l : this.getLecturers())
      {
         if (l.getName().equals(name))
         {
            return l;
         }
      }
      return null;
   }

   public UniStudent getStudents(String studentId)
   {
      for (UniStudent stud : this.getStudents())
      {
         if (stud.getStudentId().equals(studentId))
         {
            return stud;
         }
      }
      return null;
   }
}