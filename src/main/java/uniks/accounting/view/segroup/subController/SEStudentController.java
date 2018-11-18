package uniks.accounting.view.segroup.subController;

import uniks.accounting.segroup.Achievement;
import uniks.accounting.segroup.SEStudent;
import uniks.accounting.view.segroup.subView.OfficeTreeItem;

import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

public class SEStudentController implements SubController {

    private OfficeTreeItem view;
    private SEStudent student;

    private OfficeTreeItem achievements;

    public SEStudentController(OfficeTreeItem view, SEStudent student) {
        this.view = view;
        this.student = student;
    }

    public void init() {
        this.achievements = new OfficeTreeItem("Achievements");

        this.view.getChildren().add(this.achievements);
        this.view.setExpanded(true);

        modelView.put(this.achievements.getId(), this);

        this.addModelListener();
    }

    private void addModelListener() {
        this.student.addPropertyChangeListener(SEStudent.PROPERTY_studentId, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(student.getGroup().getStudents().size() + ". " + student.getStudentId());
            }
        });

        this.student.addPropertyChangeListener(SEStudent.PROPERTY_achievements, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                Achievement achiev = (Achievement) evt.getNewValue();
                String achievText = this.student.getAchievements().size() + ". ";
                if (achiev.getSeClass() != null) {
                    achievText += achiev.getSeClass().getTopic();
                }

                if (achiev.getOfficeStatus() != null) {
                    achievText += achiev.getOfficeStatus() + " - ";
                }

                if (achiev.getGrade() != null) {
                    achievText += achiev.getGrade();
                }
                OfficeTreeItem newAchievement = new OfficeTreeItem(achievText);

                AchievementController con = new AchievementController(newAchievement, achiev);
                con.init();

                modelView.put(newAchievement.getId(), con);

                this.achievements.getChildren().add(newAchievement);
            }
        });
    }

    @Override
    public Object getModel() {
        return this.student;
    }
}
