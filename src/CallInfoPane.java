import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Encapsulates the UI pane on the right for displaying active call info.
 */
public class CallInfoPane {

    private final VBox pane;

    /**
     * Constructs the call info pane with sample content.
     */
    public CallInfoPane() {
        pane = new VBox(10);
        pane.setPadding(new Insets(10));

        Label title = new Label("Active Calls");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button newCallBtn = new Button("New Call");

        // just example text for now"
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

        pane.getChildren().addAll(title, newCallBtn, callInfo, callNotes, addUnitBtn);
    }

    /**
     * Returns the VBox pane containing call info UI.
     * 
     * @return The VBox pane.
     */
    public VBox getPane() {
        return pane;
    }
}
