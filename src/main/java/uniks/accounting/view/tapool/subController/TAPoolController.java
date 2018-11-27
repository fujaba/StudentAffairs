package uniks.accounting.view.tapool.subController;

import uniks.accounting.tapool.TAPool;
import uniks.accounting.tapool.TAStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

import static uniks.accounting.view.tapool.TAPoolApplication.modelView;

public class TAPoolController implements SubController {
    
    private OfficeTreeItem view;
    private TAPool pool;
    
    private OfficeTreeItem students;
    
    public TAPoolController(OfficeTreeItem view, TAPool pool) {
        this.view = view;
        this.pool = pool;
    }
    
    public void init() {
        this.students = new OfficeTreeItem("Students");
        
        this.view.getChildren().add(students);
        this.view.setExpanded(true);
        
        modelView.put(this.students.getId(), this);
        
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.pool.addPropertyChangeListener(TAPool.PROPERTY_students, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                TAStudent student = (TAStudent) evt.getNewValue();
                OfficeTreeItem newStudent = new OfficeTreeItem(
                        student.getPool().getStudents().size() + ". " + 
                        student.getStudentId() + " - " + 
                        student.getName() + " :: " +
                        student.getTeachingAssistantFor());
                
                TAStudentController con = new TAStudentController(newStudent, student);
                con.init();
                
                modelView.put(newStudent.getId(), con);
                
                this.students.getChildren().add(newStudent);
            }
        });
    }

    @Override
    public Object getModel() {
        return this.pool;
    }
}
