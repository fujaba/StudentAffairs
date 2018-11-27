package uniks.accounting.view.tapool.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.tapool.TAPool;

import java.math.BigInteger;

import static uniks.accounting.view.tapool.TAPoolApplication.tb;

public class ModifyTAPool extends Dialog<Void> {
    
    public ModifyTAPool(TAPool pool) {
        this.setTitle("Modify ta pool");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        VBox studBox = new VBox(5.0);
        Label studLabel = new Label("Added students");
        ListView<String> stud = new ListView<>();
        HBox studContrBox = new HBox(5.0);
        TextField newStudName = new TextField();
        newStudName.setPromptText("Name");
        TextField newStudTeachingFor = new TextField();
        newStudTeachingFor.setPromptText("Lecturer");
        Button addStud = new Button("Add");
        studContrBox.getChildren().addAll(newStudName, newStudTeachingFor, addStud);
        studBox.getChildren().addAll(studLabel, stud, studContrBox);
        HBox.setHgrow(newStudName, Priority.ALWAYS);
        HBox.setHgrow(newStudTeachingFor, Priority.ALWAYS);

        b.getChildren().addAll(studBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        addStud.setOnAction(evt -> {
            if (newStudName.getText() != null && !newStudName.getText().isEmpty() && newStudTeachingFor.getText() != null && !newStudTeachingFor.getText().isEmpty()) {
                stud.getItems().add(newStudName.getText() + " - " + newStudTeachingFor.getText());
                newStudName.setText("");
                newStudTeachingFor.setText("");
            }
        });

        ((Button) this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            for (String s : stud.getItems()) {
                String[] split = s.split(" - ");
                tb.getOrCreateStudent(split[0], new BigInteger(split[0].getBytes()).toString(), split[1]);    
            }
        });
    }
}
