package studyTool.models.controllersAndViews;
import studyTool.models.notecardModels.utils.UserSingleton;
import studyTool.models.notecardModels.UserModel;
import studyTool.models.notecardModels.noteCards.User;
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
    UserSingleton singleton;
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
    private TextField registrationFName;
    
    @FXML
    private TextField registrationLName;

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
    anchorPane.setStyle("-fx-background-color:  #0080ff");
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
        String FName = registrationFName.getText();
        String LName = registrationLName.getText();

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


        this.getSceneManager().switchTo("landingPage");
        
        singleton = UserSingleton.getInstance();
        UserModel UM = new UserModel();
        User user = new User();
        user.setFirstName(FName);
        user.setLastName(LName);
        user.setUserId(usernameText);
        UM.createUser(user, passwordText);
    }

    private void SetErrorLabel(String errorMessage) {

        errorLabel.setText(errorMessage);
    }


    public void handleLoginOk(ActionEvent actionEvent) {
        singleton = UserSingleton.getInstance();
        UserModel UM = new UserModel();
        singleton.setUser(UM.getLoginInfo(loginUsername.getText(), loginPassword.getText()));
        if(singleton.getUser() == null)
        {
            SetErrorLabel("Incorrect Username and/or Password.");
        }
        else
        {
            this.getSceneManager().switchTo("landingPage");
        }
    }
}

