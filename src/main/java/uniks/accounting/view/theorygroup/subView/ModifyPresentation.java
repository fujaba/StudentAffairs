package uniks.accounting.view.theorygroup.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.theorygroup.Presentation;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.gb;

public class ModifyPresentation extends Dialog<Void> {
    
    public ModifyPresentation(Presentation presentation) {
        this.setTitle("Modify presentation");

        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);

        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));

        HBox enrollBox = new HBox(5.0);
        enrollBox.setAlignment(Pos.CENTER);
        Button enroll = new Button("Enroll");
        enroll.prefWidthProperty().bind(b.widthProperty());
        HBox.setHgrow(enroll, Priority.ALWAYS);
        enrollBox.getChildren().add(enroll);

        HBox pointBox = new HBox(5.0);
        pointBox.setAlignment(Pos.CENTER_LEFT);
        TextField slides = new TextField(presentation.getSlides() + "");
        TextField scholarship = new TextField(presentation.getScholarship() + "");
        TextField content = new TextField(presentation.getContent() + "");
        Button givePoints = new Button("Give Points");
        HBox.setHgrow(slides, Priority.ALWAYS);
        HBox.setHgrow(scholarship, Priority.ALWAYS);
        HBox.setHgrow(content, Priority.ALWAYS);
        pointBox.getChildren().addAll(slides, scholarship, content, givePoints);

        HBox gradeBox = new HBox(5.0);
        gradeBox.setAlignment(Pos.CENTER);
        Button grade = new Button("Grade");
        grade.prefWidthProperty().bind(b.widthProperty());
        HBox.setHgrow(grade, Priority.ALWAYS);
        gradeBox.getChildren().add(grade);
        
        b.getChildren().addAll(enrollBox, pointBox, gradeBox);
        pane.setContent(b);
        this.setDialogPane(pane);

        enroll.setOnAction(evt -> {
            gb.enroll(presentation);
            this.close();
        });
        
        grade.setOnAction(evt -> {
            gb.gradePresentation(presentation);
            this.close();
        });
        
        givePoints.setOnAction(evt -> {
            gb.gradePresentation(presentation, slides.getText(), scholarship.getText(), content.getText());
            this.close();
        });
    }
}
