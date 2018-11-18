package uniks.accounting.view.segroup.subController;

import uniks.accounting.segroup.Achievement;
import uniks.accounting.segroup.Assignment;
import uniks.accounting.segroup.SEClass;
import uniks.accounting.view.segroup.subView.OfficeTreeItem;

import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

public class SEClassController implements SubController {

    private OfficeTreeItem view;
    private SEClass seClass;

    private OfficeTreeItem assignments;
    private OfficeTreeItem achievements;

    public SEClassController(OfficeTreeItem view, SEClass seClass) {
        this.view = view;
        this.seClass = seClass;
    }

    public void init() {
        this.assignments = new OfficeTreeItem("Assignments");
        this.achievements = new OfficeTreeItem("Achievements");

        this.view.getChildren().add(this.assignments);
        this.view.getChildren().add(this.achievements);
        this.view.setExpanded(true);

        modelView.put(this.assignments.getId(), this);
        modelView.put(this.achievements.getId(), this);

        this.addModelListener();
    }

    private void addModelListener() {
        this.seClass.addPropertyChangeListener(SEClass.PROPERTY_topic, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(seClass.getTopic() + " - " + seClass.getTerm());
            }
        });

        this.seClass.addPropertyChangeListener(SEClass.PROPERTY_term, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(seClass.getTopic() + " - " + seClass.getTerm());
            }
        });

        this.seClass.addPropertyChangeListener(SEClass.PROPERTY_assignments, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                Assignment assig = (Assignment) evt.getNewValue();
                OfficeTreeItem newAssignment = new OfficeTreeItem(assig.getTask() + " - " + assig.getPoints());

                AssignmentController con = new AssignmentController(newAssignment, assig);
                con.init();

                modelView.put(newAssignment.getId(), con);

                this.assignments.getChildren().add(newAssignment);
            }
        });

        this.seClass.addPropertyChangeListener(SEClass.PROPERTY_participations, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                Achievement achiev = (Achievement) evt.getNewValue();
                String achievText = achiev.getSeClass().getParticipations().size() + ". ";
                if (achiev.getStudent() != null) {
                    achievText += achiev.getStudent().getStudentId();
                }

                if (achiev.getOfficeStatus() != null) {
                    achievText += ", " + achiev.getOfficeStatus();
                }

                if (achiev.getGrade() != null) {
                    achievText += " - " + achiev.getGrade();
                }

                OfficeTreeItem newAchievement = new OfficeTreeItem(achievText);
                this.achievements.getChildren().add(newAchievement);
            }
        });
    }

    @Override
    public Object getModel() {
        return this.seClass;
    }
}
