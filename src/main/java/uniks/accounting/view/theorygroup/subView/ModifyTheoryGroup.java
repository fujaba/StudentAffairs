package uniks.accounting.view.theorygroup.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.theorygroup.TheoryGroup;

import java.math.BigInteger;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.gb;

public class ModifyTheoryGroup extends Dialog<Void> {
    
    public ModifyTheoryGroup(TheoryGroup group) {
        this.setTitle("Modify theory group");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox nameBox = new HBox(5.0);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Head:");
        TextField name = new TextField(group.getHead());
        nameBox.getChildren().addAll(nameLabel, name);

        HBox addListsBox = new HBox(5.0);

        VBox seminarBox = new VBox(5.0);
        Label seminarLabel = new Label("Added seminars");
        ListView<String> seminars = new ListView<>();
        HBox seminarContrBox = new HBox(5.0);
        TextField newSeminarName = new TextField();
        Button addSeminar = new Button("Add");
        seminarContrBox.getChildren().addAll(newSeminarName, addSeminar);
        seminarBox.getChildren().addAll(seminarLabel, seminars, seminarContrBox);
        HBox.setHgrow(newSeminarName, Priority.ALWAYS);

        VBox studBox = new VBox(5.0);
        Label studLabel = new Label("Added students");
        ListView<String> stud = new ListView<>();
        HBox studContrBox = new HBox(5.0);
        TextField newStudName = new TextField();
        Button addStud = new Button("Add");
        studContrBox.getChildren().addAll(newStudName, addStud);
        studBox.getChildren().addAll(studLabel, stud, studContrBox);
        HBox.setHgrow(newStudName, Priority.ALWAYS);

        addListsBox.getChildren().addAll(seminarBox, studBox);
        b.getChildren().addAll(nameBox, addListsBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        addSeminar.setOnAction(evt -> {
            if (newSeminarName.getText() != null && !newSeminarName.getText().isEmpty()) {
                seminars.getItems().add(newSeminarName.getText());
                newSeminarName.setText("");
            }
        });

        addStud.setOnAction(evt -> {
            if (newStudName.getText() != null && !newStudName.getText().isEmpty()) {
                stud.getItems().add(newStudName.getText());
                newStudName.setText("");
            }
        });
        
        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            group.setHead(name.getText());
            
            for (String s : seminars.getItems()) {
                gb.getOrCreateSeminar(s, "2018-19");
            }
            
            for (String s : stud.getItems()) {
                gb.getOrCreateStudent(s, new BigInteger(s.getBytes()).toString());
            }
        });
    }
}
