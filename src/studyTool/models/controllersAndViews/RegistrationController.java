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

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.util.Random;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class RegistrationController extends Switch implements Initializable {
    UserSingleton singleton;
    private Stage primaryStage;
    

    
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField reEnterPasswordTextField;
    

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;
    
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
    @FXML
    private Label register;
   
    TextField firstName;
    TextField lastName;
    TextField userBox;
    TextField pBox;
    TextField p2Box;

    public RegistrationController() {
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initRegistration();
    }
    
    public void initRegistration(){
        register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                final Stage editStage = new Stage(StageStyle.UNDECORATED);
                editStage.initModality(Modality.APPLICATION_MODAL);
                editStage.initOwner(primaryStage);
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));
           
                Text scenetitle = new Text("Register");
                scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                grid.add(scenetitle, 0, 0, 2, 1);

                Label fName = new Label("first Name:");
                grid.add(fName, 0, 1);

                firstName = new TextField();
                grid.add(firstName, 1, 1);

                Label lName = new Label("Last Name");
                grid.add(lName, 0, 2);

                lastName = new TextField();
                grid.add(lastName, 1, 2);
                
                Label userName = new Label("User Name");
                grid.add(userName, 0, 3);

                userBox = new TextField();
                grid.add(userBox, 1, 3);
                
                Label pWord = new Label("Password");
                grid.add(pWord, 0, 4);

                pBox = new TextField();
                grid.add(pBox, 1, 4);
                
                Label p2Word = new Label("Password");
                grid.add(p2Word, 0, 5);

                p2Box = new TextField();
                grid.add(p2Box, 1, 5);
                HBox buttonArea = new HBox();
                buttonArea.setSpacing(10);
                Button submit = new Button();
                    submit.setText("Submit");
                    submit.setStyle(
                          "-fx-background-radius: 15em; " +
                          "-fx-min-width: 60px; " +
                          "-fx-min-height: 30px; " +
                          "-fx-max-width: 60px; " +
                          "-fx-max-height: 30px;"
                    );

                    Button exit = new Button();
                    exit.setText("Exit");
                    exit.setStyle(
                          "-fx-background-radius: 15em; " +
                          "-fx-min-width: 60px; " +
                          "-fx-min-height: 30px; " +
                          "-fx-max-width: 60px; " +
                          "-fx-max-height: 30px;"
                    );
                    buttonArea.getChildren().add(submit);
                    buttonArea.getChildren().add(exit);
                    submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent mouseEvent) {
                            handleRegistrationOk();
                        }
                      });
                      exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent mouseEvent) {
                            editStage.close();

                        }
                      }); 
                VBox topContainer = new VBox();
                topContainer.getChildren().addAll(grid, buttonArea);
                
                Scene editScene = new Scene(topContainer, 500, 300);
                editStage.setScene(editScene);
                editStage.show();
            }
        });
    }

    /*@FXML
    private void handleCancel(ActionEvent event) {

        this.getSceneManager().switchTo("home");
    }*/

    
    private void handleRegistrationOk() {


        //TOFO: create new user
        //TOGO log in with newly created user
        String usernameText = userBox.getText();
        String passwordText = pBox.getText();
        String reEnterPasswordText = p2Box.getText();
        String FName = firstName.getText();
        String LName = lastName.getText();

        if(firstName.equals((""))) {
            showAlert("First Name");
            return;
        }
        if(lastName.equals((""))) {
            showAlert("Last Name");
            return;
        }
        if(userBox.equals((""))) {
            showAlert("User Name");
            return;
        }

        if(pBox.equals((""))) {
            showAlert("Password");
            return;
        }
        if(!p2Box.getText().equals(pBox.getText())) {
            showAlert("Password Confirmation");
            return;
        }

        singleton = UserSingleton.getInstance();
        UserModel UM = new UserModel();
        User user = new User();
        user.setFirstName(FName);
        user.setLastName(LName);
        user.setUserId(usernameText);
        UM.createUser(user, passwordText);
        singleton.setUser(user);
        this.getSceneManager().switchTo("landingPage");

    }

    /*private void SetErrorLabel(String errorMessage) {

        errorLabel.setText(errorMessage);
    }


   */
    public void showAlert(String Message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        //alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText("You Didn't Correctly Enter " + Message);

        alert.showAndWait();
    }
    
    public void handleLoginOk(ActionEvent actionEvent) {
        singleton = UserSingleton.getInstance();
        UserModel UM = new UserModel();
        singleton.setUser(UM.getLoginInfo(loginUsername.getText(), loginPassword.getText()));
        if(singleton.getUser() == null)
        {
            showAlert("Your User Information");
        }
        else
        {
            this.getSceneManager().switchTo("landingPage");
        }
    }
}

