package uniks.accounting.studentOffice;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class UniStudent  
{

   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public UniStudent setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_studentId = "studentId";

   private String studentId;

   public String getStudentId()
   {
      return studentId;
   }

   public UniStudent setStudentId(String value)
   {
      if (value == null ? this.studentId != null : ! value.equals(this.studentId))
      {
         String oldValue = this.studentId;
         this.studentId = value;
         firePropertyChange("studentId", oldValue, value);
      }
      return this;
   }


   private StudentOffice department = null;

   public StudentOffice getDepartment()
   {
      return this.department;
   }

   public UniStudent setDepartment(StudentOffice value)
   {
      if (this.department != value)
      {
         StudentOffice oldValue = this.department;
         if (this.department != null)
         {
            this.department = null;
            oldValue.withoutStudents(this);
         }
         this.department = value;
         if (value != null)
         {
            value.withStudents(this);
         }
         firePropertyChange("department", oldValue, value);
      }
      return this;
   }



   private StudyProgram majorSubject = null;

   public StudyProgram getMajorSubject()
   {
      return this.majorSubject;
   }

   public UniStudent setMajorSubject(StudyProgram value)
   {
      if (this.majorSubject != value)
      {
         StudyProgram oldValue = this.majorSubject;
         if (this.majorSubject != null)
         {
            this.majorSubject = null;
            oldValue.withoutStudents(this);
         }
         this.majorSubject = value;
         if (value != null)
         {
            value.withStudents(this);
         }
         firePropertyChange("majorSubject", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<Enrollment> EMPTY_enrollments = new java.util.ArrayList<Enrollment>()
   { @Override public boolean add(Enrollment value){ throw new UnsupportedOperationException("No direct add! Use xy.withEnrollments(obj)"); }};


   private java.util.ArrayList<Enrollment> enrollments = null;

   public java.util.ArrayList<Enrollment> getEnrollments()
   {
      if (this.enrollments == null)
      {
         return EMPTY_enrollments;
      }

      return this.enrollments;
   }

   public UniStudent withEnrollments(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withEnrollments(i);
            }
         }
         else if (item instanceof Enrollment)
         {
            if (this.enrollments == null)
            {
               this.enrollments = new java.util.ArrayList<Enrollment>();
            }
            if ( ! this.enrollments.contains(item))
            {
               this.enrollments.add((Enrollment)item);
               ((Enrollment)item).setStudent(this);
               firePropertyChange("enrollments", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public UniStudent withoutEnrollments(Object... value)
   {
      if (this.enrollments == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutEnrollments(i);
            }
         }
         else if (item instanceof Enrollment)
         {
            if (this.enrollments.contains(item))
            {
               this.enrollments.remove((Enrollment)item);
               ((Enrollment)item).setStudent(null);
               firePropertyChange("enrollments", item, null);
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

      result.append(" ").append(this.getName());
      result.append(" ").append(this.getStudentId());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setDepartment(null);
      this.setMajorSubject(null);

      this.withoutEnrollments(this.getEnrollments().clone());


   }


}