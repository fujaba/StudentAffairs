package uniks.accounting.view.studentOffice;

import javafx.event.ActionEvent;
import uniks.accounting.StudentOfficeService;
import uniks.accounting.studentOffice.StudentOffice;
import uniks.accounting.view.studentOffice.subController.StudentOfficeController;
import uniks.accounting.view.studentOffice.subView.ModifyStudentOffice;
import uniks.accounting.view.studentOffice.subView.OfficeTreeItem;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.modelView;
import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class MainController {
    
    private MainView view;
    private StudentOfficeService officeService;
    
    private StudentOfficeController officeCon;
    
    public MainController(MainView view) {
        this.view = view;
    }
    
    public void init() {
        this.view.getCreateOffice().setOnAction(this::onCreateOffice);
        this.view.getUpdate().setOnAction(this::onUpdate);
    }
    
    private void onCreateOffice(ActionEvent evt) {
        String departmentName = this.view.getDepartmentName().getText();
        if (departmentName != null && departmentName.length() > 0) {
            StudentOffice office = ob.buildStudentOffice(departmentName);
            OfficeTreeItem rootItem = new OfficeTreeItem("Department - " + departmentName);

            this.officeCon = new StudentOfficeController(rootItem, office);
            this.officeCon.init();
            
            modelView.put(rootItem.getId(), officeCon);

            this.view.addRootTreeItem(rootItem);
        }
    }
    
    private void onUpdate(ActionEvent evt) {
        OfficeTreeItem selectedItem = (OfficeTreeItem) this.view.getOfficeOverview().getSelectionModel().getSelectedItem();
        Object modelItem = modelView.get(selectedItem.getId()).getModel();
     
        if (modelItem instanceof StudentOffice) {
            StudentOffice office = (StudentOffice) modelItem;
            new ModifyStudentOffice(office).showAndWait();
        }
    }
}
