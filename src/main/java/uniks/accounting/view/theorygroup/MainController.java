package uniks.accounting.view.theorygroup;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import uniks.accounting.theorygroup.Presentation;
import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.theorygroup.TheoryGroup;
import uniks.accounting.theorygroup.TheoryStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;
import uniks.accounting.view.theorygroup.subController.TheoryGroupController;
import uniks.accounting.view.theorygroup.subView.ModifyPresentation;
import uniks.accounting.view.theorygroup.subView.ModifySeminar;
import uniks.accounting.view.theorygroup.subView.ModifyTheoryGroup;
import uniks.accounting.view.theorygroup.subView.ModifyTheoryStudent;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.gb;
import static uniks.accounting.view.theorygroup.TheoryGroupApplication.modelView;

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
        TheoryGroup group = gb.getOrCreateTheoryGroup();
        OfficeTreeItem rootItem = new OfficeTreeItem("Theory Group - " + group.getHead());

        TheoryGroupController groupCon = new TheoryGroupController(rootItem, group);
        groupCon.init();
        
        modelView.put(rootItem.getId(), groupCon);
        
        this.view.addRootItem(rootItem);
        
        this.view.getGroupOverview().setOnMousePressed(this::onGroupOverviewDoubleClick);
        this.view.getCreateGroup().disableProperty().bind(this.view.getGroupOverview().rootProperty().isNotNull());
        
        this.view.getUpdate().disableProperty().bind(this.view.getGroupOverview().getSelectionModel().selectedItemProperty().isNull());
        this.view.getUpdate().prefWidthProperty().bind(this.view.widthProperty());
    }
    
    private void onUpdate(ActionEvent evt) {
        OfficeTreeItem selectedItem = (OfficeTreeItem) this.view.getGroupOverview().getSelectionModel().getSelectedItem();
        SubController con = modelView.get(selectedItem.getId());
        if (con != null) {
            Object modelItem = con.getModel();
            if (modelItem instanceof TheoryGroup) {
                TheoryGroup group = (TheoryGroup) modelItem;
                new ModifyTheoryGroup(group).showAndWait();
            } else if (modelItem instanceof TheoryStudent) {
                TheoryStudent student = (TheoryStudent) modelItem;
                new ModifyTheoryStudent(student).showAndWait();
            } else if (modelItem instanceof Seminar) {
                Seminar seminar = (Seminar) modelItem;
                new ModifySeminar(seminar).showAndWait();
            } else if (modelItem instanceof Presentation) {
                Presentation presentation = (Presentation) modelItem;
                new ModifyPresentation(presentation).showAndWait();
            }
        }
    }
}
