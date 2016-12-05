/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studyTool.models.controllersAndViews;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import studyTool.models.controllersAndViews.Switch;
import studyTool.models.notecardModels.NoteCardModel;
import studyTool.models.notecardModels.noteCards.StackModel;
import studyTool.models.notecardModels.utils.UserSingleton;

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
    public HBox topContainer;
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
    //@FXML
    //private Button homeButton;
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
    @FXML   
    private VBox vboxContainer;
    @FXML 
    private HBox bigContainer;

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
        noteCardModel = new NoteCardModel();
        userSingleton = UserSingleton.getInstance();
        stack = userSingleton.getStack();
        setToggleGroup();
        vboxContainer.minWidthProperty().bind(bigContainer.minWidthProperty());
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
      Button homeButton = new Button();
      ImageView im = new ImageView(new Image("resources/Icon.PNG"));
      im.setFitWidth(90);
      im.setFitHeight(60);
      homeButton.setGraphic(im);
      //homeButton.translateXProperty().bind(topContainer.widthProperty().subtract(homeButton.maxWidth(USE_PREF_SIZE)+20));
      topContainer.getChildren().add(homeButton);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if(radbut1.isSelected()){
                    if(answerNum ==1){
                        right.setVisible(true);
                        ++gotRight;
                        stack.getNoteCards().get(globalIdx).setAttemptsCorrect(stack.getNoteCards().get(globalIdx).getAttemptsCorrect() + 1);
                    }
                    else{
                        wrong.setVisible(true);
                        ++gotWrong;
                        stack.getNoteCards().get(globalIdx).setAttemptsCorrect(stack.getNoteCards().get(globalIdx).getAttemptsCorrect() + 1);

                    }

                }
                if(radbut2.isSelected()){
                    if(answerNum ==2){
                        right.setVisible(true);
                        ++gotRight;
                        stack.getNoteCards().get(globalIdx).setAttemptsCorrect(stack.getNoteCards().get(globalIdx).getAttemptsCorrect() + 1);
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
                        stack.getNoteCards().get(globalIdx).setAttemptsCorrect(stack.getNoteCards().get(globalIdx).getAttemptsCorrect() + 1);
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

                    stack.getNoteCards().forEach(x -> {
                        noteCardModel.updateNoteCard(x, userSingleton.getUser().getUserId());
                    });

                    next.setDisable(true);
                    submit.setDisable(true);
                    final Stage statsStage = new Stage(StageStyle.UNDECORATED);
                    statsStage.initModality(Modality.APPLICATION_MODAL);
                    statsStage.initOwner(stage);
                    HBox buttonArea = new HBox();
                    TextArea EditorFld = new TextArea();
                    int total = gotRight + gotWrong;
                    System.out.println("total: " + total);
                    System.out.println("gotright: " + gotRight);
                    System.out.println("gotwrong: " + gotWrong);
                    double decimal = ((double)gotRight/(double)total);
                    
                    int percent = (int) (decimal * 100);
                    
                    String currentText = "Congrats!!!\n" + gotRight + "/"+total+ " \n"+percent+"%";
                    EditorFld.setWrapText(true);
                    EditorFld.setPrefWidth(400);
                    EditorFld.setStyle("-fx-text-fill: black; -fx-font: 45px 'Times New Roman';");
                    EditorFld.setText(currentText);
                    EditorFld.setPrefRowCount(10);
                    EditorFld.setPrefColumnCount(100);
                    EditorFld.setEditable(false);
                    Button stats = new Button();
                    stats.setText("Stats");
                    stats.setStyle(
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
                    buttonArea.getChildren().add(stats);
                    buttonArea.getChildren().add(exit);
                    stats.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent mouseEvent) {
                            statsStage.close();
                            switchViews("Stats");
                        }
                      });
                      exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent mouseEvent) {
                            statsStage.close();

                        }
                      });
                      VBox editArea = new VBox(EditorFld, buttonArea);
                      Scene editScene = new Scene(editArea, 500, 300);
                      statsStage.setScene(editScene);
                      statsStage.show();

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
        stack.getNoteCards().get(globalIdx).setAttempts(stack.getNoteCards().get(globalIdx).getAttempts() + 1);

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
        String answerSwitch = stack.getNoteCards().get(globalIdx).getBack();
        //processBuildIt(answerSwitch);
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
        //String answersSwitch[] = processBuildIt(answer);
        answers.add(answer);
        //answers.add(answersSwitch[0]);
        //answers.add(answersSwitch[1]);
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
    
    public String[] processBuildIt(String answer){
        try{
            // for tilda expansion
            //if (filepath.startsWith("~" + File.separator)) {
                //filepath = System.getProperty("user.home") + filepath.substring(1);
            //}

            //ProcessBuilder builder = new ProcessBuilder("python", "-c", "import sys; import nltk; print \"whatever\"");
            ProcessBuilder builder = new ProcessBuilder("python", "C:/Users/derek/documents/visual studio 2015/Projects/quizNLP/quizNLP/quizNLP.py", answer, "1");
            builder.directory(new File("C:/users/derek/documents/Visual Studio 2015/Projects/test.py/test.py/"));
            System.out.println(builder.directory());
            builder.redirectErrorStream(true);
            Process p = builder.start();
            InputStream stdout = p.getInputStream();
            BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));
            String fullAnswer = "";
            String line;
            while ((line = reader.readLine ()) != null) {
                fullAnswer = fullAnswer.concat(line);
            }
            System.out.println("fullans " + fullAnswer);
            String answers[] = fullAnswer.split("~");
            System.out.println("answers0 "+ answers[0]);
            System.out.println("answer1 " + answers[1]);
            return answers;
        } catch (Exception e){
            System.out.println("error biatch");
            e.printStackTrace();
        }
        return null;
    }
    

}
