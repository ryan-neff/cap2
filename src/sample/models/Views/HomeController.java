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

public class HomeController extends Switch implements Initializable {
    private Stage primaryStage;

    @FXML
    private AnchorPane anchorPane;


    QuoteMaker maker = new QuoteMaker();
    public HomeController() {
    }

    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleGoEdit(ActionEvent event) {
        this.getSceneManager().switchTo("edit1");
    }

    @FXML
    private void handleGoQuiz(ActionEvent event) {
        this.getSceneManager().switchTo("quizpage");
    }

    @FXML
    private void hangleGoRegister(ActionEvent event) {
        this.getSceneManager().switchTo("registration");
    }

    @FXML
    private void handleGoQuote(ActionEvent event) {
       // this.getSceneManager().switchTo("Quotemaker");
        try {
            this.maker.startQuote(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}