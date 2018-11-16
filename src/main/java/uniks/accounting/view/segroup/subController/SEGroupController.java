package uniks.accounting.view.segroup.subController;

import uniks.accounting.segroup.SEGroup;
import uniks.accounting.view.segroup.subView.OfficeTreeItem;

import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

public class SEGroupController implements SubController {
    
    private OfficeTreeItem view;
    private SEGroup group;
    
    OfficeTreeItem groupClasses;
    OfficeTreeItem groupStudents;
    
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
                
            }
        });
        
        this.group.addPropertyChangeListener(SEGroup.PROPERTY_students, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                
            }
        });
    }
    
    @Override
    public Object getModel() {
        return this.group;
    }
}
