package uniks.accounting.view.theorygroup.subController;

import uniks.accounting.theorygroup.Presentation;
import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.modelView;

public class SeminarController implements SubController {
    
    private OfficeTreeItem view;
    private Seminar seminar;
    
    private OfficeTreeItem presentations;
    
    public SeminarController(OfficeTreeItem view, Seminar seminar) {
        this.view = view;
        this.seminar = seminar;
    }
    
    public void init() {
        this.presentations = new OfficeTreeItem("Presentations");
        
        this.view.getChildren().add(this.presentations);
        this.view.setExpanded(true);

        modelView.put(this.presentations.getId(), this);
        
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.seminar.addPropertyChangeListener(Seminar.PROPERTY_topic, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(evt.getNewValue() + " - " + seminar.getTerm());
            }
        });
        
        this.seminar.addPropertyChangeListener(Seminar.PROPERTY_term, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(seminar.getTopic() + " - " + evt.getNewValue());
            }
        });
        
        this.seminar.addPropertyChangeListener(Seminar.PROPERTY_presentations, evt -> { 
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                Presentation presentation = (Presentation) evt.getNewValue();
                String presText = this.seminar.getPresentations().size() + ". ";
                presText += seminar.getTopic();
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
        return this.seminar;
    }
}
