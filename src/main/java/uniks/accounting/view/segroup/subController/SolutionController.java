package uniks.accounting.view.segroup.subController;

import uniks.accounting.segroup.Solution;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;

public class SolutionController implements SubController {
    
    private OfficeTreeItem view;
    private Solution solution;
    
    SolutionController(OfficeTreeItem view, Solution solution) {
        this.view = view;
        this.solution = solution;
    }
    
    public void init() {
        this.addModelLister();
    }
    
    private void addModelLister() {
        solution.addPropertyChangeListener(Solution.PROPERTY_gitUrl, evt -> {
           if (evt.getNewValue() != null) {
               view.setValue(evt.getNewValue() + " - " + solution.getPoints());
           }
        });
        
        solution.addPropertyChangeListener(Solution.PROPERTY_points, evt -> {
            if (evt.getNewValue() != null) {
                view.setValue(solution.getGitUrl() + " - " + evt.getNewValue());
            }
        });
    }

    @Override
    public Object getModel() {
        return this.solution;
    }
}
