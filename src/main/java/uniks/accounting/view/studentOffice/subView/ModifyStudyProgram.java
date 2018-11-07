package uniks.accounting.view.studentOffice.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.studentOffice.StudyProgram;
import uniks.accounting.studentOffice.UniStudent;

import java.util.List;
import java.util.stream.Collectors;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class ModifyStudyProgram extends Dialog<Void> {

    public ModifyStudyProgram(StudyProgram program) {
        this.setTitle("Modify study program");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox nameBox = new HBox(5.0);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Subject:");
        TextField name = new TextField(program.getSubject());
        nameBox.getChildren().addAll(nameLabel, name);

        HBox addListsBox = new HBox(5.0);
        
        VBox courseBox = new VBox(5.0);
        Label courseLabel = new Label("Added courses");
        ListView<String> course = new ListView<>();
        HBox courseContrBox = new HBox(5.0);
        TextField newCourseName = new TextField();
        Button addCourse = new Button("Add");
        courseContrBox.getChildren().addAll(newCourseName, addCourse);
        courseBox.getChildren().addAll(courseLabel, course, courseContrBox);
        HBox.setHgrow(newCourseName, Priority.ALWAYS);
        
        VBox studBox = new VBox(5.0);
        studBox.setMaxHeight(450.0);
        Label studLabel = new Label("Manage students");
        ListView<UniStudent> unassignedStud = new ListView<>();
        ListView<UniStudent> programStud = new ListView<>();
        HBox studContrBox = new HBox(5.0);
        studContrBox.setAlignment(Pos.CENTER);
        Button assignStud = new Button("▼");
        Button unassignStud = new Button("▲");
        studContrBox.getChildren().addAll(assignStud, unassignStud);
        studBox.getChildren().addAll(studLabel, unassignedStud, studContrBox, programStud);

        addListsBox.getChildren().addAll(courseBox, studBox);
        b.getChildren().addAll(nameBox, addListsBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        List<UniStudent> allStuds = ob.getStudentOffice().getStudents();
        List<UniStudent> progStuds = program.getStudents();
        allStuds = allStuds.stream().filter(stud -> !progStuds.contains(stud)).collect(Collectors.toList());

        unassignedStud.setItems(FXCollections.observableList(allStuds));
        programStud.setItems(FXCollections.observableList(progStuds));
        
        addCourse.setOnAction(evt -> {
            if (newCourseName.getText() != null && !newCourseName.getText().isEmpty()) {
                course.getItems().add(newCourseName.getText());
                newCourseName.setText("");
            }
        });
        
        assignStud.setOnAction(evt -> {
            UniStudent stud = unassignedStud.getSelectionModel().getSelectedItem();
            if (stud != null) {
                unassignedStud.getItems().remove(stud);
                programStud.getItems().add(stud);
            }
        });
        
        unassignStud.setOnAction(evt -> {
            UniStudent stud = programStud.getSelectionModel().getSelectedItem();
            if (stud != null) {
                programStud.getItems().remove(stud);
                unassignedStud.getItems().add(stud);
            }
        });

        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            program.setSubject(name.getText());

            for (String s : course.getItems()) {
                ob.buildCourse(program, s);
            }
            
            for (UniStudent s : programStud.getItems()) {
                if (s.getMajorSubject() == null) {
                    ob.chooseMajorSubject(s, program);
                }
            }
        });
    }
}
