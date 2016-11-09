package sample.models.Views;

/**
 * Created by JOSH on 11/8/2016.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.Views.landingPageController;

public class LandingPage extends Application {
    public LandingPage() {
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("landingPageR.fxml"));
        Parent root = (Parent)loader.load();
        landingPageController controller = (landingPageController)loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("landingPage.css");
        stage.setScene(scene);
        stage.show();
        controller.ready(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

