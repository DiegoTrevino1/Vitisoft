import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Encapsulates a WebView for displaying a Google Map.
 */
public class MapViewPane {

    private final WebView webView;
    private final WebEngine webEngine;

    /**
     * Constructs a MapViewPane and loads the specified map URL.
     * 
     * @param mapUrl The URL to the map HTML page.
     */
    public MapViewPane(String mapUrl) {
        webView = new WebView();
        webEngine = webView.getEngine();

        webEngine.load(mapUrl);

        // Debug: check load success or failure
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                System.out.println("Map loaded successfully");
            } else if (newState == Worker.State.FAILED) {
                System.out.println("Failed to load map");
            }
        });

        // JS alert debugging
        webEngine.setOnAlert(event -> System.out.println("JS Alert: " + event.getData()));
    }

    /**
     * Returns the WebView component.
     * 
     * @return The WebView displaying the map.
     */
    public WebView getWebView() {
        return webView;
    }
}
