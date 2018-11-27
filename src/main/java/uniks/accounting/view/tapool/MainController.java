package uniks.accounting.view.tapool;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import uniks.accounting.tapool.TAPool;
import uniks.accounting.tapool.TAStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;
import uniks.accounting.view.tapool.subController.TAPoolController;
import uniks.accounting.view.tapool.subView.ModifyTAPool;
import uniks.accounting.view.tapool.subView.ModifyTAStudent;

import static uniks.accounting.view.tapool.TAPoolApplication.tb;
import static uniks.accounting.view.tapool.TAPoolApplication.modelView;

public class MainController {
    
    private MainView view;
    
    public MainController(MainView view) {
        this.view = view;
    }
    
    public void init() {
        this.view.getCreatePool().setOnAction(this::onCreateGroup);
        this.view.getUpdate().setOnAction(this::onUpdate);
    }
    
    private void onPoolOverviewDoubleClick(MouseEvent evt) {
        if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
            this.onUpdate(null);    
        }
    }
    
    private void onCreateGroup(ActionEvent evt) {
        TAPool pool = tb.getOrCreateTAPool();
        OfficeTreeItem rootItem = new OfficeTreeItem("TA Pool");

        TAPoolController poolCon = new TAPoolController(rootItem, pool);
        poolCon.init();
        
        modelView.put(rootItem.getId(), poolCon);
        
        this.view.addRootItem(rootItem);
        
        this.view.getPoolOverview().setOnMousePressed(this::onPoolOverviewDoubleClick);
        this.view.getCreatePool().disableProperty().bind(this.view.getPoolOverview().rootProperty().isNotNull());
        
        this.view.getUpdate().disableProperty().bind(this.view.getPoolOverview().getSelectionModel().selectedItemProperty().isNull());
        this.view.getUpdate().prefWidthProperty().bind(this.view.widthProperty());
    }
    
    private void onUpdate(ActionEvent evt) {
        OfficeTreeItem selectedItem = (OfficeTreeItem) this.view.getPoolOverview().getSelectionModel().getSelectedItem();
        SubController con = modelView.get(selectedItem.getId());
        if (con != null) {
            Object modelItem = con.getModel();
            if (modelItem instanceof TAPool) {
                TAPool pool = (TAPool) modelItem;
                new ModifyTAPool(pool).showAndWait();
            } else if (modelItem instanceof TAStudent) {
                TAStudent student = (TAStudent) modelItem;
                new ModifyTAStudent(student).showAndWait();
            }
        }
    }
}
