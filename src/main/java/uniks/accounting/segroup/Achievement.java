package uniks.accounting.segroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Achievement  
{

   public static final String PROPERTY_grade = "grade";

   private String grade;

   public String getGrade()
   {
      return grade;
   }

   public Achievement setGrade(String value)
   {
      if (value == null ? this.grade != null : ! value.equals(this.grade))
      {
         String oldValue = this.grade;
         this.grade = value;
         firePropertyChange("grade", oldValue, value);
      }
      return this;
   }


   private SEClass seClass = null;

   public SEClass getSeClass()
   {
      return this.seClass;
   }

   public Achievement setSeClass(SEClass value)
   {
      if (this.seClass != value)
      {
         SEClass oldValue = this.seClass;
         if (this.seClass != null)
         {
            this.seClass = null;
            oldValue.withoutParticipations(this);
         }
         this.seClass = value;
         if (value != null)
         {
            value.withParticipations(this);
         }
         firePropertyChange("seClass", oldValue, value);
      }
      return this;
   }



   private SEStudent student = null;

   public SEStudent getStudent()
   {
      return this.student;
   }

   public Achievement setStudent(SEStudent value)
   {
      if (this.student != value)
      {
         SEStudent oldValue = this.student;
         if (this.student != null)
         {
            this.student = null;
            oldValue.withoutAchievements(this);
         }
         this.student = value;
         if (value != null)
         {
            value.withAchievements(this);
         }
         firePropertyChange("student", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<Solution> EMPTY_solutions = new java.util.ArrayList<Solution>()
   { @Override public boolean add(Solution value){ throw new UnsupportedOperationException("No direct add! Use xy.withSolutions(obj)"); }};


   private java.util.ArrayList<Solution> solutions = null;

   public java.util.ArrayList<Solution> getSolutions()
   {
      if (this.solutions == null)
      {
         return EMPTY_solutions;
      }

      return this.solutions;
   }

   public Achievement withSolutions(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withSolutions(i);
            }
         }
         else if (item instanceof Solution)
         {
            if (this.solutions == null)
            {
               this.solutions = new java.util.ArrayList<Solution>();
            }
            if ( ! this.solutions.contains(item))
            {
               this.solutions.add((Solution)item);
               ((Solution)item).setAchievement(this);
               firePropertyChange("solutions", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Achievement withoutSolutions(Object... value)
   {
      if (this.solutions == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutSolutions(i);
            }
         }
         else if (item instanceof Solution)
         {
            if (this.solutions.contains(item))
            {
               this.solutions.remove((Solution)item);
               ((Solution)item).setAchievement(null);
               firePropertyChange("solutions", item, null);
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
      this.setSeClass(null);
      this.setStudent(null);

      this.withoutSolutions(this.getSolutions().clone());


   }


   public static final String PROPERTY_officeStatus = "officeStatus";

   private String officeStatus;

   public String getOfficeStatus()
   {
      return officeStatus;
   }

   public Achievement setOfficeStatus(String value)
   {
      if (value == null ? this.officeStatus != null : ! value.equals(this.officeStatus))
      {
         String oldValue = this.officeStatus;
         this.officeStatus = value;
         firePropertyChange("officeStatus", oldValue, value);
      }
      return this;
   }


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getGrade());
      result.append(" ").append(this.getOfficeStatus());


      return result.substring(1);
   }

   public Solution getSolutions(Assignment assignment)
   {
      for (Solution s : this.getSolutions())
      {
         if (s.getAssignment() == assignment)
         {
            return s;
         }
      }

      return null;
   }
   public static final String PROPERTY_seClass = "seClass";

   public static final String PROPERTY_student = "student";

   public static final String PROPERTY_solutions = "solutions";

}