package uniks.accounting.view.segroup;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import uniks.accounting.segroup.*;
import uniks.accounting.view.segroup.subController.SEGroupController;
import uniks.accounting.view.segroup.subController.SubController;
import uniks.accounting.view.segroup.subView.*;

import static uniks.accounting.view.segroup.SEGroupApplication.gb;
import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

public class MainController {
    
    private MainView view;
    
    public MainController(MainView view) {
        this.view = view;
    }
    
    public void init() {
        this.view.getCreateGroup().setOnAction(this::onCreateGroup);
        this.view.getUpdate().setOnAction(this::onUpdate);
    }
    
    private void onGroupOverviewDoubleClick(MouseEvent evt) {
        if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
            this.onUpdate(null);    
        }
    }
    
    private void onCreateGroup(ActionEvent evt) {
        String profName = this.view.getProfName().getText();
        if (profName != null && !profName.isEmpty()) {
            SEGroup group = gb.buildSEGroup(profName);
            OfficeTreeItem rootItem = new OfficeTreeItem("SE Group - " + profName);

            SEGroupController groupCon = new SEGroupController(rootItem, group);
            groupCon.init();
            
            modelView.put(rootItem.getId(), groupCon);
            
            this.view.addRootItem(rootItem);
            
            this.view.getGroupOverview().setOnMousePressed(this::onGroupOverviewDoubleClick);
            this.view.getCreateGroup().disableProperty().bind(this.view.getGroupOverview().rootProperty().isNotNull());
            this.view.getProfName().disableProperty().bind(this.view.getGroupOverview().rootProperty().isNotNull());
            
            this.view.getUpdate().disableProperty().bind(this.view.getGroupOverview().getSelectionModel().selectedItemProperty().isNull());
            this.view.getUpdate().prefWidthProperty().bind(this.view.widthProperty());
        }
    }
    
    private void onUpdate(ActionEvent evt) {
        OfficeTreeItem selectedItem = (OfficeTreeItem) this.view.getGroupOverview().getSelectionModel().getSelectedItem();
        SubController con = modelView.get(selectedItem.getId());
        if (con != null) {
            Object modelItem = con.getModel();
            if (modelItem instanceof SEGroup) {
                SEGroup group = (SEGroup) modelItem;
                new ModifySEGroup(group).showAndWait();
            } else if (modelItem instanceof SEClass) {
                SEClass seClass = (SEClass) modelItem;
                new ModifySEClass(seClass).showAndWait();
            } else if (modelItem instanceof Assignment) {
                Assignment assignment = (Assignment) modelItem;
                new ModifyAssignment(assignment).showAndWait();
            } else if(modelItem instanceof SEStudent) {
                SEStudent student = (SEStudent) modelItem;
                new ModifySEStudent(student).showAndWait();
            } else if(modelItem instanceof Achievement) {
                Achievement achievement = (Achievement) modelItem;
                new ModifyAchievement(achievement).showAndWait();
            } else if(modelItem instanceof  Solution) {
                Solution solution = (Solution) modelItem;
                new ModifySolution(solution).showAndWait();
            }
        }
    }
}
