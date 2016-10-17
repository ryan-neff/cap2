package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.models.Views.SceneManager;

public class Main extends Application {

    private SceneManager sceneManager;

    public void start(Stage primaryStage) {
        HBox root = new HBox();
        root.setPrefSize(600.0D, 400.0D);
        root.setAlignment(Pos.CENTER);
        Text message = new Text("fail!");
        message.setFont(Font.font("MODENA", 32.0D));
        root.getChildren().add(message);
        Scene scene = new Scene(root);
        this.sceneManager = new SceneManager(scene);
        //this.sceneManager.switchTo("home");
        //^^use this to make it work^^
        //vv use this to test quotemaker vv
        this.sceneManager.switchTo("QuoteMaker");
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
            launch(args);
    }
}
