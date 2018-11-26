package uniks.accounting.view.theorygroup.subController;

import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.theorygroup.TheoryGroup;
import uniks.accounting.theorygroup.TheoryStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.modelView;

public class TheoryGroupController implements SubController {

    private OfficeTreeItem view;
    private TheoryGroup group;
    
    private OfficeTreeItem groupSeminars;
    private OfficeTreeItem groupStudents;
    
    public TheoryGroupController(OfficeTreeItem view, TheoryGroup group) {
        this.view = view;
        this.group = group;
    }
    
    public void init() {
        this.groupSeminars = new OfficeTreeItem("Seminars");
        this.groupStudents = new OfficeTreeItem("Students");
        
        this.view.getChildren().add(this.groupSeminars);
        this.view.getChildren().add(this.groupStudents);
        this.view.setExpanded(true);
        
        modelView.put(this.groupSeminars.getId(), this);
        modelView.put(this.groupStudents.getId(), this);
        
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.group.addPropertyChangeListener(TheoryGroup.PROPERTY_head, evt -> {
           if (evt.getNewValue() != null) {
               this.view.setValue("Theory Group - " + evt.getNewValue());
           }
        });
        
        this.group.addPropertyChangeListener(TheoryGroup.PROPERTY_seminars, evt -> {
           if (evt.getNewValue() != null && evt.getOldValue() == null) {
               Seminar seminar = (Seminar) evt.getNewValue();
               OfficeTreeItem newSeminar = new OfficeTreeItem(seminar.getTopic() + " - " + seminar.getTerm());
               
               SeminarController con = new SeminarController(newSeminar, seminar);
               con.init();
               
               modelView.put(newSeminar.getId(), con);
               
               this.groupSeminars.getChildren().add(newSeminar);
           }
        });
        
        this.group.addPropertyChangeListener(TheoryGroup.PROPERTY_students, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                TheoryStudent student = (TheoryStudent) evt.getNewValue();
                OfficeTreeItem newStudent = new OfficeTreeItem(student.getGroup().getStudents().size() + ". " + student.getStudentId());
                
                TheoryStudentController con = new TheoryStudentController(newStudent, student);
                con.init();
                
                modelView.put(newStudent.getId(), con);
                
                this.groupStudents.getChildren().add(newStudent);                
            }
        });
    }

    @Override
    public Object getModel() {
        return this.group;
    }
}
