package uniks.accounting.studentOffice;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Enrollment  
{

   private Examination exam = null;

   public Examination getExam()
   {
      return this.exam;
   }

   public Enrollment setExam(Examination value)
   {
      if (this.exam != value)
      {
         Examination oldValue = this.exam;
         if (this.exam != null)
         {
            this.exam = null;
            oldValue.withoutEnrollments(this);
         }
         this.exam = value;
         if (value != null)
         {
            value.withEnrollments(this);
         }
         firePropertyChange("exam", oldValue, value);
      }
      return this;
   }



   private UniStudent student = null;

   public UniStudent getStudent()
   {
      return this.student;
   }

   public Enrollment setStudent(UniStudent value)
   {
      if (this.student != value)
      {
         UniStudent oldValue = this.student;
         if (this.student != null)
         {
            this.student = null;
            oldValue.withoutEnrollments(this);
         }
         this.student = value;
         if (value != null)
         {
            value.withEnrollments(this);
         }
         firePropertyChange("student", oldValue, value);
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



   public void removeYou()
   {
      this.setExam(null);
      this.setStudent(null);

   }
















   public static final String PROPERTY_grade = "grade";

   private String grade;

   public String getGrade()
   {
      return grade;
   }

   public Enrollment setGrade(String value)
   {
      if (value == null ? this.grade != null : ! value.equals(this.grade))
      {
         String oldValue = this.grade;
         this.grade = value;
         firePropertyChange("grade", oldValue, value);
      }
      return this;
   }


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getGrade());


      return result.substring(1);
   }

}