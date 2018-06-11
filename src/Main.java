import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Globals.primaryStage = primaryStage;
        Globals.primaryStage.setTitle("AudioSpectrum");
        Parent fxml = FXMLLoader.load(getClass().getResource("FXML/Menu.fxml"));

        Globals.primaryStage.setScene(new Scene(fxml));
        Globals.primaryStage.show();
    }
}
