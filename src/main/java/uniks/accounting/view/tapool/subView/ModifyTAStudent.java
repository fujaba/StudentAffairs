package uniks.accounting.view.tapool.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uniks.accounting.tapool.TAStudent;

public class ModifyTAStudent extends Dialog<Void> {
    
    public ModifyTAStudent(TAStudent student) {
        this.setTitle("Modify ta student");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox nameBox = new HBox(5.0);
        nameBox.setAlignment(Pos.CENTER);
        Label teachingForLabel = new Label("Lecturer:");
        TextField lecturer = new TextField(student.getTeachingAssistantFor());
        nameBox.getChildren().addAll(teachingForLabel, lecturer);

        b.getChildren().addAll(nameBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        ((Button) this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> student.setTeachingAssistantFor(lecturer.getText()));
    }
}
