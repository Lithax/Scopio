package scopio.gui;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
    public static void showConnectionCut() {
        showAlert("Connection Cut", "Connection was cut", "Unexpected Connection Failure", AlertType.ERROR, true);
    }

    public static void showAlert(String title, String header, String content, AlertType alertType, boolean waitAfterShown) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.setResizable(false);
            if (waitAfterShown) {
                alert.showAndWait();
            } else {
                alert.show();
            }
        });
    }
}
