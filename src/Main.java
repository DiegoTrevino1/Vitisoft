import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Entry point for the Emergency Dispatch Dashboard application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create layout containers
        MapViewPane mapViewPane = new MapViewPane("https://diegotrevino1.github.io/Vitisoft/html/map.html");
        CallInfoPane callInfoPane = new CallInfoPane();

        BorderPane root = new BorderPane();
        root.setLeft(mapViewPane.getWebView());
        root.setRight(callInfoPane.getPane());

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Emergency Dispatch Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // connect to the database
        DatabaseManager.connect();
        launch(args);
    }
}
