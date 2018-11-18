package uniks.accounting.view.segroup.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uniks.accounting.segroup.Assignment;

public class ModifyAssignment extends Dialog<Void> {

    public ModifyAssignment(Assignment assignment) {
        this.setTitle("Modify assignment");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox taskBox = new HBox(5.0);
        Label taskLabel = new Label("Task: ");
        TextField task = new TextField(assignment.getTask());
        taskBox.getChildren().addAll(taskLabel, task);

        HBox pointBox = new HBox(5.0);
        Label pointLabel = new Label("Points:");
        TextField points = new TextField(assignment.getPoints() + "");
        pointBox.getChildren().addAll(pointLabel, points);

        b.getChildren().addAll(taskBox, pointBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            assignment.setTask(task.getText());
            assignment.setPoints(Double.parseDouble(points.getText()));
        });
    }
}
