package uniks.accounting.view.studentOffice.subController;

import uniks.accounting.studentOffice.Enrollment;
import uniks.accounting.studentOffice.Examination;
import uniks.accounting.studentOffice.Lecturer;
import uniks.accounting.view.studentOffice.subView.OfficeTreeItem;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.modelView;

public class ExaminationController implements SubController {
    
    private OfficeTreeItem view;
    private Examination exam;
    
    private OfficeTreeItem lecturer;
    private OfficeTreeItem enrollments;
    
    public ExaminationController(OfficeTreeItem view, Examination exam) {
        this.view = view;
        this.exam = exam;
    }
    
    public void init() {
        this.lecturer = new OfficeTreeItem("Lecturer - " + exam.getLecturer().getName());
        this.enrollments = new OfficeTreeItem("Enrollments");
        
        this.view.getChildren().add(this.lecturer);
        this.view.getChildren().add(this.enrollments);
        this.view.setExpanded(true);
        
        modelView.put(this.lecturer.getId(), this);
        modelView.put(this.enrollments.getId(), this);
        
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.exam.addPropertyChangeListener(Examination.PROPERTY_date, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue((this.exam.getTopic().getExams().indexOf(this.exam) + 1) + " - " + evt.getNewValue());
            }
        });
        
        this.exam.addPropertyChangeListener(Examination.PROPERTY_lecturer, evt -> {
           if (evt.getNewValue() != null && evt.getOldValue() != null) {
               Lecturer newLecturer = (Lecturer) evt.getNewValue();
               this.lecturer.setValue("Lecturer - " + newLecturer.getName());
           }
        });
        
        this.exam.addPropertyChangeListener(Examination.PROPERTY_enrollments, evt -> {
           if (evt.getNewValue() != null && evt.getOldValue() == null) {
               Enrollment enroll = (Enrollment) evt.getNewValue();
               OfficeTreeItem newEnroll = new OfficeTreeItem(enroll.getStudent().getName() + " - " + enroll.getStudent().getStudentId());
               this.enrollments.getChildren().add(newEnroll);
           }
        });
    }

    @Override
    public Object getModel() {
        return this.exam;
    }
}
