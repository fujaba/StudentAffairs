package uniks.accounting.view.segroup;

import com.mashape.unirest.http.Unirest;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniks.accounting.SEGroupBuilder;
import uniks.accounting.view.shared.SubController;

import java.io.IOException;
import java.util.HashMap;

public class SEGroupApplication extends Application {

    public static SEGroupBuilder gb;
    public static HashMap<String, SubController> modelView;
    static {
        gb = new SEGroupBuilder();
        modelView = new HashMap<>();
    }
    
    @Override
    public void start(Stage primaryStage) {
        MainView groupView = new MainView();
        MainController con = new MainController(groupView);
        con.init();
        
        primaryStage.setTitle("SE Group Application");
        primaryStage.setMinWidth(300.0);
        primaryStage.setMinHeight(600.0);
        primaryStage.setScene(new Scene(groupView));
        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {
        Unirest.shutdown();
        System.exit(0);
    }
    
    public static void main(String... args) {
        Application.launch(args);
    }
}
