package uniks.accounting.view.studentOffice.subController;

import uniks.accounting.studentOffice.Course;
import uniks.accounting.studentOffice.Examination;
import uniks.accounting.view.studentOffice.subView.OfficeTreeItem;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.modelView;

public class CourseController implements SubController {
    
    private OfficeTreeItem view;
    private Course course;
    
    private OfficeTreeItem examinations;
    
    public CourseController(OfficeTreeItem view, Course course ) {
        this.view = view;
        this.course = course;
    }
    
    public void init() {
        this.examinations = new OfficeTreeItem("Examinations");
        
        this.view.getChildren().add(this.examinations);
        this.view.setExpanded(true);
        
        modelView.put(this.examinations.getId(), this);
        
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.course.addPropertyChangeListener(Course.PROPERTY_title, evt -> {
           if (evt.getNewValue() != null) {
               this.view.setValue(evt.getNewValue() + "");
           }
        });
        
        this.course.addPropertyChangeListener(Course.PROPERTY_exams, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                Examination exam = (Examination) evt.getNewValue();
                OfficeTreeItem newExam = new OfficeTreeItem(this.course.getExams().size() + " - " + exam.getDate());
                
                ExaminationController con = new ExaminationController(newExam, exam);
                con.init();
                
                modelView.put(newExam.getId(), con);
                
                this.examinations.getChildren().add(newExam);
            }
        });
    }

    @Override
    public Object getModel() {
        return this.course;
    }
}
