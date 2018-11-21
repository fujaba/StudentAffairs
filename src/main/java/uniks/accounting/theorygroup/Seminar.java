package uniks.accounting.theorygroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Seminar  
{

   public static final String PROPERTY_topic = "topic";

   private String topic;

   public String getTopic()
   {
      return topic;
   }

   public Seminar setTopic(String value)
   {
      if (value == null ? this.topic != null : ! value.equals(this.topic))
      {
         String oldValue = this.topic;
         this.topic = value;
         firePropertyChange("topic", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_term = "term";

   private String term;

   public String getTerm()
   {
      return term;
   }

   public Seminar setTerm(String value)
   {
      if (value == null ? this.term != null : ! value.equals(this.term))
      {
         String oldValue = this.term;
         this.term = value;
         firePropertyChange("term", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_group = "group";

   private TheoryGroup group = null;

   public TheoryGroup getGroup()
   {
      return this.group;
   }

   public Seminar setGroup(TheoryGroup value)
   {
      if (this.group != value)
      {
         TheoryGroup oldValue = this.group;
         if (this.group != null)
         {
            this.group = null;
            oldValue.withoutSeminars(this);
         }
         this.group = value;
         if (value != null)
         {
            value.withSeminars(this);
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

   public Seminar withPresentations(Object... value)
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
               ((Presentation)item).setSeminar(this);
               firePropertyChange("presentations", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Seminar withoutPresentations(Object... value)
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
               ((Presentation)item).setSeminar(null);
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

      result.append(" ").append(this.getTopic());
      result.append(" ").append(this.getTerm());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setGroup(null);

      this.withoutPresentations(this.getPresentations().clone());


   }


}