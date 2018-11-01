package uniks.accounting.view.studentOffice;

import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import uniks.accounting.StudentOfficeService;
import uniks.accounting.studentOffice.StudentOffice;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class StudentOfficeController {
    
    private StudentOfficeView view;
    private StudentOfficeService officeService;
    
    private TreeItem<String> officeStudents;
    private TreeItem<String> officeLecturer;
    private TreeItem<String> programs;
    
    private TreeItem<String> programStudents;
    private TreeItem<String> programCourses;
    
    private TreeItem<String> courseExamination;
    
    private StudentOffice office;
    
    public StudentOfficeController(StudentOfficeView view) {
        this.view = view;
    }
    
    public void init() {
        this.view.getCreateOffice().setOnAction(this::onCreateOffice);
    }
    
    private void onCreateOffice(ActionEvent evt) {
        String departmentName = this.view.getDepartmentName().getText();
        if (departmentName != null && departmentName.length() > 0) {
            this.office = ob.buildStudentOffice(departmentName);

            this.officeStudents = new TreeItem<>("Unassigned Students");
            this.officeLecturer = new TreeItem<>("Lecturer");
            this.programs = new TreeItem<>("Study Program");
            TreeItem<String> rootItem = new TreeItem<>("Department - " + departmentName);
            rootItem.getChildren().add(this.officeStudents);
            rootItem.getChildren().add(this.officeLecturer);
            rootItem.getChildren().add(this.programs);
            rootItem.setExpanded(true);
            
            this.view.addRootTreeItem(rootItem);
            
            this.view.getCreateOffice().setDisable(true);
            this.view.getDepartmentName().setDisable(true);
            
            this.view.getAddStudyProgram().setOnAction(this::onCreateProgram);
        }
    }
    
    private void onCreateProgram(ActionEvent evt) {
        String programName = this.view.getProgramName().getText();
        if (programName != null && programName.length() > 0) {
            ob.buildStudyProgram(programName);
            
            this.programStudents = new TreeItem<>("Students");
            this.programCourses = new TreeItem<>("Courses");
            
            TreeItem<String> program = new TreeItem<>(programName);
            program.getChildren().add(this.programStudents);
            program.getChildren().add(this.programCourses);
            program.setExpanded(true);
            
            this.programs.getChildren().add(program);
            this.programs.setExpanded(true);
            
            this.view.getProgramName().setText("");
        }
    }
    
    private void onCreateLecturer(ActionEvent evt) {
        
    }
}
