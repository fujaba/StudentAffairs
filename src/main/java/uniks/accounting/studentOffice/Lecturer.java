package uniks.accounting.studentOffice;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Lecturer  
{

   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public Lecturer setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   private StudentOffice department = null;

   public StudentOffice getDepartment()
   {
      return this.department;
   }

   public Lecturer setDepartment(StudentOffice value)
   {
      if (this.department != value)
      {
         StudentOffice oldValue = this.department;
         if (this.department != null)
         {
            this.department = null;
            oldValue.withoutLecturers(this);
         }
         this.department = value;
         if (value != null)
         {
            value.withLecturers(this);
         }
         firePropertyChange("department", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<Examination> EMPTY_examinations = new java.util.ArrayList<Examination>()
   { @Override public boolean add(Examination value){ throw new UnsupportedOperationException("No direct add! Use xy.withExaminations(obj)"); }};


   private java.util.ArrayList<Examination> examinations = null;

   public java.util.ArrayList<Examination> getExaminations()
   {
      if (this.examinations == null)
      {
         return EMPTY_examinations;
      }

      return this.examinations;
   }

   public Lecturer withExaminations(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withExaminations(i);
            }
         }
         else if (item instanceof Examination)
         {
            if (this.examinations == null)
            {
               this.examinations = new java.util.ArrayList<Examination>();
            }
            if ( ! this.examinations.contains(item))
            {
               this.examinations.add((Examination)item);
               ((Examination)item).setLecturer(this);
               firePropertyChange("examinations", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Lecturer withoutExaminations(Object... value)
   {
      if (this.examinations == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutExaminations(i);
            }
         }
         else if (item instanceof Examination)
         {
            if (this.examinations.contains(item))
            {
               this.examinations.remove((Examination)item);
               ((Examination)item).setLecturer(null);
               firePropertyChange("examinations", item, null);
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


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setDepartment(null);

      this.withoutExaminations(this.getExaminations().clone());


   }


   public static final String PROPERTY_department = "department";

   public static final String PROPERTY_examinations = "examinations";

}