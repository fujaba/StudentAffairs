package uniks.accounting.view.theorygroup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.view.shared.OfficeTreeItem;

public class MainView extends VBox {
    
    private Button createGroup;
    private Button update;
    
    private TreeView<String> groupOverview;
    
    public MainView() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20.0);
        this.setPadding(new Insets(15.0));
        
        this.createGroup = new Button("Create Group");
        this.createGroup.setDefaultButton(true);
        
        HBox b = new HBox(5.0);
        b.setAlignment(Pos.CENTER);
        b.getChildren().addAll(this.createGroup);
        
        this.update = new Button("Modify");
        
        this.getChildren().addAll(b);
    }
    
    public void addRootItem(OfficeTreeItem rootItem) {
        this.groupOverview = new TreeView<>(rootItem);
        VBox.setVgrow(this.groupOverview, Priority.ALWAYS);
        this.getChildren().addAll(this.groupOverview, update);
    }

    public Button getCreateGroup() {
        return this.createGroup;
    }

    public TreeView<String> getGroupOverview() {
        return this.groupOverview;
    }

    public Button getUpdate() {
        return this.update;
    }
}
