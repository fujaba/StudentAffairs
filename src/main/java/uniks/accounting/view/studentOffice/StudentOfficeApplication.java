package uniks.accounting.view.studentOffice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniks.accounting.StudentOfficeBuilder;

public class StudentOfficeApplication extends Application {
    
    public static StudentOfficeBuilder ob;
    static {
        ob = new StudentOfficeBuilder();
    }

    @Override
    public void start(Stage primaryStage) {
        StudentOfficeView officeView = new StudentOfficeView();
        StudentOfficeController con = new StudentOfficeController(officeView);
        con.init();
        
        primaryStage.setTitle("Student Office Application");
        primaryStage.setMinWidth(300.0);
        primaryStage.setMinHeight(600.0);
        primaryStage.setScene(new Scene(officeView));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(String... args) {
        Application.launch(args);
    }
}
