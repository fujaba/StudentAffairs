package uniks.accounting.view.theorygroup.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uniks.accounting.theorygroup.Presentation;
import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.theorygroup.TheoryStudent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.gb;

public class ModifySeminar extends Dialog<Void> {
    
    public ModifySeminar(Seminar seminar) {
        this.setTitle("Modify seminar");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox nameBox = new HBox(5.0);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Topic:");
        TextField name = new TextField(seminar.getTopic());
        nameBox.getChildren().addAll(nameLabel, name);

        HBox termBox = new HBox(5.0);
        termBox.setAlignment(Pos.CENTER);
        Label termLabel = new Label("Term:");
        TextField term = new TextField(seminar.getTerm());
        termBox.getChildren().addAll(termLabel, term);

        VBox presBox = new VBox(5.0);
        presBox.setMaxHeight(450.0);
        Label presLabel = new Label("Manage presentations");
        ListView<TheoryStudent> unassignedStud = new ListView<>();
        ListView<TheoryStudent> enrolledStud = new ListView<>();
        HBox presContrBox = new HBox(5.0);
        presContrBox.setAlignment(Pos.CENTER);
        Button assignStud = new Button("▼");
        Button unassignStud = new Button("▲");
        presContrBox.getChildren().addAll(assignStud, unassignStud);
        presBox.getChildren().addAll(presLabel, unassignedStud, presContrBox, enrolledStud);

        b.getChildren().addAll(nameBox, termBox, presBox);
        pane.setContent(b);
        this.setDialogPane(pane);
        
        List<TheoryStudent> allStuds = seminar.getGroup().getStudents();
        List<TheoryStudent> presStuds = getSeminarStudents(seminar.getPresentations());
        allStuds = allStuds.stream().filter(stud -> !presStuds.contains(stud)).collect(Collectors.toList());
        
        unassignedStud.setItems(FXCollections.observableArrayList(allStuds));
        enrolledStud.setItems(FXCollections.observableArrayList(presStuds));

        assignStud.setOnAction(evt -> {
            TheoryStudent student = unassignedStud.getSelectionModel().getSelectedItem();
            if (student != null) {
                unassignedStud.getItems().remove(student);
                enrolledStud.getItems().addAll(student);
            }
        });

        unassignStud.setOnAction(evt -> {
            TheoryStudent student = enrolledStud.getSelectionModel().getSelectedItem();
            if (student != null) {
                enrolledStud.getItems().remove(student);
                unassignedStud.getItems().addAll(student);
            }
        });

        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            seminar.setTopic(name.getText());
            seminar.setTerm(term.getText());
            
            for (TheoryStudent s : enrolledStud.getItems()) {
                gb.getOrCreatePresentation(s, seminar);                
            }
        });
    }
    
    private List<TheoryStudent> getSeminarStudents(List<Presentation> seminars) {
        List<TheoryStudent> result = new ArrayList<>();
        for (Presentation p : seminars) {
            result.add(p.getStudent());    
        }
        return result;
    }
}
