package uniks.accounting.view.studentOffice;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import uniks.accounting.StudentOfficeService;
import uniks.accounting.studentOffice.Course;
import uniks.accounting.studentOffice.Examination;
import uniks.accounting.studentOffice.StudentOffice;
import uniks.accounting.studentOffice.StudyProgram;
import uniks.accounting.view.studentOffice.subController.StudentOfficeController;
import uniks.accounting.view.studentOffice.subController.SubController;
import uniks.accounting.view.studentOffice.subView.*;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.modelView;
import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class MainController {
    
    private MainView view;
    private StudentOfficeService officeService;
    
    public MainController(MainView view) {
        this.view = view;
    }
    
    public void init() {
        this.view.getCreateOffice().setOnAction(this::onCreateOffice);
        this.view.getUpdate().setOnAction(this::onUpdate);
    }

    private void onOfficeOverviewDoubleClick(MouseEvent evt) {
        if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
            this.onUpdate(null);
        }
    }
    
    private void onCreateOffice(ActionEvent evt) {
        String departmentName = this.view.getDepartmentName().getText();
        if (departmentName != null && !departmentName.isEmpty()) {
            StudentOffice office = ob.getOrCreateStudentOffice(departmentName);
            OfficeTreeItem rootItem = new OfficeTreeItem("Department - " + departmentName);

            StudentOfficeController officeCon = new StudentOfficeController(rootItem, office);
            officeCon.init();
            
            modelView.put(rootItem.getId(), officeCon);

            this.view.addRootTreeItem(rootItem);

            this.view.getOfficeOverview().setOnMousePressed(this::onOfficeOverviewDoubleClick);
            this.view.getCreateOffice().disableProperty().bind(this.view.getOfficeOverview().rootProperty().isNotNull());
            this.view.getDepartmentName().disableProperty().bind(this.view.getOfficeOverview().rootProperty().isNotNull());

            this.view.getUpdate().disableProperty().bind(this.view.getOfficeOverview().getSelectionModel().selectedItemProperty().isNull());
            this.view.getUpdate().prefWidthProperty().bind(this.view.widthProperty());
        }
    }
    
    private void onUpdate(ActionEvent evt) {
        OfficeTreeItem selectedItem = (OfficeTreeItem) this.view.getOfficeOverview().getSelectionModel().getSelectedItem();
        SubController con = modelView.get(selectedItem.getId());
        if (con != null) {
            Object modelItem = con.getModel();
            if (modelItem instanceof StudentOffice) {
                StudentOffice office = (StudentOffice) modelItem;
                new ModifyStudentOffice(office).showAndWait();
            } else if (modelItem instanceof StudyProgram) {
                StudyProgram program = (StudyProgram) modelItem;
                new ModifyStudyProgram(program).showAndWait();
            } else if (modelItem instanceof Course) {
                Course course = (Course) modelItem;
                new ModifyCourse(course).showAndWait();
            } else if (modelItem instanceof Examination) {
                Examination exam = (Examination) modelItem;
                new ModifyExamination(exam).showAndWait();
            }
        }
    }
}
