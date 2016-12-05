package studyTool;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import studyTool.models.controllersAndViews.SceneManager;

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

        scene.getStylesheets()
                .add(getClass()
                        .getResource("notecard.css")
                        .toExternalForm());
        scene.getStylesheets()
                .add(getClass()
                        .getResource("landingPage.css")
                        .toExternalForm());
        this.sceneManager = new SceneManager(scene);
        this.sceneManager.switchTo("registration");;

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
