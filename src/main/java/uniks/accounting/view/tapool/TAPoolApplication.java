package uniks.accounting.view.tapool;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniks.accounting.TAPoolBuilder;
import uniks.accounting.view.shared.SubController;

import java.util.HashMap;

public class TAPoolApplication extends Application {

    public static TAPoolBuilder tb;
    public static TAPoolRestService ts;
    public static HashMap<String, SubController> modelView;
    static {
        tb = new TAPoolBuilder();
        modelView = new HashMap<>();
    }
    
    @Override
    public void start(Stage primaryStage) {
        MainView groupView = new MainView();
        MainController con = new MainController(groupView);
        con.init();
        
        ts = new TAPoolRestService();
        
        primaryStage.setTitle("TA Pool Application");
        primaryStage.setMinWidth(300.0);
        primaryStage.setMinHeight(600.0);
        primaryStage.setScene(new Scene(groupView));
        primaryStage.show();
    }

    @Override
    public void stop() {
        ts.stopServer();
        System.exit(0);
    }
    
    public static void main(String... args) {
        Application.launch(args);
    }
}
