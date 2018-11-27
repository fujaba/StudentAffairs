package uniks.accounting.view.tapool;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.view.shared.OfficeTreeItem;

public class MainView extends VBox {
    
    private Button createPool;
    private Button update;
    
    private TreeView<String> poolOverview;
    
    public MainView() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20.0);
        this.setPadding(new Insets(15.0));
        
        this.createPool = new Button("Create Pool");
        this.createPool.setDefaultButton(true);
        
        HBox b = new HBox(5.0);
        b.setAlignment(Pos.CENTER);
        b.getChildren().addAll(this.createPool);
        
        this.update = new Button("Modify");
        
        this.getChildren().addAll(b);
    }
    
    public void addRootItem(OfficeTreeItem rootItem) {
        this.poolOverview = new TreeView<>(rootItem);
        VBox.setVgrow(this.poolOverview, Priority.ALWAYS);
        this.getChildren().addAll(this.poolOverview, update);
    }

    public Button getCreatePool() {
        return this.createPool;
    }

    public TreeView<String> getPoolOverview() {
        return this.poolOverview;
    }

    public Button getUpdate() {
        return this.update;
    }
}
