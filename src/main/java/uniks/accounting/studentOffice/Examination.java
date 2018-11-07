package uniks.accounting.studentOffice;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Examination  
{

   public static final String PROPERTY_date = "date";

   private String date;

   public String getDate()
   {
      return date;
   }

   public Examination setDate(String value)
   {
      if (value == null ? this.date != null : ! value.equals(this.date))
      {
         String oldValue = this.date;
         this.date = value;
         firePropertyChange("date", oldValue, value);
      }
      return this;
   }


   private Course topic = null;

   public Course getTopic()
   {
      return this.topic;
   }

   public Examination setTopic(Course value)
   {
      if (this.topic != value)
      {
         Course oldValue = this.topic;
         if (this.topic != null)
         {
            this.topic = null;
            oldValue.withoutExams(this);
         }
         this.topic = value;
         if (value != null)
         {
            value.withExams(this);
         }
         firePropertyChange("topic", oldValue, value);
      }
      return this;
   }



   private Lecturer lecturer = null;

   public Lecturer getLecturer()
   {
      return this.lecturer;
   }

   public Examination setLecturer(Lecturer value)
   {
      if (this.lecturer != value)
      {
         Lecturer oldValue = this.lecturer;
         if (this.lecturer != null)
         {
            this.lecturer = null;
            oldValue.withoutExaminations(this);
         }
         this.lecturer = value;
         if (value != null)
         {
            value.withExaminations(this);
         }
         firePropertyChange("lecturer", oldValue, value);
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

   public Examination withEnrollments(Object... value)
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
               ((Enrollment)item).setExam(this);
               firePropertyChange("enrollments", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Examination withoutEnrollments(Object... value)
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
               ((Enrollment)item).setExam(null);
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

      result.append(" ").append(this.getDate());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setTopic(null);
      this.setLecturer(null);

      this.withoutEnrollments(this.getEnrollments().clone());


   }


   public static final String PROPERTY_topic = "topic";

   public static final String PROPERTY_lecturer = "lecturer";

   public static final String PROPERTY_enrollments = "enrollments";

}