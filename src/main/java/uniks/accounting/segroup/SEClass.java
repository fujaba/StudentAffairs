package uniks.accounting.segroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SEClass  
{

   public static final String PROPERTY_topic = "topic";

   private String topic;

   public String getTopic()
   {
      return topic;
   }

   public SEClass setTopic(String value)
   {
      if (value == null ? this.topic != null : ! value.equals(this.topic))
      {
         String oldValue = this.topic;
         this.topic = value;
         firePropertyChange("topic", oldValue, value);
      }
      return this;
   }


   private SEGroup group = null;

   public SEGroup getGroup()
   {
      return this.group;
   }

   public SEClass setGroup(SEGroup value)
   {
      if (this.group != value)
      {
         SEGroup oldValue = this.group;
         if (this.group != null)
         {
            this.group = null;
            oldValue.withoutClasses(this);
         }
         this.group = value;
         if (value != null)
         {
            value.withClasses(this);
         }
         firePropertyChange("group", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<Assignment> EMPTY_assignments = new java.util.ArrayList<Assignment>()
   { @Override public boolean add(Assignment value){ throw new UnsupportedOperationException("No direct add! Use xy.withAssignments(obj)"); }};


   private java.util.ArrayList<Assignment> assignments = null;

   public java.util.ArrayList<Assignment> getAssignments()
   {
      if (this.assignments == null)
      {
         return EMPTY_assignments;
      }

      return this.assignments;
   }

   public SEClass withAssignments(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withAssignments(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.assignments == null)
            {
               this.assignments = new java.util.ArrayList<Assignment>();
            }
            if ( ! this.assignments.contains(item))
            {
               this.assignments.add((Assignment)item);
               ((Assignment)item).setSeClass(this);
               firePropertyChange("assignments", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEClass withoutAssignments(Object... value)
   {
      if (this.assignments == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutAssignments(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.assignments.contains(item))
            {
               this.assignments.remove((Assignment)item);
               ((Assignment)item).setSeClass(null);
               firePropertyChange("assignments", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<Achievement> EMPTY_participations = new java.util.ArrayList<Achievement>()
   { @Override public boolean add(Achievement value){ throw new UnsupportedOperationException("No direct add! Use xy.withParticipations(obj)"); }};


   private java.util.ArrayList<Achievement> participations = null;

   public java.util.ArrayList<Achievement> getParticipations()
   {
      if (this.participations == null)
      {
         return EMPTY_participations;
      }

      return this.participations;
   }

   public SEClass withParticipations(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withParticipations(i);
            }
         }
         else if (item instanceof Achievement)
         {
            if (this.participations == null)
            {
               this.participations = new java.util.ArrayList<Achievement>();
            }
            if ( ! this.participations.contains(item))
            {
               this.participations.add((Achievement)item);
               ((Achievement)item).setSeClass(this);
               firePropertyChange("participations", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEClass withoutParticipations(Object... value)
   {
      if (this.participations == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutParticipations(i);
            }
         }
         else if (item instanceof Achievement)
         {
            if (this.participations.contains(item))
            {
               this.participations.remove((Achievement)item);
               ((Achievement)item).setSeClass(null);
               firePropertyChange("participations", item, null);
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

      result.append(" ").append(this.getTopic());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setGroup(null);

      this.withoutAssignments(this.getAssignments().clone());


      this.withoutParticipations(this.getParticipations().clone());


   }


}