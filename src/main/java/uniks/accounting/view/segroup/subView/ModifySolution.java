package uniks.accounting.view.segroup.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.segroup.Solution;

import static uniks.accounting.view.segroup.SEGroupApplication.gb;

public class ModifySolution extends Dialog<Void> {
    
    public ModifySolution(Solution solution) {
        this.setTitle("Modify solution");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox gradeBox = new HBox(5.0);
        gradeBox.setAlignment(Pos.CENTER_LEFT);
        TextField points = new TextField(solution.getPoints() + "");
        Button grade = new Button("Give Points");
        HBox.setHgrow(points, Priority.ALWAYS);
        gradeBox.getChildren().addAll(points, grade);
        
        b.getChildren().add(gradeBox);
        pane.setContent(b);
        this.setDialogPane(pane);
        
        grade.setOnAction(evt -> {
            gb.gradeSolution(solution, Double.parseDouble(points.getText()));
            this.close();
        });
    }
}
