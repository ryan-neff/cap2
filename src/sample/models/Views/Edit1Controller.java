package sample.models.Views;

/**
 * Created by JOSH
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Edit1Controller extends Switch implements Initializable {
    private Stage primaryStage;
    @FXML
    private AnchorPane anchorPane;

    public Edit1Controller() {
    }

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleGoEdit2(ActionEvent event) {
        this.getSceneManager().switchTo("edit2");
    }

    @FXML
    private void handleGoHome(ActionEvent event) {
        this.getSceneManager().switchTo("home");
    }
}