package sample.models.Views;

/**
 * Created by JOSH
 */
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.models.notecardModels.NoteCardModel;
import sample.models.notecardModels.UserModel;
import sample.models.notecardModels.noteCards.StackModel;
import sample.models.notecardModels.noteCards.User;
import sample.models.notecardModels.utils.UserSingleton;

public class QuizpageController extends Switch implements Initializable {
    private Stage primaryStage;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    final ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    final TextArea textArea = new TextArea();

    @FXML
    final ScrollPane scrollPane = new ScrollPane();

    private UserModel userModel;
    private User user;
    private NoteCardModel noteCardModel;
    private Map<String, StackModel> stacks;

    public QuizpageController() {
    }

    public void initialize(URL url, ResourceBundle rb) {

        tempSetup();  //TODO Fix setup once Auth is finished.

      /* TODO Uncomment once Auth is finished
        user = UserSingleton.getInstance().getUser();

        if(user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "How the hell did you get in here? Go log in you menace!");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> this.getSceneManager().switchTo("home"));
        }*/

        beginQuiz();
    }

    private void beginQuiz() {

    }

    private void tempSetup() {
        userModel = new UserModel();
        noteCardModel = new NoteCardModel();
        user = userModel.getLoginInfo("uid_1", "pash");
        stacks = noteCardModel.getAllStacks("uid_1");

        stacks.forEach((stackLabelName, stack) -> {
            StackPane p = new StackPane();

        });

        System.out.println(stacks.toString());

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

