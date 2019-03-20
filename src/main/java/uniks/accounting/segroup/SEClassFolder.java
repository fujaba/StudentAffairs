package uniks.accounting.segroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SEClassFolder 
{

   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public SEClassFolder setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_group = "group";

   private SEGroup group = null;

   public SEGroup getGroup()
   {
      return this.group;
   }

   public SEClassFolder setGroup(SEGroup value)
   {
      if (this.group != value)
      {
         SEGroup oldValue = this.group;
         if (this.group != null)
         {
            this.group = null;
            oldValue.withoutClassFolder(this);
         }
         this.group = value;
         if (value != null)
         {
            value.withClassFolder(this);
         }
         firePropertyChange("group", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<SEClassFolder> EMPTY_subFolders = new java.util.ArrayList<SEClassFolder>()
   { @Override public boolean add(SEClassFolder value){ throw new UnsupportedOperationException("No direct add! Use xy.withSubFolders(obj)"); }};


   public static final String PROPERTY_subFolders = "subFolders";

   private java.util.ArrayList<SEClassFolder> subFolders = null;

   public java.util.ArrayList<SEClassFolder> getSubFolders()
   {
      if (this.subFolders == null)
      {
         return EMPTY_subFolders;
      }

      return this.subFolders;
   }

   public SEClassFolder withSubFolders(Object... value)
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
         else if (item instanceof SEClassFolder)
         {
            if (this.subFolders == null)
            {
               this.subFolders = new java.util.ArrayList<SEClassFolder>();
            }
            if ( ! this.subFolders.contains(item))
            {
               this.subFolders.add((SEClassFolder)item);
               ((SEClassFolder)item).setParent(this);
               firePropertyChange("subFolders", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEClassFolder withoutSubFolders(Object... value)
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
         else if (item instanceof SEClassFolder)
         {
            if (this.subFolders.contains(item))
            {
               this.subFolders.remove((SEClassFolder)item);
               ((SEClassFolder)item).setParent(null);
               firePropertyChange("subFolders", item, null);
            }
         }
      }
      return this;
   }


   public static final String PROPERTY_parent = "parent";

   private SEClassFolder parent = null;

   public SEClassFolder getParent()
   {
      return this.parent;
   }

   public SEClassFolder setParent(SEClassFolder value)
   {
      if (this.parent != value)
      {
         SEClassFolder oldValue = this.parent;
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



   public static final java.util.ArrayList<SEClass> EMPTY_classes = new java.util.ArrayList<SEClass>()
   { @Override public boolean add(SEClass value){ throw new UnsupportedOperationException("No direct add! Use xy.withClasses(obj)"); }};


   public static final String PROPERTY_classes = "classes";

   private java.util.ArrayList<SEClass> classes = null;

   public java.util.ArrayList<SEClass> getClasses()
   {
      if (this.classes == null)
      {
         return EMPTY_classes;
      }

      return this.classes;
   }

   public SEClassFolder withClasses(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withClasses(i);
            }
         }
         else if (item instanceof SEClass)
         {
            if (this.classes == null)
            {
               this.classes = new java.util.ArrayList<SEClass>();
            }
            if ( ! this.classes.contains(item))
            {
               this.classes.add((SEClass)item);
               ((SEClass)item).setFolder(this);
               firePropertyChange("classes", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEClassFolder withoutClasses(Object... value)
   {
      if (this.classes == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutClasses(i);
            }
         }
         else if (item instanceof SEClass)
         {
            if (this.classes.contains(item))
            {
               this.classes.remove((SEClass)item);
               ((SEClass)item).setFolder(null);
               firePropertyChange("classes", item, null);
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

      result.append(" ").append(this.getName());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setGroup(null);
      this.setParent(null);

      this.withoutSubFolders(this.getSubFolders().clone());


      this.withoutClasses(this.getClasses().clone());


   }


}