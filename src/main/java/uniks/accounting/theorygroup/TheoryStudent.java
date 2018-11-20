package uniks.accounting.theorygroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class TheoryStudent 
{

   public static final String PROPERTY_studentId = "studentId";

   private String studentId;

   public String getStudentId()
   {
      return studentId;
   }

   public TheoryStudent setStudentId(String value)
   {
      if (value == null ? this.studentId != null : ! value.equals(this.studentId))
      {
         String oldValue = this.studentId;
         this.studentId = value;
         firePropertyChange("studentId", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_group = "group";

   private TheoryGroup group = null;

   public TheoryGroup getGroup()
   {
      return this.group;
   }

   public TheoryStudent setGroup(TheoryGroup value)
   {
      if (this.group != value)
      {
         TheoryGroup oldValue = this.group;
         if (this.group != null)
         {
            this.group = null;
            oldValue.withoutStudents(this);
         }
         this.group = value;
         if (value != null)
         {
            value.withStudents(this);
         }
         firePropertyChange("group", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<Presentation> EMPTY_presentations = new java.util.ArrayList<Presentation>()
   { @Override public boolean add(Presentation value){ throw new UnsupportedOperationException("No direct add! Use xy.withPresentations(obj)"); }};


   public static final String PROPERTY_presentations = "presentations";

   private java.util.ArrayList<Presentation> presentations = null;

   public java.util.ArrayList<Presentation> getPresentations()
   {
      if (this.presentations == null)
      {
         return EMPTY_presentations;
      }

      return this.presentations;
   }

   public TheoryStudent withPresentations(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withPresentations(i);
            }
         }
         else if (item instanceof Presentation)
         {
            if (this.presentations == null)
            {
               this.presentations = new java.util.ArrayList<Presentation>();
            }
            if ( ! this.presentations.contains(item))
            {
               this.presentations.add((Presentation)item);
               ((Presentation)item).setStudent(this);
               firePropertyChange("presentations", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public TheoryStudent withoutPresentations(Object... value)
   {
      if (this.presentations == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutPresentations(i);
            }
         }
         else if (item instanceof Presentation)
         {
            if (this.presentations.contains(item))
            {
               this.presentations.remove((Presentation)item);
               ((Presentation)item).setStudent(null);
               firePropertyChange("presentations", item, null);
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

      result.append(" ").append(this.getStudentId());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setGroup(null);

      this.withoutPresentations(this.getPresentations().clone());


   }


}