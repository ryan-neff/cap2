/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.models.Views;
import java.awt.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.notecardModels.NoteCardModel;
import sample.models.notecardModels.noteCards.StackModel;
import sample.models.notecardModels.utils.UserSingleton;

/**
 *
 * @author derek
 */
public class QuizpageController extends Switch implements Initializable {

    private Stage stage;
    @FXML
    private BorderPane root;
    @FXML
    private Label label;
    @FXML
    public HBox question;
    @FXML
    public HBox questionBody;
    @FXML
    public TextArea answer1;
    @FXML
    public TextArea answer2;
    @FXML
    public TextArea answer3;
    @FXML
    public HBox title;
    @FXML
    private RadioButton radbut1;
    @FXML
    private RadioButton radbut2;
    @FXML
    private RadioButton radbut3;
    @FXML
    private Button submit;
    @FXML
    private Button next;
    @FXML
    private Button homeButton;
    @FXML
    private CheckBox right;
    @FXML
    private Text wrong;
    @FXML
    private HBox container1;
    @FXML
    private HBox container2;
    @FXML
    private HBox container3;

    public ArrayList<String> front = new ArrayList<String>();
    public ArrayList<String> back = new ArrayList<String>();
    public ArrayList<Integer> ids = new ArrayList<Integer>();
    public int globalIdx = 0;
    public int answerNum = -1;
    public int gotRight = 0;
    public int gotWrong = 0;
    private UserSingleton userSingleton;
    private NoteCardModel noteCardModel;
    private StackModel stack;


    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userSingleton = UserSingleton.getInstance();
        stack = userSingleton.getStack();
        setToggleGroup();
        
        setStyle();
        Text titleText = new Text();
        titleText.setText(stack.getCourse() + stack.getName());
        title.getChildren().add(titleText);
        titleText.setStyle("-fx-font: 35px 'Segoe Script';");
        initButtons();
        makeQuestions();
    }

    public void setToggleGroup(){
        ToggleGroup tg = new ToggleGroup();
        radbut1.setToggleGroup(tg);
        radbut2.setToggleGroup(tg);
        radbut3.setToggleGroup(tg);
    }

    public void initButtons(){

        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if(radbut1.isSelected()){
                    if(answerNum ==1){
                        right.setVisible(true);
                        ++gotRight;
                    }
                    else{
                        wrong.setVisible(true);
                        ++gotWrong;
                    }

                }
                if(radbut2.isSelected()){
                    if(answerNum ==2){
                        right.setVisible(true);
                        ++gotRight;
                    }
                    else{
                        wrong.setVisible(true);
                        ++gotWrong;
                    }

                }
                if(radbut3.isSelected()){
                    if(answerNum ==3){
                        right.setVisible(true);
                        ++gotRight;
                    }
                    else{
                        wrong.setVisible(true);
                        ++gotWrong;
                    }

                }
                radbut1.setDisable(true);
                radbut2.setDisable(true);
                radbut3.setDisable(true);
            }
        });


        next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                questionBody.getChildren().clear();
                answer1.clear();
                answer2.clear();
                answer3.clear();
                radbut1.setSelected(false);
                radbut2.setSelected(false);
                radbut3.setSelected(false);
                answerNum = -1;
                right.setVisible(false);
                wrong.setVisible(false);
                radbut1.setDisable(false);
                radbut2.setDisable(false);
                radbut3.setDisable(false);
                if(stack.getNoteCards().size() - 1 == globalIdx){
                    System.out.println("stop");
                    System.out.println("stop it");
                }else {
                    ++globalIdx;
                    makeQuestions();
                }

            }
        });

        homeButton.setOnMouseClicked(event -> {
            switchViews("landingPage");
        });

    }

    public void makeQuestions(){
        //makeQuestion();
        makeQuestionBody();
        makeAnswer1();//this calls the other makeAnswers

    }

    public void makeQuestion(){
        Text frontText = new Text();
        String textBody = Integer.toString(globalIdx);
        frontText.setText(textBody);
        frontText.setStyle("-fx-font: 22px 'Times New Roman';");
        question.getChildren().add(frontText);
    }
    public void makeQuestionBody(){
        Text frontText = new Text();
        frontText.setText(stack.getNoteCards().get(globalIdx).getFront());
        frontText.setStyle("-fx-font: 18px 'Times New Roman';");
        Label answer1Lab = new Label();
        answer1Lab.setText(stack.getNoteCards().get(globalIdx).getFront());
        frontText.setWrappingWidth(answer1.getWidth());
        questionBody.getChildren().add(frontText);
    }
    public void makeAnswer1(){
        ArrayList<String> answers = new ArrayList<String>();
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        indexes.add(0);
        indexes.add(1);
        indexes.add(2);
        answers = makeAnswers();
        System.out.println("answerssize: " + answers.size());
        int randomPick = (int)(Math.random() * 2);
        int randIdx = indexes.get(randomPick);
        System.out.println("randomidx: " + randIdx);
        //indexes.remove(randIdx);
        indexes.remove(randIdx);
        if(randIdx == 0){//if randIdx =s to the idx of the answer we set the global id
            answerNum = 1;
        }
        String finalAnswer = answers.get(randIdx);
        System.out.println("finalAnswer1: " + finalAnswer);
        Text textAnswer = makeTextObj(finalAnswer);
        textAnswer.setWrappingWidth(800);
        //answer1.setAlignment(Pos.CENTER);
        answer1.appendText(finalAnswer);
        makeAnswer2(indexes, answers);


    }

    public Text makeTextObj(String finalAnswer){

        Text textAnswer = new Text();
        textAnswer.setText(finalAnswer);
        textAnswer.setStyle("-fx-font: 18px 'Times New Roman';");
        return textAnswer;
    }

    public ArrayList<String> makeAnswers(){

        ArrayList<String> answers = new ArrayList<String>();
        int size = stack.getNoteCards().size();
        String answer = stack.getNoteCards().get(globalIdx).getBack();
        int random1 = (int)(Math.random() * size);
        int random2 = (int)(Math.random() * size);
        String randAnswer1 = "";
        String randAnswer2 = "";
        if(globalIdx != random1){
            randAnswer1 = stack.getNoteCards().get(random1).getBack();
        }
        else{
            randAnswer1 = stack.getNoteCards().get(random1 + 1).getBack();
        }
        if(globalIdx != random2){
            randAnswer2 = stack.getNoteCards().get(random2).getBack();
        }
        else{
            randAnswer2 = stack.getNoteCards().get(random2 + 1).getBack();
        }
        answers.add(answer);
        answers.add(randAnswer1);
        answers.add(randAnswer2);

        return answers;
    }

    public void makeAnswer2(ArrayList<Integer> indexes, ArrayList<String> answers){
        int randomPick = (int)(Math.random() * 1);
        int randIdx = indexes.get(randomPick);
        System.out.println("randomidx2: " + randIdx);
        System.out.println("indexes.size: " + indexes.size());
        //indexes.remove(randIdx);
        indexes.remove(randIdx);
        if(randIdx == 0){//if randIdx =s to the idx of the answer we set the global id
            answerNum = 2;
        }

        String finalAnswer = answers.get(randIdx);
        Text textAnswer = makeTextObj(finalAnswer);
        textAnswer.setWrappingWidth(800);
        answer2.appendText(finalAnswer);
        makeAnswer3(indexes, answers);
    }
    public void makeAnswer3(ArrayList<Integer> indexes, ArrayList<String> answers){
        if(answerNum == -1){
            answerNum = 3;
        }
        int idx = indexes.get(0);
        String finalAnswer = answers.get(idx);
        System.out.println("idx: " + idx);
        Text textAnswer = makeTextObj(finalAnswer);
        textAnswer.setWrappingWidth(800);
        answer3.appendText(finalAnswer);
    }

    public void setStyle(){
        root.setStyle("-fx-background-color:  #0080ff");
        //container2.setStyle("-fx-background-color: cornsilk; -fx-background-insets: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, purple, 10, 0, 0, 0);");
        //container3.setStyle("-fx-background-color: cornsilk; -fx-background-insets: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, purple, 10, 0, 0, 0);");
        //container1.setStyle("-fx-background-color: cornsilk; -fx-background-insets: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, purple, 10, 0, 0, 0);");
    }

    private void switchViews(final String view) {
        this.getSceneManager().switchTo(view);

    }

}
