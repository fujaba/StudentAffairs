package uniks.accounting.view.segroup.subView;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uniks.accounting.segroup.Achievement;
import uniks.accounting.segroup.SEClass;
import uniks.accounting.segroup.SEStudent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uniks.accounting.view.segroup.SEGroupApplication.gb;

public class ModifySEStudent extends Dialog<Void> {

    public ModifySEStudent(SEStudent student) {
        this.setTitle("Modify student");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        VBox assignBox = new VBox(5.0);
        assignBox.setMaxHeight(450.0);
        Label assignLabel = new Label("Manage achievements");
        ListView<SEClass> unassignedClasses = new ListView<>();
        ListView<SEClass> enrolledClasses = new ListView<>();
        HBox assignContrBox = new HBox(5.0);
        assignContrBox.setAlignment(Pos.CENTER);
        Button assignClass = new Button("▼");
        Button unassignClass = new Button("▲");
        assignContrBox.getChildren().addAll(assignClass, unassignClass);
        assignBox.getChildren().addAll(assignLabel, unassignedClasses, assignContrBox, enrolledClasses);

        b.getChildren().addAll(assignBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        List<SEClass> allClasses = student.getGroup().getClasses();
        List<SEClass> studentClasses = getAllStudentClasses(student.getAchievements());
        allClasses = allClasses.stream().filter(clazz -> !studentClasses.contains(clazz)).collect(Collectors.toList());

        unassignedClasses.setItems(FXCollections.observableArrayList(allClasses));
        enrolledClasses.setItems(FXCollections.observableArrayList(studentClasses));

        assignClass.setOnAction(evt -> {
            SEClass clazz = unassignedClasses.getSelectionModel().getSelectedItem();
            if (clazz != null) {
                unassignedClasses.getItems().remove(clazz);
                enrolledClasses.getItems().add(clazz);
            }
        });

        unassignClass.setOnAction(evt -> {
            SEClass clazz = enrolledClasses.getSelectionModel().getSelectedItem();
            if (clazz != null) {
                enrolledClasses.getItems().remove(clazz);
                unassignedClasses.getItems().add(clazz);
            }
        });

        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            for (SEClass c : enrolledClasses.getItems()) {
                gb.getOrCreateAchievement(student, c);
            }
        });
    }

    private List<SEClass> getAllStudentClasses(List<Achievement> achievements) {
        List<SEClass> result = new ArrayList<>();
        for (Achievement a : achievements) {
            result.add(a.getSeClass());
        }
        return result;
    }
}
