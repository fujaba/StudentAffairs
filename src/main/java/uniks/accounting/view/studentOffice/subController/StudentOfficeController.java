package uniks.accounting.view.studentOffice.subController;

import uniks.accounting.studentOffice.Lecturer;
import uniks.accounting.studentOffice.StudentOffice;
import uniks.accounting.studentOffice.StudyProgram;
import uniks.accounting.view.studentOffice.subView.OfficeTreeItem;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.modelView;

public class StudentOfficeController implements SubController {
    
    private OfficeTreeItem view;
    private StudentOffice office;

    private OfficeTreeItem officeStudents;
    private OfficeTreeItem officeLecturer;
    private OfficeTreeItem programs;
    
    public StudentOfficeController(OfficeTreeItem view, StudentOffice office) {
        this.view = view;
        this.office = office;
    }
    
    public void init() {
        officeStudents = new OfficeTreeItem("Unassigned students");
        officeLecturer = new OfficeTreeItem("Lecturers");
        programs = new OfficeTreeItem("Study programs");
        
        this.view.getChildren().add(officeStudents);
        this.view.getChildren().add(officeLecturer);
        this.view.getChildren().add(programs);
        this.view.setExpanded(true);
        
        modelView.put(officeStudents.getId(), this);
        modelView.put(officeLecturer.getId(), this);
        modelView.put(programs.getId(), this);
        
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.office.addPropertyChangeListener(StudentOffice.PROPERTY_department, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue("Department - " + evt.getNewValue());
            }
        });
        
        this.office.addPropertyChangeListener("lecturers", evt -> {
           if (evt.getNewValue() != null && evt.getOldValue() == null) {
               Lecturer lec = (Lecturer) evt.getNewValue();
               OfficeTreeItem newLecturer = new OfficeTreeItem(lec.getName());
               this.officeLecturer.getChildren().add(newLecturer);
           }
        });
        
        this.office.addPropertyChangeListener("programs", evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                StudyProgram prog = (StudyProgram) evt.getNewValue();
                OfficeTreeItem newProgram = new OfficeTreeItem(prog.getSubject());
                this.programs.getChildren().add(newProgram);
            }
        });
    }

    @Override
    public Object getModel() {
        return this.office;
    }
}
