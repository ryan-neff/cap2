//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample.models.Views;

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

    public static void startLandingPage(Stage args) {
       // launch(args);
    }
}
