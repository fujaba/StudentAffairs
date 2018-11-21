package uniks.accounting.tapool;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class TAStudent 
{

   public static final String PROPERTY_studentId = "studentId";

   private String studentId;

   public String getStudentId()
   {
      return studentId;
   }

   public TAStudent setStudentId(String value)
   {
      if (value == null ? this.studentId != null : ! value.equals(this.studentId))
      {
         String oldValue = this.studentId;
         this.studentId = value;
         firePropertyChange("studentId", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public TAStudent setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_teachingAssistantFor = "teachingAssistantFor";

   private String teachingAssistantFor;

   public String getTeachingAssistantFor()
   {
      return teachingAssistantFor;
   }

   public TAStudent setTeachingAssistantFor(String value)
   {
      if (value == null ? this.teachingAssistantFor != null : ! value.equals(this.teachingAssistantFor))
      {
         String oldValue = this.teachingAssistantFor;
         this.teachingAssistantFor = value;
         firePropertyChange("teachingAssistantFor", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_pool = "pool";

   private TAPool pool = null;

   public TAPool getPool()
   {
      return this.pool;
   }

   public TAStudent setPool(TAPool value)
   {
      if (this.pool != value)
      {
         TAPool oldValue = this.pool;
         if (this.pool != null)
         {
            this.pool = null;
            oldValue.withoutStudents(this);
         }
         this.pool = value;
         if (value != null)
         {
            value.withStudents(this);
         }
         firePropertyChange("pool", oldValue, value);
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
      result.append(" ").append(this.getName());
      result.append(" ").append(this.getTeachingAssistantFor());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setPool(null);

   }


}