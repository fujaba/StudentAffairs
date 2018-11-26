package uniks.accounting.view.theorygroup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniks.accounting.TheoryGroupBuilder;
import uniks.accounting.view.shared.SubController;

import java.util.HashMap;

public class TheoryGroupApplication extends Application {

    public static TheoryGroupBuilder gb;
    public static HashMap<String, SubController> modelView;
    static {
        gb = new TheoryGroupBuilder();
        modelView = new HashMap<>();
    }
    
    @Override
    public void start(Stage primaryStage) {
        MainView groupView = new MainView();
        MainController con = new MainController(groupView);
        con.init();
        
        primaryStage.setTitle("Theory Group Application");
        primaryStage.setMinWidth(300.0);
        primaryStage.setMinHeight(600.0);
        primaryStage.setScene(new Scene(groupView));
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
