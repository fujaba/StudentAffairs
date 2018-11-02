package uniks.accounting.view.studentOffice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniks.accounting.StudentOfficeBuilder;
import uniks.accounting.view.studentOffice.subController.SubController;

import java.util.HashMap;

public class StudentOfficeApplication extends Application {
    
    public static StudentOfficeBuilder ob;
    public static HashMap<String, SubController> modelView;
    static {
        ob = new StudentOfficeBuilder();
        modelView = new HashMap<>();
    }

    @Override
    public void start(Stage primaryStage) {
        MainView officeView = new MainView();
        MainController con = new MainController(officeView);
        con.init();

        primaryStage.setTitle("Student Office Application");
        primaryStage.setMinWidth(300.0);
        primaryStage.setMinHeight(600.0);
        primaryStage.setScene(new Scene(officeView));
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String... args) {
        Application.launch(args);
    }
}
