package uniks.accounting.view.segroup.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.segroup.Achievement;
import uniks.accounting.segroup.Assignment;

import java.util.Arrays;

import static uniks.accounting.view.segroup.SEGroupApplication.gb;

public class ModifyAchievement extends Dialog<Void> {
    
    private static String ENROLLED = "enrolled";

    public ModifyAchievement(Achievement achievement) {
        this.setTitle("Modify achievement");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        CheckBox enrolled = new CheckBox("Enrolled");
        enrolled.setSelected(achievement.getOfficeStatus().contains(ENROLLED));

        Button gradeExamination = new Button("Give a grade");

        VBox solutionBox = new VBox(5.0);
        Label solutionLabel = new Label("Added solutions");
        ListView<String> solutions = new ListView<>();
        HBox solutionContrBox = new HBox(5.0);
        ComboBox<Assignment> assignments = new ComboBox<>();
        assignments.setItems(FXCollections.observableList(achievement.getSeClass().getAssignments()));
        TextField newSolutionGit = new TextField();
        Button addSolution = new Button("Add");
        solutionContrBox.getChildren().addAll(assignments, newSolutionGit, addSolution);
        solutionBox.getChildren().addAll(solutionLabel, solutions, solutionContrBox);
        HBox.setHgrow(newSolutionGit, Priority.ALWAYS);

        b.getChildren().addAll(enrolled, gradeExamination, solutionBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        gradeExamination.setOnAction(evt -> {
            gb.gradeExamination(achievement);
            this.close();
        });
        
        addSolution.setOnAction(evt -> {
            Assignment assign = assignments.getSelectionModel().getSelectedItem();
            if (assign != null && 
                newSolutionGit.getText() != null && !newSolutionGit.getText().isEmpty()) {
                solutions.getItems().add(assign.getTask() + " - " + newSolutionGit.getText());
                newSolutionGit.setText("");
            }
        });

        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            if (!achievement.getOfficeStatus().equals(ENROLLED) && enrolled.isSelected()) {
                gb.enroll(achievement);
            }
            
            for (String s : solutions.getItems()) {
                String task = s.split(" - ")[0];
                String git = s.split(" - ")[1];
                gb.buildSolution(achievement, achievement.getSeClass().getAssignments(task), git);
            }
        });
    }
}
