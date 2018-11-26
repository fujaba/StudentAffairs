package uniks.accounting.view.studentOffice;

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
    
    private TextField departmentName;
    private Button createOffice;
    private Button update;
    
    private TreeView<String> officeOverview;
    
    public MainView() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20.0);
        this.setPadding(new Insets(15.0));

        this.departmentName = new TextField();
        this.createOffice = new Button("Create Office");
        this.createOffice.setDefaultButton(true);
        HBox.setHgrow(this.departmentName, Priority.ALWAYS);
        
        HBox b = new HBox(5.0);
        b.setAlignment(Pos.CENTER);
        b.getChildren().addAll(this.departmentName, this.createOffice);

        this.update = new Button("Modify");
        
        this.getChildren().addAll(b);
    }
    
    public void addRootTreeItem(OfficeTreeItem rootItem) {
        this.officeOverview = new TreeView<>(rootItem);
        VBox.setVgrow(this.officeOverview, Priority.ALWAYS);
        this.getChildren().addAll(this.officeOverview, update);
    }
    
    public TextField getDepartmentName() {
        return departmentName;
    }

    public Button getCreateOffice() {
        return createOffice;
    }

    public TreeView<String> getOfficeOverview() {
        return officeOverview;
    }

    public Button getUpdate() {
        return update;
    }
}
