package uniks.accounting.segroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SolutionFolder 
{

   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public SolutionFolder setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final java.util.ArrayList<Solution> EMPTY_solutions = new java.util.ArrayList<Solution>()
   { @Override public boolean add(Solution value){ throw new UnsupportedOperationException("No direct add! Use xy.withSolutions(obj)"); }};


   public static final String PROPERTY_solutions = "solutions";

   private java.util.ArrayList<Solution> solutions = null;

   public java.util.ArrayList<Solution> getSolutions()
   {
      if (this.solutions == null)
      {
         return EMPTY_solutions;
      }

      return this.solutions;
   }

   public SolutionFolder withSolutions(Object... value)
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
               ((Solution)item).setFolder(this);
               firePropertyChange("solutions", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SolutionFolder withoutSolutions(Object... value)
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
               ((Solution)item).setFolder(null);
               firePropertyChange("solutions", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<SolutionFolder> EMPTY_subFolders = new java.util.ArrayList<SolutionFolder>()
   { @Override public boolean add(SolutionFolder value){ throw new UnsupportedOperationException("No direct add! Use xy.withSubFolders(obj)"); }};


   public static final String PROPERTY_subFolders = "subFolders";

   private java.util.ArrayList<SolutionFolder> subFolders = null;

   public java.util.ArrayList<SolutionFolder> getSubFolders()
   {
      if (this.subFolders == null)
      {
         return EMPTY_subFolders;
      }

      return this.subFolders;
   }

   public SolutionFolder withSubFolders(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withSubFolders(i);
            }
         }
         else if (item instanceof SolutionFolder)
         {
            if (this.subFolders == null)
            {
               this.subFolders = new java.util.ArrayList<SolutionFolder>();
            }
            if ( ! this.subFolders.contains(item))
            {
               this.subFolders.add((SolutionFolder)item);
               ((SolutionFolder)item).setParent(this);
               firePropertyChange("subFolders", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SolutionFolder withoutSubFolders(Object... value)
   {
      if (this.subFolders == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutSubFolders(i);
            }
         }
         else if (item instanceof SolutionFolder)
         {
            if (this.subFolders.contains(item))
            {
               this.subFolders.remove((SolutionFolder)item);
               ((SolutionFolder)item).setParent(null);
               firePropertyChange("subFolders", item, null);
            }
         }
      }
      return this;
   }


   public static final String PROPERTY_parent = "parent";

   private SolutionFolder parent = null;

   public SolutionFolder getParent()
   {
      return this.parent;
   }

   public SolutionFolder setParent(SolutionFolder value)
   {
      if (this.parent != value)
      {
         SolutionFolder oldValue = this.parent;
         if (this.parent != null)
         {
            this.parent = null;
            oldValue.withoutSubFolders(this);
         }
         this.parent = value;
         if (value != null)
         {
            value.withSubFolders(this);
         }
         firePropertyChange("parent", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_assignment = "assignment";

   private Assignment assignment = null;

   public Assignment getAssignment()
   {
      return this.assignment;
   }

   public SolutionFolder setAssignment(Assignment value)
   {
      if (this.assignment != value)
      {
         Assignment oldValue = this.assignment;
         if (this.assignment != null)
         {
            this.assignment = null;
            oldValue.setSolutionFolder(null);
         }
         this.assignment = value;
         if (value != null)
         {
            value.setSolutionFolder(this);
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

      result.append(" ").append(this.getName());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setParent(null);
      this.setAssignment(null);

      this.withoutSolutions(this.getSolutions().clone());


      this.withoutSubFolders(this.getSubFolders().clone());


   }


}