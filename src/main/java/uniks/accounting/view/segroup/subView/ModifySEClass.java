package uniks.accounting.view.segroup.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.segroup.Achievement;
import uniks.accounting.segroup.SEClass;
import uniks.accounting.segroup.SEStudent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uniks.accounting.view.segroup.SEGroupApplication.gb;

public class ModifySEClass extends Dialog<Void> {

    public ModifySEClass(SEClass seClass) {
        this.setTitle("Modify se class");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox nameBox = new HBox(5.0);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Topic:");
        TextField name = new TextField(seClass.getTopic());
        nameBox.getChildren().addAll(nameLabel, name);

        HBox termBox = new HBox(5.0);
        termBox.setAlignment(Pos.CENTER);
        Label termLabel = new Label("Term:");
        TextField term = new TextField(seClass.getTerm());
        termBox.getChildren().addAll(termLabel, term);

        HBox addListsBox = new HBox(5.0);

        VBox assignBox = new VBox(5.0);
        Label assignLabel = new Label("Added assignments");
        ListView<String> assign = new ListView<>();
        HBox assignContrBox = new HBox(5.0);
        TextField assignTask = new TextField();
        assignTask.setPromptText("Task");
        TextField assignPoints = new TextField();
        assignPoints.setPromptText("Points");
        Button addAssign = new Button("Add");
        assignContrBox.getChildren().addAll(assignTask, assignPoints, addAssign);
        assignBox.getChildren().addAll(assignLabel, assign, assignContrBox);
        HBox.setHgrow(assignTask, Priority.ALWAYS);

        VBox achievBox = new VBox(5.0);
        achievBox.setMaxHeight(450.0);
        Label achievLabel = new Label("Manage achievements");
        ListView<SEStudent> unassignedStud = new ListView<>();
        ListView<SEStudent> enrolledStud = new ListView<>();
        HBox achievContrBox = new HBox(5.0);
        achievContrBox.setAlignment(Pos.CENTER);
        Button assignStud = new Button("▼");
        Button unassignStud = new Button("▲");
        achievContrBox.getChildren().addAll(assignStud, unassignStud);
        achievBox.getChildren().addAll(achievLabel, unassignedStud, achievContrBox, enrolledStud);

        addListsBox.getChildren().addAll(assignBox, achievBox);
        b.getChildren().addAll(nameBox, term, addListsBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        List<SEStudent> allStuds = seClass.getGroup().getStudents();
        List<SEStudent> achievStuds = getAllAchievStudents(seClass.getParticipations());
        allStuds = allStuds.stream().filter(stud -> !achievStuds.contains(stud)).collect(Collectors.toList());

        unassignedStud.setItems(FXCollections.observableArrayList(allStuds));
        enrolledStud.setItems(FXCollections.observableArrayList(achievStuds));

        assignStud.setOnAction(evt -> {
            SEStudent stud = unassignedStud.getSelectionModel().getSelectedItem();
            if (stud != null) {
                unassignedStud.getItems().remove(stud);
                enrolledStud.getItems().add(stud);
            }
        });

        unassignStud.setOnAction(evt -> {
            SEStudent stud = enrolledStud.getSelectionModel().getSelectedItem();
            if (stud != null) {
                enrolledStud.getItems().remove(stud);
                unassignedStud.getItems().add(stud);
            }
        });

        addAssign.setOnAction(evt -> {
            if (assignTask.getText() != null && !assignTask.getText().isEmpty() &&
                assignPoints.getText() != null && !assignPoints.getText().isEmpty()) {
                try {
                    double points = Double.parseDouble(assignPoints.getText());
                    assign.getItems().add(assignTask.getText() + " - " + points);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                assignTask.setText("");
                assignPoints.setText("");
            }
        });

        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            seClass.setTopic(name.getText());
            seClass.setTerm(term.getText());

            for (String s : assign.getItems()) {
                String task = s.split(" - ")[0];
                double points = Double.parseDouble(s.split(" - ")[1]);
                gb.buildAssignment(seClass, task, points);
            }

            for (SEStudent s : enrolledStud.getItems()) {
                gb.getOrCreateAchievement(s, seClass);
            }
        });
    }

    private List<SEStudent> getAllAchievStudents(List<Achievement> achievements) {
        ArrayList<SEStudent> result = new ArrayList<>();
        for (Achievement a : achievements) {
            result.add(a.getStudent());
        }
        return result;
    }
}
