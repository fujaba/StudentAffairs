package uniks.accounting.view.theorygroup.subController;

import uniks.accounting.theorygroup.Presentation;
import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.theorygroup.TheoryStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

import java.beans.PropertyChangeEvent;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.modelView;

public class PresentationController implements SubController {
    
    private OfficeTreeItem view;
    private Presentation presentation;
    
    public PresentationController(OfficeTreeItem view, Presentation presentation) {
        this.view = view;
        this.presentation = presentation;
    }
    
    public void init() {
        this.addModelListener();
    }
    
    private void addModelListener() {
        this.presentation.addPropertyChangeListener(Presentation.PROPERTY_content, this::refreshPresentation);
        this.presentation.addPropertyChangeListener(Presentation.PROPERTY_grade, this::refreshPresentation);
        this.presentation.addPropertyChangeListener(Presentation.PROPERTY_officeStatus, this::refreshPresentation);
        this.presentation.addPropertyChangeListener(Presentation.PROPERTY_scholarship, this::refreshPresentation);
        this.presentation.addPropertyChangeListener(Presentation.PROPERTY_slides, this::refreshPresentation);
        this.presentation.addPropertyChangeListener(Presentation.PROPERTY_total, this::refreshPresentation);
    }
    
    private void refreshPresentation(PropertyChangeEvent evt) {
        if (evt.getNewValue() != null) {
            String presText = "";
            Object parent = modelView.get(((OfficeTreeItem)this.view.getParent()).getId()).getModel();
            if (parent instanceof Seminar) {
                presText += (this.presentation.getSeminar().getPresentations().indexOf(this.presentation) + 1) + ". ";    
            } else if (parent instanceof TheoryStudent) {
                presText += (this.presentation.getStudent().getPresentations().indexOf(this.presentation) + 1) + ". ";
            }
            presText += this.presentation.getSeminar().getTopic();
            presText += " (" + this.presentation.getSlides() + ", " + this.presentation.getScholarship() + ", " + this.presentation.getContent() + ", " + this.presentation.getTotal() + ")";

            if (this.presentation.getOfficeStatus() != null) {
                presText += ", " + this.presentation.getOfficeStatus();
            }

            if (this.presentation.getGrade() != null) {
                presText += " - " + this.presentation.getGrade();
            }
            this.view.setValue(presText);
        }
    }

    @Override
    public Object getModel() {
        return this.presentation;
    }
}
