package uniks.accounting.view.segroup.subController;

import uniks.accounting.segroup.Achievement;
import uniks.accounting.segroup.Solution;
import uniks.accounting.view.segroup.subView.OfficeTreeItem;

import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

public class AchievementController implements SubController {

    private OfficeTreeItem view;
    private Achievement achievement;

    private OfficeTreeItem solutions;

    public AchievementController(OfficeTreeItem view, Achievement achievement) {
        this.view = view;
        this.achievement = achievement;
    }

    public void init() {
        this.solutions = new OfficeTreeItem("Solutions");

        this.view.getChildren().add(this.solutions);
        this.view.setExpanded(true);

        modelView.put(this.solutions.getId(), this);

        this.addModelListener();
    }

    private void addModelListener() {
        this.achievement.addPropertyChangeListener(Achievement.PROPERTY_grade, evt -> {
           if (evt.getNewValue() != null) {
               String achievText = this.achievement.getSeClass().getParticipations().size() + ". ";
               if (this.achievement.getSeClass() != null) {
                   achievText += this.achievement.getSeClass().getTopic();
               }

               if (this.achievement.getOfficeStatus() != null) {
                   achievText += ", " + this.achievement.getOfficeStatus();
               }
               achievText += " - " + evt.getNewValue();
               this.view.setValue(achievText);
           }
        });

        this.achievement.addPropertyChangeListener(Achievement.PROPERTY_officeStatus, evt -> {
            if (evt.getNewValue() != null) {
                String achievText = this.achievement.getSeClass().getParticipations().size() + ". ";
                if (this.achievement.getSeClass() != null) {
                    achievText += this.achievement.getSeClass().getTopic();
                }
                achievText += ", " + evt.getNewValue();

                if (this.achievement.getGrade() != null) {
                    achievText += " - " + this.achievement.getGrade();
                }
                this.view.setValue(achievText);
            }
        });

        this.achievement.addPropertyChangeListener(Achievement.PROPERTY_solutions, evt -> {
            if (evt.getNewValue() != null && evt.getOldValue() == null) {
                Solution solution = (Solution) evt.getNewValue();
                OfficeTreeItem newSolution = new OfficeTreeItem(solution.getGitUrl() + " - " + solution.getPoints());

                SolutionController con = new SolutionController(newSolution, solution);
                con.init();

                modelView.put(newSolution.getId(), con);
                
                this.solutions.getChildren().add(newSolution);
            }
        });
    }

    @Override
    public Object getModel() {
        return this.achievement;
    }
}
