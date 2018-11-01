package uniks.accounting.view.studentOffice;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentOfficeView extends VBox {
    
    private TextField departmentName;
    private Button createOffice;
    
    private TreeView<String> officeOverview;
    
    private TextField programName;
    private Button addStudyProgram;
    
    public StudentOfficeView() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20.0);
        this.setPadding(new Insets(15.0));

        this.departmentName = new TextField();
        this.createOffice = new Button("Create Office");
        this.createOffice.setDefaultButton(true);
        
        HBox b = new HBox(5.0);
        b.setAlignment(Pos.CENTER);
        b.getChildren().addAll(this.departmentName, this.createOffice);

        this.getChildren().addAll(b);
    }
    
    public void addRootTreeItem(TreeItem<String> rootItem) {
        this.officeOverview = new TreeView<>(rootItem);
        this.getChildren().add(this.officeOverview);
        this.addDepartmentControls();
    }
    
    private void addDepartmentControls() {
        this.programName = new TextField();
        this.addStudyProgram = new Button("Add Program");
        
        HBox b = new HBox(5.0);
        b.setAlignment(Pos.CENTER);
        b.getChildren().addAll(programName, addStudyProgram);
        
        this.getChildren().add(b);
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

    public TextField getProgramName() {
        return programName;
    }

    public Button getAddStudyProgram() {
        return addStudyProgram;
    }
}
