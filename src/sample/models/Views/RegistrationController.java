package sample.models.Views;

/**
 * Created by JOSH
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends Switch implements Initializable {
    private Stage primaryStage;
    @FXML
    private AnchorPane anchorPane;

    /*
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField reEnterPasswordTextField;
    */

    @FXML
    private TextField loginUsername;

    @FXML
    private TextField loginPassword;

    @FXML
    private TextField registrationUsername;

    @FXML
    private TextField registrationPassword;

    @FXML
    private TextField registrationReEnterPassword;

    @FXML
    private Label errorLabel;

    public RegistrationController() {
    }

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleCancel(ActionEvent event) {

        this.getSceneManager().switchTo("home");
    }

    @FXML
    private void handleRegistrationOk(ActionEvent event) {


        //TOFO: create new user
        //TOGO log in with newly created user
        String usernameText = registrationUsername.getText();
        String passwordText = registrationPassword.getText();
        String reEnterPasswordText = registrationReEnterPassword.getText();


        if(usernameText.equals((""))) {
            SetErrorLabel("Error: Please enter a username.");
            return;
        }
        if(passwordText.equals((""))) {
            SetErrorLabel("Error: Please enter a password.");
            return;
        }

        if(reEnterPasswordText.equals((""))) {
            SetErrorLabel("Error: Please re-enter the password.");
            return;
        }
        if(!passwordText.equals((reEnterPasswordText))) {
            SetErrorLabel("Error: Passwords do not match.");
            return;
        }


        this.getSceneManager().switchTo("home");
    }

    private void SetErrorLabel(String errorMessage) {

        errorLabel.setText(errorMessage);
    }


    public void handleLoginOk(ActionEvent actionEvent) {
    }
}

