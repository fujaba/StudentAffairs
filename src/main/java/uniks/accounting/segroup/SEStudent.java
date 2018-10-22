package uniks.accounting.segroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SEStudent  
{

   public static final String PROPERTY_studentId = "studentId";

   private String studentId;

   public String getStudentId()
   {
      return studentId;
   }

   public SEStudent setStudentId(String value)
   {
      if (value == null ? this.studentId != null : ! value.equals(this.studentId))
      {
         String oldValue = this.studentId;
         this.studentId = value;
         firePropertyChange("studentId", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<Achievement> EMPTY_achievements = new java.util.ArrayList<Achievement>()
   { @Override public boolean add(Achievement value){ throw new UnsupportedOperationException("No direct add! Use xy.withAchievements(obj)"); }};


   private java.util.ArrayList<Achievement> achievements = null;

   public java.util.ArrayList<Achievement> getAchievements()
   {
      if (this.achievements == null)
      {
         return EMPTY_achievements;
      }

      return this.achievements;
   }

   public SEStudent withAchievements(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withAchievements(i);
            }
         }
         else if (item instanceof Achievement)
         {
            if (this.achievements == null)
            {
               this.achievements = new java.util.ArrayList<Achievement>();
            }
            if ( ! this.achievements.contains(item))
            {
               this.achievements.add((Achievement)item);
               ((Achievement)item).setStudent(this);
               firePropertyChange("achievements", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEStudent withoutAchievements(Object... value)
   {
      if (this.achievements == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutAchievements(i);
            }
         }
         else if (item instanceof Achievement)
         {
            if (this.achievements.contains(item))
            {
               this.achievements.remove((Achievement)item);
               ((Achievement)item).setStudent(null);
               firePropertyChange("achievements", item, null);
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

      this.withoutAchievements(this.getAchievements().clone());


   }


   private SEGroup group = null;

   public SEGroup getGroup()
   {
      return this.group;
   }

   public SEStudent setGroup(SEGroup value)
   {
      if (this.group != value)
      {
         SEGroup oldValue = this.group;
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



}