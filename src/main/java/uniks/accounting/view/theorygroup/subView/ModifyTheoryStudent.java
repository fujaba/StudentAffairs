package uniks.accounting.view.theorygroup.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.theorygroup.TheoryStudent;

import java.util.List;
import java.util.stream.Collectors;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.gb;

public class ModifyTheoryStudent extends Dialog<Void> {
    
    public ModifyTheoryStudent(TheoryStudent student) {
        this.setTitle("Modify student");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox teachingBox = new HBox(5.0);
        teachingBox.setAlignment(Pos.CENTER);
        Label teachingLabel = new Label("Assistant of:");
        TextField teaching = new TextField(student.getTa_4());
        teachingBox.getChildren().addAll(teachingLabel, teaching);

        VBox presBox = new VBox(5.0);
        presBox.setMaxHeight(450.0);
        Label presLabel = new Label("Manage presentations");
        ListView<Seminar> unassignedSeminars = new ListView<>();
        ListView<Seminar> enrolledSeminars = new ListView<>();
        HBox presContrBox = new HBox(5.0);
        presContrBox.setAlignment(Pos.CENTER);
        Button assignSeminar = new Button("▼");
        Button unassignSeminar = new Button("▲");
        presContrBox.getChildren().addAll(assignSeminar, unassignSeminar);
        presBox.getChildren().addAll(presLabel, unassignedSeminars, presContrBox, enrolledSeminars);

        b.getChildren().addAll(teachingBox, presBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        List<Seminar> allSeminars = student.getGroup().getSeminars();
        List<Seminar> studentSeminars = student.getGroup().getSeminars().stream()
                .filter(seminar -> seminar.getPresentations().stream().anyMatch(pres -> pres.getStudent().equals(student)))
                .collect(Collectors.toList());
        allSeminars = allSeminars.stream().filter(seminar -> !studentSeminars.contains(seminar)).collect(Collectors.toList());
        
        unassignedSeminars.setItems(FXCollections.observableArrayList(allSeminars));
        enrolledSeminars.setItems(FXCollections.observableArrayList(studentSeminars));
        
        assignSeminar.setOnAction(evt -> {
            Seminar seminar = unassignedSeminars.getSelectionModel().getSelectedItem();
            if (seminar != null) {
                unassignedSeminars.getItems().remove(seminar);
                enrolledSeminars.getItems().addAll(seminar);
            }
        });
        
        unassignSeminar.setOnAction(evt -> {
            Seminar seminar = enrolledSeminars.getSelectionModel().getSelectedItem();
            if (seminar != null) {
                enrolledSeminars.getItems().remove(seminar);
                unassignedSeminars.getItems().addAll(seminar);
            }
        });
        
        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            if (teaching.getText() != null && !teaching.getText().isEmpty()) {
                gb.studentHired(student, teaching.getText());
            }
            
            for (Seminar s : enrolledSeminars.getItems()) {
                gb.getOrCreatePresentation(student, s).setOfficeStatus("unregistered");
            }
        });
    }
}
