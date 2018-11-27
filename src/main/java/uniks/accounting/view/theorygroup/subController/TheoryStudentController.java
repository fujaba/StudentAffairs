package uniks.accounting.view.theorygroup.subController;

import uniks.accounting.theorygroup.Presentation;
import uniks.accounting.theorygroup.TheoryStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.modelView;

public class TheoryStudentController implements SubController {
    
    private OfficeTreeItem view;
    private TheoryStudent student;
    
    private OfficeTreeItem presentations;
    
    public TheoryStudentController(OfficeTreeItem view, TheoryStudent student) {
        this.view = view;
        this.student = student;
    }
    
    public void init() {
        this.presentations = new OfficeTreeItem("Presentations");
        
        this.view.getChildren().add(this.presentations);
        this.view.setExpanded(true);
        
        modelView.put(this.presentations.getId(), this);
        
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.student.addPropertyChangeListener(TheoryStudent.PROPERTY_studentId, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(student.getGroup().getStudents().size() + ". " + student.getStudentId());
            }
        });
        
        this.student.addPropertyChangeListener(TheoryStudent.PROPERTY_presentations, evt -> {
           if (evt.getNewValue() != null && evt.getOldValue() == null) {
               Presentation presentation = (Presentation) evt.getNewValue();
               String presText = this.student.getPresentations().size() + ". ";
               if (presentation.getSeminar() != null) {
                   presText += presentation.getSeminar().getTopic();
               }
               
               presText += " (" + presentation.getSlides() + ", " + presentation.getScholarship() + ", " + presentation.getContent() + ", " + presentation.getTotal() + ")";
               
               if (presentation.getOfficeStatus() != null) {
                   presText += ", " + presentation.getOfficeStatus();
               }
               
               if (presentation.getGrade() != null) {
                   presText += " - " + presentation.getGrade();
               }
               OfficeTreeItem newPresentation = new OfficeTreeItem(presText);
               
               PresentationController con = new PresentationController(newPresentation, presentation);
               con.init();
               
               modelView.put(newPresentation.getId(), con);
               
               this.presentations.getChildren().add(newPresentation);
           }
        });
    }

    @Override
    public Object getModel() {
        return this.student;
    }
}
