package uniks.accounting.view.studentOffice.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.studentOffice.Course;
import uniks.accounting.studentOffice.Lecturer;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class ModifyCourse extends Dialog<Void> {
    
    public ModifyCourse(Course course) {
        this.setTitle("Modify course");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox nameBox = new HBox(5.0);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Course:");
        TextField name = new TextField(course.getTitle());
        nameBox.getChildren().addAll(nameLabel, name);

        HBox addListsBox = new HBox(5.0);

        VBox examBox = new VBox(5.0);
        examBox.setMaxHeight(450.0);
        Label examLabel = new Label("Added examinations");
        ListView<String> exam = new ListView<>();
        ListView<Lecturer> lecturers = new ListView<>();
        HBox examContrBox = new HBox(5.0);
        DatePicker newExamDate = new DatePicker();
        Button addExam = new Button("Add");
        addExam.disableProperty().bind(lecturers.getSelectionModel().selectedItemProperty().isNull());
        examContrBox.getChildren().addAll(newExamDate, addExam);
        examBox.getChildren().addAll(examLabel, lecturers, exam, examContrBox);
        HBox.setHgrow(newExamDate, Priority.ALWAYS);
        
        addListsBox.getChildren().addAll(examBox);
        b.getChildren().addAll(nameBox, addListsBox);
        pane.setContent(b);
        this.setDialogPane(pane);
        
        lecturers.setItems(FXCollections.observableList(ob.getStudentOffice().getLecturers()));
        
        addExam.setOnAction(evt -> {
            if (newExamDate.getValue() != null) {
                String newExam = lecturers.getSelectionModel().getSelectedItem().getName() + " - " + newExamDate.getValue();
                exam.getItems().add(newExam);
                newExamDate.setValue(null);
            }
        });

        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            course.setTitle(name.getText());
            
            for (String s : exam.getItems()) {
                String lecturer = s.split(" - ")[0];
                String date = s.split(" - ")[1];
                ob.buildExamination(course, ob.getStudentOffice().getLecturers(lecturer), date);
            }
        });
    }
}
