package uniks.accounting.tapool;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class TAPool 
{

   public static final java.util.ArrayList<TAStudent> EMPTY_students = new java.util.ArrayList<TAStudent>()
   { @Override public boolean add(TAStudent value){ throw new UnsupportedOperationException("No direct add! Use xy.withStudents(obj)"); }};


   public static final String PROPERTY_students = "students";

   private java.util.ArrayList<TAStudent> students = null;

   public java.util.ArrayList<TAStudent> getStudents()
   {
      if (this.students == null)
      {
         return EMPTY_students;
      }

      return this.students;
   }

   public TAPool withStudents(Object... value)
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
         else if (item instanceof TAStudent)
         {
            if (this.students == null)
            {
               this.students = new java.util.ArrayList<TAStudent>();
            }
            if ( ! this.students.contains(item))
            {
               this.students.add((TAStudent)item);
               ((TAStudent)item).setPool(this);
               firePropertyChange("students", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public TAPool withoutStudents(Object... value)
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
         else if (item instanceof TAStudent)
         {
            if (this.students.contains(item))
            {
               this.students.remove((TAStudent)item);
               ((TAStudent)item).setPool(null);
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



   public void removeYou()
   {
      this.withoutStudents(this.getStudents().clone());


   }


}