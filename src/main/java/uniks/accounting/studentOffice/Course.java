package uniks.accounting.studentOffice;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Course  
{

   public static final String PROPERTY_title = "title";

   private String title;

   public String getTitle()
   {
      return title;
   }

   public Course setTitle(String value)
   {
      if (value == null ? this.title != null : ! value.equals(this.title))
      {
         String oldValue = this.title;
         this.title = value;
         firePropertyChange("title", oldValue, value);
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

   public Course withPrograms(Object... value)
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
               ((StudyProgram)item).withCourses(this);
               firePropertyChange("programs", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Course withoutPrograms(Object... value)
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
               ((StudyProgram)item).withoutCourses(this);
               firePropertyChange("programs", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<Examination> EMPTY_exams = new java.util.ArrayList<Examination>()
   { @Override public boolean add(Examination value){ throw new UnsupportedOperationException("No direct add! Use xy.withExams(obj)"); }};


   private java.util.ArrayList<Examination> exams = null;

   public java.util.ArrayList<Examination> getExams()
   {
      if (this.exams == null)
      {
         return EMPTY_exams;
      }

      return this.exams;
   }

   public Course withExams(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withExams(i);
            }
         }
         else if (item instanceof Examination)
         {
            if (this.exams == null)
            {
               this.exams = new java.util.ArrayList<Examination>();
            }
            if ( ! this.exams.contains(item))
            {
               this.exams.add((Examination)item);
               ((Examination)item).setTopic(this);
               firePropertyChange("exams", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Course withoutExams(Object... value)
   {
      if (this.exams == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutExams(i);
            }
         }
         else if (item instanceof Examination)
         {
            if (this.exams.contains(item))
            {
               this.exams.remove((Examination)item);
               ((Examination)item).setTopic(null);
               firePropertyChange("exams", item, null);
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

      result.append(" ").append(this.getTitle());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutPrograms(this.getPrograms().clone());


      this.withoutExams(this.getExams().clone());


   }


   public static final String PROPERTY_programs = "programs";

   public static final String PROPERTY_exams = "exams";

}