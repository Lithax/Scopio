package scopio;
import javafx.application.Application;
import javafx.stage.Stage;
import scopio.gui.Alerts;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Alerts.showConnectionCut();
    }

    public static void main(String[] args) {
        launch(args);
    }
}