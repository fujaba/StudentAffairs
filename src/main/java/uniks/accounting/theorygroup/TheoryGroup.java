package uniks.accounting.theorygroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class TheoryGroup  
{

   public static final String PROPERTY_head = "head";

   private String head;

   public String getHead()
   {
      return head;
   }

   public TheoryGroup setHead(String value)
   {
      if (value == null ? this.head != null : ! value.equals(this.head))
      {
         String oldValue = this.head;
         this.head = value;
         firePropertyChange("head", oldValue, value);
      }
      return this;
   }


   public static final java.util.ArrayList<Seminar> EMPTY_seminars = new java.util.ArrayList<Seminar>()
   { @Override public boolean add(Seminar value){ throw new UnsupportedOperationException("No direct add! Use xy.withSeminars(obj)"); }};


   public static final String PROPERTY_seminars = "seminars";

   private java.util.ArrayList<Seminar> seminars = null;

   public java.util.ArrayList<Seminar> getSeminars()
   {
      if (this.seminars == null)
      {
         return EMPTY_seminars;
      }

      return this.seminars;
   }

   public TheoryGroup withSeminars(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withSeminars(i);
            }
         }
         else if (item instanceof Seminar)
         {
            if (this.seminars == null)
            {
               this.seminars = new java.util.ArrayList<Seminar>();
            }
            if ( ! this.seminars.contains(item))
            {
               this.seminars.add((Seminar)item);
               ((Seminar)item).setGroup(this);
               firePropertyChange("seminars", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public TheoryGroup withoutSeminars(Object... value)
   {
      if (this.seminars == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutSeminars(i);
            }
         }
         else if (item instanceof Seminar)
         {
            if (this.seminars.contains(item))
            {
               this.seminars.remove((Seminar)item);
               ((Seminar)item).setGroup(null);
               firePropertyChange("seminars", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<TheoryStudent> EMPTY_students = new java.util.ArrayList<TheoryStudent>()
   { @Override public boolean add(TheoryStudent value){ throw new UnsupportedOperationException("No direct add! Use xy.withStudents(obj)"); }};


   public static final String PROPERTY_students = "students";

   private java.util.ArrayList<TheoryStudent> students = null;

   public java.util.ArrayList<TheoryStudent> getStudents()
   {
      if (this.students == null)
      {
         return EMPTY_students;
      }

      return this.students;
   }

   public TheoryGroup withStudents(Object... value)
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
         else if (item instanceof TheoryStudent)
         {
            if (this.students == null)
            {
               this.students = new java.util.ArrayList<TheoryStudent>();
            }
            if ( ! this.students.contains(item))
            {
               this.students.add((TheoryStudent)item);
               ((TheoryStudent)item).setGroup(this);
               firePropertyChange("students", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public TheoryGroup withoutStudents(Object... value)
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
         else if (item instanceof TheoryStudent)
         {
            if (this.students.contains(item))
            {
               this.students.remove((TheoryStudent)item);
               ((TheoryStudent)item).setGroup(null);
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

      result.append(" ").append(this.getHead());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutSeminars(this.getSeminars().clone());


      this.withoutStudents(this.getStudents().clone());


   }


}