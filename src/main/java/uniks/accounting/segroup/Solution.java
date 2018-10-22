package uniks.accounting.segroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Solution  
{

   public static final String PROPERTY_gitUrl = "gitUrl";

   private String gitUrl;

   public String getGitUrl()
   {
      return gitUrl;
   }

   public Solution setGitUrl(String value)
   {
      if (value == null ? this.gitUrl != null : ! value.equals(this.gitUrl))
      {
         String oldValue = this.gitUrl;
         this.gitUrl = value;
         firePropertyChange("gitUrl", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_points = "points";

   private double points;

   public double getPoints()
   {
      return points;
   }

   public Solution setPoints(double value)
   {
      if (value != this.points)
      {
         double oldValue = this.points;
         this.points = value;
         firePropertyChange("points", oldValue, value);
      }
      return this;
   }


   private Achievement achievement = null;

   public Achievement getAchievement()
   {
      return this.achievement;
   }

   public Solution setAchievement(Achievement value)
   {
      if (this.achievement != value)
      {
         Achievement oldValue = this.achievement;
         if (this.achievement != null)
         {
            this.achievement = null;
            oldValue.withoutSolutions(this);
         }
         this.achievement = value;
         if (value != null)
         {
            value.withSolutions(this);
         }
         firePropertyChange("achievement", oldValue, value);
      }
      return this;
   }



   private Assignment assignment = null;

   public Assignment getAssignment()
   {
      return this.assignment;
   }

   public Solution setAssignment(Assignment value)
   {
      if (this.assignment != value)
      {
         Assignment oldValue = this.assignment;
         if (this.assignment != null)
         {
            this.assignment = null;
            oldValue.withoutSolutions(this);
         }
         this.assignment = value;
         if (value != null)
         {
            value.withSolutions(this);
         }
         firePropertyChange("assignment", oldValue, value);
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

      result.append(" ").append(this.getGitUrl());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setAchievement(null);
      this.setAssignment(null);

   }


}