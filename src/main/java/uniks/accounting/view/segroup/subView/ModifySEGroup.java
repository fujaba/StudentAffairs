package uniks.accounting.view.segroup.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.segroup.SEGroup;

import java.math.BigInteger;

import static uniks.accounting.view.segroup.SEGroupApplication.gb;

public class ModifySEGroup extends Dialog<Void> {
    
    public ModifySEGroup(SEGroup group) {
        this.setTitle("Modify se group");

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

        VBox classBox = new VBox(5.0);
        Label classLabel = new Label("Added classes");
        ListView<String> classes = new ListView<>();
        HBox classContrBox = new HBox(5.0);
        TextField newClassName = new TextField();
        Button addClass = new Button("Add");
        classContrBox.getChildren().addAll(newClassName, addClass);
        classBox.getChildren().addAll(classLabel, classes, classContrBox);
        HBox.setHgrow(newClassName, Priority.ALWAYS);

        VBox studBox = new VBox(5.0);
        Label studLabel = new Label("Added students");
        ListView<String> stud = new ListView<>();
        HBox studContrBox = new HBox(5.0);
        TextField newStudName = new TextField();
        Button addStud = new Button("Add");
        studContrBox.getChildren().addAll(newStudName, addStud);
        studBox.getChildren().addAll(studLabel, stud, studContrBox);
        HBox.setHgrow(newStudName, Priority.ALWAYS);

        addListsBox.getChildren().addAll(classBox, studBox);
        b.getChildren().addAll(nameBox, addListsBox);
        pane.setContent(b);
        this.setDialogPane(pane);
        
        addClass.setOnAction(evt -> {
            if (newClassName.getText() != null && !newClassName.getText().isEmpty()) {
                classes.getItems().add(newClassName.getText());
                newClassName.setText("");
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
            
            for (String s : stud.getItems()) {
                gb.getOrCreateStudent(s, new BigInteger(s.getBytes()).toString());
            }
            
            for (String s : classes.getItems()) {
                gb.getOrCreateSEClass(s, "2018-19");
            }
        });
    }
}
