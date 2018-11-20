package uniks.accounting.theorygroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Presentation 
{

   public static final String PROPERTY_slides = "slides";

   private int slides;

   public int getSlides()
   {
      return slides;
   }

   public Presentation setSlides(int value)
   {
      if (value != this.slides)
      {
         int oldValue = this.slides;
         this.slides = value;
         firePropertyChange("slides", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_scolarship = "scolarship";

   private int scolarship;

   public int getScolarship()
   {
      return scolarship;
   }

   public Presentation setScolarship(int value)
   {
      if (value != this.scolarship)
      {
         int oldValue = this.scolarship;
         this.scolarship = value;
         firePropertyChange("scolarship", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_content = "content";

   private int content;

   public int getContent()
   {
      return content;
   }

   public Presentation setContent(int value)
   {
      if (value != this.content)
      {
         int oldValue = this.content;
         this.content = value;
         firePropertyChange("content", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_total = "total";

   private int total;

   public int getTotal()
   {
      return total;
   }

   public Presentation setTotal(int value)
   {
      if (value != this.total)
      {
         int oldValue = this.total;
         this.total = value;
         firePropertyChange("total", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_grade = "grade";

   private String grade;

   public String getGrade()
   {
      return grade;
   }

   public Presentation setGrade(String value)
   {
      if (value == null ? this.grade != null : ! value.equals(this.grade))
      {
         String oldValue = this.grade;
         this.grade = value;
         firePropertyChange("grade", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_officeStatus = "officeStatus";

   private String officeStatus;

   public String getOfficeStatus()
   {
      return officeStatus;
   }

   public Presentation setOfficeStatus(String value)
   {
      if (value == null ? this.officeStatus != null : ! value.equals(this.officeStatus))
      {
         String oldValue = this.officeStatus;
         this.officeStatus = value;
         firePropertyChange("officeStatus", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_student = "student";

   private TheoryStudent student = null;

   public TheoryStudent getStudent()
   {
      return this.student;
   }

   public Presentation setStudent(TheoryStudent value)
   {
      if (this.student != value)
      {
         TheoryStudent oldValue = this.student;
         if (this.student != null)
         {
            this.student = null;
            oldValue.withoutPresentations(this);
         }
         this.student = value;
         if (value != null)
         {
            value.withPresentations(this);
         }
         firePropertyChange("student", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_seminar = "seminar";

   private Seminar seminar = null;

   public Seminar getSeminar()
   {
      return this.seminar;
   }

   public Presentation setSeminar(Seminar value)
   {
      if (this.seminar != value)
      {
         Seminar oldValue = this.seminar;
         if (this.seminar != null)
         {
            this.seminar = null;
            oldValue.withoutPresentations(this);
         }
         this.seminar = value;
         if (value != null)
         {
            value.withPresentations(this);
         }
         firePropertyChange("seminar", oldValue, value);
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

      result.append(" ").append(this.getGrade());
      result.append(" ").append(this.getOfficeStatus());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setStudent(null);
      this.setSeminar(null);

   }


}