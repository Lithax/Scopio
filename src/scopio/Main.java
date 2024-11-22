package scopio;
import javafx.application.Application;
import javafx.stage.Stage;
import scopio.gui.Alerts;

/**
 * @see <a href="https://github.com/Lithax/Scopio">Scopio Repository</a>
 * @see <a href="mailto:Lithax@outlook.com">E-mail me</a>
 * @author Lithax
 * @version 22-11-24
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Alerts.showConnectionCut();
    }

    public static void main(String[] args) {
        launch(args);
    }
}