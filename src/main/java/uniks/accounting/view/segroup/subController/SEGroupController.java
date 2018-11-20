package uniks.accounting.view.segroup.subController;

import uniks.accounting.segroup.SEClass;
import uniks.accounting.segroup.SEGroup;
import uniks.accounting.segroup.SEStudent;
import uniks.accounting.view.segroup.subView.OfficeTreeItem;

import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

public class SEGroupController implements SubController {
    
    private OfficeTreeItem view;
    private SEGroup group;
    
    private OfficeTreeItem groupClasses;
    private OfficeTreeItem groupStudents;
    
    public SEGroupController(OfficeTreeItem view, SEGroup group) {
        this.view = view;
        this.group = group;
    }
    
    public void init() {
        this.groupClasses = new OfficeTreeItem("Classes");
        this.groupStudents = new OfficeTreeItem("Students");
        
        this.view.getChildren().add(this.groupClasses);
        this.view.getChildren().add(this.groupStudents);
        this.view.setExpanded(true);
        
        modelView.put(this.groupClasses.getId(), this);
        modelView.put(this.groupStudents.getId(), this);
        
        this.addModelListener();
    }

    private void addModelListener() {
        this.group.addPropertyChangeListener(SEGroup.PROPERTY_head, evt -> {
           if (evt.getNewValue() != null) {
               this.view.setValue("SE Group - " + evt.getNewValue());
           }
        });
        
        this.group.addPropertyChangeListener(SEGroup.PROPERTY_classes, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                SEClass seClass = (SEClass) evt.getNewValue();
                OfficeTreeItem newClass = new OfficeTreeItem(seClass.getTopic() + " - " + seClass.getTerm());

                SEClassController con = new SEClassController(newClass, seClass);
                con.init();

                modelView.put(newClass.getId(), con);

                this.groupClasses.getChildren().add(newClass);
            }
        });
        
        this.group.addPropertyChangeListener(SEGroup.PROPERTY_students, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                SEStudent student = (SEStudent) evt.getNewValue();
                OfficeTreeItem newStudent = new OfficeTreeItem(student.getGroup().getStudents().size() + ". " + student.getStudentId());

                SEStudentController con = new SEStudentController(newStudent, student);
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
