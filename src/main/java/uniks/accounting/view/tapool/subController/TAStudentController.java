package uniks.accounting.view.tapool.subController;

import uniks.accounting.tapool.TAStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

public class TAStudentController implements SubController {
    
    private OfficeTreeItem view;
    private TAStudent student;
    
    public TAStudentController(OfficeTreeItem view, TAStudent student) {
        this.view = view;
        this.student = student;
    }
    
    public void init() {
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.student.addPropertyChangeListener(TAStudent.PROPERTY_teachingAssistantFor, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(
                    (student.getPool().getStudents().indexOf(student) + 1) + ". " +
                    student.getStudentId() + " - " +
                    student.getName() + " :: " +
                    evt.getNewValue());
            }
        });
    }

    @Override
    public Object getModel() {
        return this.student;
    }
}
