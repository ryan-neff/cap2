package sample.models.Views;

/**
 * Created by JOSH
 */
import sample.models.Views.Switch;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.models.notecardModels.NoteCardModel;

public class Edit2Controller extends Switch implements Initializable {
    private Stage primaryStage;
    @FXML
    private AnchorPane anchorPane;
    private NoteCardModel model = new NoteCardModel();
    public Edit2Controller() {
    }

    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleGoEdit(ActionEvent event) {
        this.getSceneManager().switchTo("edit1");
    }

    @FXML
    private void handleGoHome(ActionEvent event) {
        this.getSceneManager().switchTo("home");
    }
}

