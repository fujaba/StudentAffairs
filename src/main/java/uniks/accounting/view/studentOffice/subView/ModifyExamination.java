package uniks.accounting.view.studentOffice.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uniks.accounting.studentOffice.Course;
import uniks.accounting.studentOffice.Examination;
import uniks.accounting.studentOffice.StudyProgram;
import uniks.accounting.studentOffice.UniStudent;

import java.util.ArrayList;
import java.util.List;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class ModifyExamination  extends Dialog<Void> {
    
    public ModifyExamination(Examination exam) {
        this.setTitle("Modify examination");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        VBox studBox = new VBox(5.0);
        studBox.setMaxHeight(450.0);
        Label studLabel = new Label("Manage enrollments");
        ListView<UniStudent> unassignedStud = new ListView<>();
        ListView<UniStudent> enrolledStud = new ListView<>();
        HBox studContrBox = new HBox(5.0);
        studContrBox.setAlignment(Pos.CENTER);
        Button assignStud = new Button("▼");
        Button unassignStud = new Button("▲");
        studContrBox.getChildren().addAll(assignStud, unassignStud);
        studBox.getChildren().addAll(studLabel, unassignedStud, studContrBox, enrolledStud);

        b.getChildren().add(studBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        List<UniStudent> programStudents = getAllProgramStudents(exam.getTopic());
        unassignedStud.setItems(FXCollections.observableList(programStudents));
        
        assignStud.setOnAction(evt -> {
            UniStudent stud = unassignedStud.getSelectionModel().getSelectedItem();
            if (stud != null) {
                unassignedStud.getItems().remove(stud);
                enrolledStud.getItems().add(stud);
            }
        });
        
        unassignStud.setOnAction(evt -> {
            UniStudent stud = enrolledStud.getSelectionModel().getSelectedItem();
            if (stud != null) {
                enrolledStud.getItems().remove(stud);
                unassignedStud.getItems().add(stud);
            }
        });
        
        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            for (UniStudent s : enrolledStud.getItems()) {
                ob.enroll(s, exam);
            }
        });
    }
    
    private List<UniStudent> getAllProgramStudents(Course course) {
        ArrayList<UniStudent> result = new ArrayList<>();
        for (StudyProgram p : course.getPrograms()) {
            result.addAll(p.getStudents());
        }
        return result;
    }
}
