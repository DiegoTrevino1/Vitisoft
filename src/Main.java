import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("✅ JavaFX App started");

        // Google Maps WebView
        WebView mapView = new WebView();
        WebEngine engine = mapView.getEngine();

        // Google maps blocks features like tile or JS when run via file://
        // especially inside a JavaFX WebView.
        engine.load("https://yourdomain.com/map.html");

        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                System.out.println("Page loaded successfully");
            } else if (newState == javafx.concurrent.Worker.State.FAILED) {
                System.out.println("Page load failed");
            }
        });

        engine.setOnAlert(event -> System.out.println("ALERT: " + event.getData()));

        // Right-side panel with call info
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));

        Label title = new Label("Active Calls");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Button newCallBtn = new Button("New Call");

        // Example call info
        Label callInfo = new Label(
                "Rig: L1, A3\n" +
                        "Status: House Fire\n" +
                        "Address: 607 N Ruby St\n" +
                        "Call received: 12:55\n" +
                        "Dispatched: 12:58\n" +
                        "On Scene: 13:01\n" +
                        "A3 - Transported: 13:04\n" +
                        "Arrived at hospital: 13:07");

        TextArea callNotes = new TextArea();
        callNotes.setPromptText("Call notes");

        Button addUnitBtn = new Button("Add Unit");

        rightPanel.getChildren().addAll(title, newCallBtn, callInfo, callNotes, addUnitBtn);

        // Layout
        BorderPane root = new BorderPane();
        root.setLeft(mapView);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Emergency Dispatch Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
