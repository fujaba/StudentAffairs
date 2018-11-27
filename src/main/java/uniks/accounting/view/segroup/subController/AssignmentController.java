package uniks.accounting.view.segroup.subController;

import uniks.accounting.segroup.Assignment;
import uniks.accounting.segroup.Solution;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

public class AssignmentController implements SubController {

    private OfficeTreeItem view;
    private Assignment assignment;

    private OfficeTreeItem solutions;

    public AssignmentController(OfficeTreeItem view, Assignment assignment) {
        this.view = view;
        this.assignment = assignment;
    }

    public void init() {
        this.solutions = new OfficeTreeItem("Solutions");

        this.view.getChildren().add(this.solutions);
        this.view.setExpanded(true);

        modelView.put(this.solutions.getId(), this);

        this.addModelListener();
    }

    private void addModelListener() {
        this.assignment.addPropertyChangeListener(Assignment.PROPERTY_task, evt -> {
           if (evt.getNewValue() != null) {
               this.view.setValue(evt.getNewValue() + " - " + this.assignment.getPoints());
           }
        });

        this.assignment.addPropertyChangeListener(Assignment.PROPERTY_points, evt -> {
            if (evt.getNewValue() != null) {
                this.view.setValue(this.assignment.getTask() + " - " + evt.getNewValue());
            }
        });

        this.assignment.addPropertyChangeListener(Assignment.PROPERTY_solutions, evt -> {
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
        return this.assignment;
    }
}
