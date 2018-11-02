package uniks.accounting.view.studentOffice.subView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import uniks.accounting.studentOffice.StudentOffice;

import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class ModifyStudentOffice extends Dialog<Void> {
    
    public ModifyStudentOffice(StudentOffice office) {
        this.setTitle("Modify student office");
        
        DialogPane pane = new DialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK);
        
        VBox b = new VBox();
        b.setAlignment(Pos.TOP_LEFT);
        b.setSpacing(20.0);
        b.setPadding(new Insets(15.0));
        
        HBox nameBox = new HBox(5.0);
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Department:");
        TextField name = new TextField(office.getDepartment());
        nameBox.getChildren().addAll(nameLabel, name);
        
        HBox addListsBox = new HBox(5.0);
        
        VBox lectBox = new VBox(5.0);
        Label lectLabel = new Label("Added lecturers");
        ListView<String> lect = new ListView<>();
        HBox lectContrBox = new HBox(5.0);
        TextField newLecName = new TextField();
        Button addLec = new Button("Add");
        lectContrBox.getChildren().addAll(newLecName, addLec);
        lectBox.getChildren().addAll(lectLabel, lect, lectContrBox);
        HBox.setHgrow(newLecName, Priority.ALWAYS);

        VBox progBox = new VBox(5.0);
        Label progLabel = new Label("Added study programs");
        ListView<String> prog = new ListView<>();
        HBox progContrBox = new HBox(5.0);
        TextField newProgName = new TextField();
        Button addProg = new Button("Add");
        progContrBox.getChildren().addAll(newProgName, addProg);
        progBox.getChildren().addAll(progLabel, prog, progContrBox);
        HBox.setHgrow(newProgName, Priority.ALWAYS);
        
        addListsBox.getChildren().addAll(lectBox, progBox);
        b.getChildren().addAll(nameBox, addListsBox);
        pane.setContent(b);
        this.setDialogPane(pane);
        
        addLec.setOnAction(evt -> {
            lect.getItems().add(newLecName.getText());
            newLecName.setText("");
        });

        addProg.setOnAction(evt -> {
            prog.getItems().add(newProgName.getText());
            newProgName.setText("");
        });
        
        ((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(evt -> {
            office.setDepartment(name.getText());
            
            for (String s : lect.getItems()) {
                ob.buildLecturer(s);
            }
            
            for (String s : prog.getItems()) {
                ob.buildStudyProgram(s);
            }
        });
    }
}
