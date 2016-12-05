/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studyTool.models.controllersAndViews;

import java.net.MalformedURLException;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.models.Views.HelloWorld;
import studyTool.models.notecardModels.NoteCardModel;
import studyTool.models.notecardModels.noteCards.NoteCard;
import studyTool.models.notecardModels.noteCards.StackModel;
import studyTool.models.notecardModels.utils.UserSingleton;


public class SessionController extends Switch implements Initializable {

    private Stage primaryStage;
    @FXML
    private Pane wrap;

    final Random random = new Random();
  
    Boolean isDrag = true;
    final Colors colors = new Colors();
    HBox hTimeline = new HBox();
    Stack focusStack;
    Label newQuote = new Label();
    StackPane sp = new StackPane();
    final ObjectProperty<Label> selectedQuote = new SimpleObjectProperty<>();
    ArrayList<Stack> stackList = new ArrayList<Stack>();
    String cardIdx;
    //Pane wrap;
    MessageBoard messageBoard;
    Label background = new Label();
    Label focusLabel;
    ScrollPane scrollPane;//This is where the notecards at the bottom go
    int StackId = 0;
    Scene fullScreen;
    ImageView picIcon;
    NoteCardModel model;
    UserSingleton userSingleton;
    StackModel stackModel;

  public void startQuote(final Stage primaryStage) throws Exception {

    wrap = new Pane();
    messageBoard = new MessageBoard();//init messageBoard
    messageBoard.setStyle("-fx-background-color: cornsilk; -fx-background-insets: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, purple, 10, 0, 0, 0);");
    messageBoard.setPrefSize(1000, 700);
    model = new NoteCardModel();
    userSingleton = UserSingleton.getInstance();
    stackModel = userSingleton.getStack();


      //init first stack
    String course = stackModel.getCourse();
    String stackName = stackModel.getName();
    makeStack(course, stackName);
    primaryStage.setTitle("NotePad Breeze");

    HBox controlPanel = new HBox(10);
    controlPanel.setStyle("-fx-background-color: #0080ff; -fx-padding: 10;");
    controlPanel.setAlignment(Pos.TOP_LEFT);
    ImageView listen = new ImageView(new Image("resources/listen.png"));
    listen.setFitWidth(30);
    listen.setFitHeight(30);
    listen.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
                helloWorld hw = new helloWorld();
                HelloWorld HW = new HelloWorld(hw);
                String resultText = HW.run();
            
       }
    });
    listen.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            
            //voiceActivation();
            listen.setCursor(Cursor.HAND);
                  
          }
    });
    
    controlPanel.getChildren().add(listen);

    // create a control panel for the message board.
    VBox controls = new VBox(10);
    controls.setStyle("-fx-background-color: #0080ff; -fx-padding: 10;");
    controls.setAlignment(Pos.TOP_CENTER);

    // create some sliders to modify properties of the existing quote.
    final LabeledSlider widthSlider = new LabeledSlider("Width", 100, 1000, 100);
    final LabeledSlider heightSlider = new LabeledSlider("Height", 150, 700, 100);
    final LabeledSlider layoutXSlider = new LabeledSlider("X Pos", 0, messageBoard.getWidth(), 0);
    layoutXSlider.slider.maxProperty().bind(messageBoard.widthProperty());
    final LabeledSlider layoutYSlider = new LabeledSlider("Y Pos", 0, messageBoard.getHeight(), 0);
    layoutYSlider.slider.maxProperty().bind(messageBoard.heightProperty());
   
    initDropDown();
    initMessageBoard(widthSlider, heightSlider);
    /*final Label quotedText = new Label();
    quotedText.setWrapText(true);
    quotedText.setStyle("-fx-font-size: 16px;");*/
    
    
    //This is the listener for the controls
    selectedQuote.addListener(new ChangeListener<Label>() {
      @Override public void changed(ObservableValue<? extends Label> observableValue, Label oldQuote, final Label newQuote) {
          
        if (oldQuote != null) {
          // disassociate the sliders from the old quote.
          
          widthSlider.slider.valueProperty().unbindBidirectional(oldQuote.prefWidthProperty());
          heightSlider.slider.valueProperty().unbindBidirectional(oldQuote.prefHeightProperty());
          layoutXSlider.slider.valueProperty().unbindBidirectional(sp.layoutXProperty());
          layoutYSlider.slider.valueProperty().unbindBidirectional(sp.layoutYProperty());
        }

        if (newQuote != null) {
          // associate the sliders with the new quote.
          widthSlider.slider.valueProperty().bindBidirectional(newQuote.prefWidthProperty());
          heightSlider.slider.valueProperty().bindBidirectional(newQuote.prefHeightProperty());
          
          layoutXSlider.slider.valueProperty().bindBidirectional(sp.layoutXProperty());
          layoutYSlider.slider.valueProperty().bindBidirectional(sp.layoutYProperty());

          
          //dragDrop();
          setupLoadWoDrag();
          //quotedText.textProperty().bind(newQuote.textProperty());
        }
        
      }
    });
    
    //set all of the objects for the sidebar area
    Text relatedTitle = new Text();
    relatedTitle.setText("Related Stacks");
    relatedTitle.setStyle("-fx-text-fill: #6666ff; -fx-font: 16px 'Times New Roman';");
    
    ImageView logo = new ImageView(new Image("resources/logo1.PNG"));
    logo.setFitWidth(180);
    logo.setFitHeight(60);
    VBox relatedNotecards = getRelated();
    relatedNotecards.setStyle(" -fx-background-color: cornsilk; -fx-background-insets: 3; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, #ccccff, 10, 0, 0, 0);");
    Text dimensions = new Text();
    dimensions.setText("Notecard\nDimensions");
    dimensions.setStyle("-fx-text-fill: #6666ff; -fx-font: 16px 'Times New Roman';");
    controls.getChildren().addAll(logo, controlPanel, new Separator(), relatedTitle, new Separator(), relatedNotecards, new Separator(), dimensions, new Separator(), widthSlider, heightSlider, layoutXSlider, layoutYSlider, new Separator());
    controls.setPrefWidth(180);
    controls.setMinWidth(180);
    controls.setMaxWidth(Control.USE_PREF_SIZE);

    

    // layout the scene.
    HBox layout = new HBox();
    layout.getChildren().addAll(controls, messageBoard);
    HBox.setHgrow(messageBoard, Priority.ALWAYS);
    final Scene scene = new Scene(layout);
    scene.getStylesheets()
             .add(getClass()
                .getResource("notecard.css")
               .toExternalForm());
        
    
    // allow the selected quote to be deleted.
    /*scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
          //System.out.println("we are in key event");
          //System.out.println(messageBoard.getChildren().size());
            for(int i = 0; i < messageBoard.getChildren().size(); ++i){
                messageBoard.getChildren().get(i);
            } 
        }
        if(keyEvent.getCode().equals(KeyCode.N)){
            nextCard();
        }
        if(keyEvent.getCode().equals(KeyCode.F)){
            flipCard();
        }
        if(keyEvent.getCode().equals(KeyCode.B)){
            PrevCard();
        }
        
        if(keyEvent.getCode().equals(KeyCode.UP)){
            widthSlider.slider.increment();
        }
        if(keyEvent.getCode().equals(KeyCode.DOWN)){
            heightSlider.slider.increment();
        }
        
      }
    });*/
    setKeyEvents(scene);
    
    

    // show the stage.
    primaryStage.setScene(scene);
    primaryStage.show();
    //dragDrop();
    setupLoadWoDrag();
    //animate.setOnAction(e-> makeAnimation(newQuote));
  }
  
  public void makeStack(String course, String name){
      String title = course +" " + name;
      Stack stack = new Stack(title);
      stackModel = model.getSingleStack(name, userSingleton.getUser().getUserId());
      stack.notecards = (ArrayList<NoteCard>) stackModel.getNoteCards();
      List<String> related = model.getStacksByCourse(course, userSingleton.getUser().getUserId());
      //related.add("Unit 2");
      stack.related = (ArrayList<String>) related;
      focusStack = stack;
      stackList.add(focusStack);
      
      makeTimeline();
      if(selectedQuote.get() != null){
          postMessageBoard();
      }
      
  }
  //init and post message boards have different signatures because of constraints with the change listener in start
  public void initMessageBoard(LabeledSlider widthSlider, LabeledSlider heightSlider){
      String myLabel = "myLabel";
      String backLabel = "back";
      final StackPane sp = messageBoard.post(focusStack.notecards.get(focusStack.index).getFront(), colors.next());
        //focusStack.incIndex();
        for(int i = 0; i < 3; ++i){
            if(myLabel.equals(sp.getChildren().get(i).getId())){
                newQuote = (Label)sp.getChildren().get(i);
            }
            
        }
        
        selectedQuote.set(newQuote);
        // make the new quote the selected quote when it is been clicked.
        sp.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            System.out.println("we in on mouse clicked on message board");
            focusStack = stackList.get(0);
            selectedQuote.set(newQuote);
            sp.toFront();
            messageBoard.post();
          }
        });
        
        widthSlider.slider.valueProperty().bindBidirectional(newQuote.prefWidthProperty());
        heightSlider.slider.valueProperty().bindBidirectional(newQuote.prefHeightProperty());
        
  }
  
 
  
  public void postMessageBoard(){
      System.out.println("in postMessageBoard");
      String myLabel = "myLabel";
      final StackPane sp = messageBoard.post(focusStack.notecards.get(focusStack.index).getFront(), colors.next());
      
      int numChildren = messageBoard.getChildren().size();
      System.out.println("numChildren in messageboard in postMessageBoard " + numChildren);
        //focusStack.incIndex();
        for(int i = 0; i < 3; ++i){
            if(myLabel.equals(sp.getChildren().get(i).getId())){
                newQuote = (Label)sp.getChildren().get(i);
            }
        }
        
        selectedQuote.set(newQuote);
        
        
        
  }
   public void setKeyEvents(Scene scene){
      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
          //System.out.println("we are in key event");
          //System.out.println(messageBoard.getChildren().size());
            for(int i = 0; i < messageBoard.getChildren().size(); ++i){
                messageBoard.getChildren().get(i);
            } 
        }
        
        if(keyEvent.getCode().equals(KeyCode.N)){
            nextCard();
        }
        if(keyEvent.getCode().equals(KeyCode.B)){
            PrevCard();
        }
        if(keyEvent.getCode().equals(KeyCode.F)){
            flipCard();
        }
        
       
        
      }
    });
    
  }

  public VBox getRelated(){//This displays the related notecards in teh sidebar up to 10
      VBox temp = new VBox();
     
      
      for(int i = 0; i < focusStack.related.size(); ++i){
          Label label = new Label();
          String relatedResults = focusStack.related.get(i);
          String[] resultsArr = relatedResults.split(" ");//split up the db keywords and send them to makeStack
          final String name = focusStack.related.get(i);
          final String course = stackModel.getCourse();

          label.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            makeStack(course, name);
          }
        });
          label.setText(relatedResults);
          label.setStyle("-fx-background-radius: 5; -fx-background-color: white; -fx-text-fill: black; -fx-font: 12px 'Segoe Script'; -fx-padding:10;");
          
          //label.setStyle("-fx-box-shadow: 0 0 0 3px #fff, 0 0 0 5px #ddd, 0 0 0 10px #fff, 0");
          label.setWrapText(true);
          label.setAlignment(Pos.CENTER);
          label.setTextAlignment(TextAlignment.CENTER);
          final DropShadow dropShadow = new DropShadow();
          final Glow glow = new Glow();
          label.setEffect(dropShadow);
          label.setWrapText(true);
          label.setMaxWidth(Double.MAX_VALUE);
          temp.getChildren().add(label);
      }
      
      return temp;
  }
  


    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleGoQuote(ActionEvent event) throws Exception {

        this.getSceneManager().switchTo("SessionController");
        startQuote(primaryStage);
    }

    class LabeledSlider extends HBox {
    Label label;
    Slider slider;
    LabeledSlider(String name, double min, double max, double value) {
      slider = new Slider(min, max, value);
      label = new Label(name);
      label.setPrefWidth(60);
      label.setLabelFor(slider);
      this.getChildren().addAll(label, slider);
    }
  }

  // a board on which you can place messages.
  class MessageBoard extends Pane {
    MessageBoard() {
      setId("messageBoard");
    }
    
    void post(){//This function sets the scrollpane at the bottom with the timeline of notecards
        System.out.println("we in post for sho");
        System.out.println("Number of children before remove: " + messageBoard.getChildren().size());
        this.getChildren().remove(scrollPane);
        System.out.println("Number of children after remove: " + messageBoard.getChildren().size());
        ArrayList<Label> effect = focusStack.timelineLabels;
        System.out.println("focusStack Title: " + focusStack.title);
        effect.get(focusStack.index).setScaleX(2.5);
        effect.get(focusStack.index).setTranslateY(-50);
        focusStack.IdxProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
     
            timelineRise();
     
      });
      
       
        HBox hTimeline = focusStack.timeline;
        
        hTimeline.setPadding(new Insets(50,0,0,0));
        double width = messageBoard.getWidth();
        
        scrollPane = new ScrollPane(hTimeline);
        //scrollPane.relocate(10, 580);
        
        scrollPane.translateYProperty().bind(messageBoard.heightProperty().subtract(200));
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMinHeight(120);
        scrollPane.setMinWidth(980);
        scrollPane.setMaxWidth(messageBoard.getWidth()-20);
        messageBoard.widthProperty().addListener((observable, oldValue, newValue) -> {
          //System.out.println("fs.get " + focusStack.getIdx());
          scrollPane.setMaxWidth(messageBoard.getWidth()-20);
      });
        scrollPane.setHmin(0);
        scrollPane.setHmax(focusStack.notecards.size()-1);
        System.out.println("messageboardheight: " + messageBoard.getHeight());
        System.out.println("scrollpane height: " + hTimeline.getHeight());
        
        scrollPane.setLayoutX(10);
        scrollPane.translateYProperty().bind(messageBoard.heightProperty().subtract(scrollPane.getMinHeight()));
        
        
        
        
        this.getChildren().addAll(scrollPane);
    }

    StackPane post(String quote, Color color) {
      //System.out.println("in messageboard.post");
      final Label label = new Label(quote);
      label.setId("myLabel");
      //-fx-background-image: url('flipThickGrey.png');ladder(" + bgColor +", lavender 49%, midnightblue 50%);-fx-opacity: 0.5;
      String bgColor = "#" + color.deriveColor(color.getHue(), color.getSaturation(), color.getBrightness(), random.nextDouble() * 0.5 + 0.5).toString().substring(2, 10);
      label.setStyle("-fx-background-radius: 5; -fx-background-color: linear-gradient(to bottom, " + bgColor + ", derive(" + bgColor + ", 20%)); -fx-text-fill:black;  -fx-font: 18px 'Segoe Script'; -fx-font-weight: bold;; -fx-padding:10; -fx-border-color: white; -fx-border-width: 4px; -fx-background-image: url('resources/notecardBackFixed.png');");
      label.setWrapText(true);
      label.setAlignment(Pos.CENTER);
      label.setTextAlignment(TextAlignment.CENTER);
      final DropShadow dropShadow = new DropShadow();
      final Glow glow = new Glow();
      label.setEffect(dropShadow);
      

      // give the quote a random fixed size and position.
      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      int prefSizeX = 500;//This sets the default size of the notecard
      int prefSizeY = 300;
      
      label.setPrefSize(prefSizeX, prefSizeY);
      label.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      int x = random.nextInt((int) Math.floor((this.getPrefWidth()-20) - label.getPrefWidth()));
      int y = random.nextInt((int) Math.floor((this.getPrefHeight()-20) - label.getPrefHeight()));
      
      label.relocate(
        x,y
      );
      
      
      // Each label has its own stackpane, the id is a global counter
      StackPane sp = new StackPane();
      String setId = Integer.toString(StackId);
      ++StackId;
      sp.setId(setId);
      final Delta dragDelta = new Delta();
      
      //Set the mouse events for the stack pane
      ////////////////////////////////////////////
      sp.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            System.out.println("we in on mouse clicked in post");
            selectedQuote.set(newQuote);
            int id = Integer.parseInt(sp.getId());
            focusStack = stackList.get(id);
            sp.toFront();
            messageBoard.post();
            
            
          }
        });
      sp.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          
          dragDelta.x = sp.getLayoutX() - mouseEvent.getSceneX();
          dragDelta.y = sp.getLayoutY() - mouseEvent.getSceneY();
          sp.setCursor(Cursor.MOVE);
        }
      });
      sp.setOnMouseReleased(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
            
          sp.setCursor(Cursor.HAND);
        }
      });
      sp.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
            
          sp.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
          sp.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
        }
      });
      sp.setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
            
          sp.setCursor(Cursor.HAND);
          dropShadow.setInput(glow);
        }
      });
      sp.setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
            
          dropShadow.setInput(null);
        }
      });
      //Set five objects of each notecard
      
      //1) set the icon and drop down menu
     VBox container = initDropDown();
     
     //call load images
     
      
      
      
      //2) Set the title
      String title = focusStack.title.toUpperCase();
      Text notecardTitle = new Text();
      notecardTitle.setText(title);
      notecardTitle.setStyle("-fx-text-fill: #6666ff; -fx-font: 16px 'Times New Roman';");
      String totalCards = Integer.toString(focusStack.notecards.size());
      
      
     //3) Set the notecard counter 
      Text numNotecards = new Text();
      focusStack.IdxProperty().addListener((observable, oldValue, newValue) -> {
          //System.out.println("fs.get " + focusStack.getIdx());
          cardIdx = Integer.toString(focusStack.getIdx());
            
            numNotecards.setText(cardIdx+"|"+totalCards);
      });
      cardIdx = Integer.toString(focusStack.getIdx());
      numNotecards.setText(cardIdx+"|"+totalCards);
      
      //4) Set the flip Button
      Button flipIt = new Button();
      ImageView im = new ImageView(new Image("resources/flipThickGrey.png"));
      im.setFitWidth(20);
      im.setFitHeight(20);
      flipIt.setGraphic(im);
      Tooltip tooltip = new Tooltip("See Back");
      tooltip.setStyle("-fx-font-size:12px;");
      Tooltip.install(flipIt, tooltip);
      
     
      flipIt.setOnAction(e -> {
          
          flipCard();
          
      });
      
      //5) Set the next Button
      Button next = new Button();
      next.setStyle("-fx-font: 1px;");
      ImageView imNext = new ImageView(new Image("resources/rightArrowGrey.png"));
      imNext.setFitWidth(20);
      imNext.setFitHeight(20);
      next.setGraphic(imNext);
      /*Tooltip toolTip = new Tooltip("Next Notecard");
      tooltip.setStyle("-fx-font-size:12px;");
      Tooltip.install(next, toolTip);*/
      next.setOnAction(e -> {
          nextCard();
      });
      
      focusStack.label = label;
      //Fix their positions
      StackPane.setAlignment(flipIt, Pos.BOTTOM_LEFT);
      StackPane.setAlignment(next, Pos.BOTTOM_RIGHT);
      StackPane.setAlignment(container, Pos.TOP_LEFT);
      StackPane.setAlignment(notecardTitle, Pos.TOP_CENTER);
      StackPane.setAlignment(numNotecards, Pos.TOP_RIGHT);
      sp.getChildren().addAll(label, flipIt, next, container, notecardTitle, numNotecards);
      sp.relocate(x, y);
      //System.out.println("sp x:y " +sp.getLayoutX() + ":" + sp.getLayoutY());
      focusStack.sp = sp;
      this.getChildren().addAll(focusStack.sp);
      sp.translateYProperty().addListener((obs, oldValue, newValue) -> {
          this.getChildren().addAll(focusStack.sp);
      });
      

      return sp;
    }
  }
  public void flipCard(){
      if(focusStack.notecards.get(focusStack.index).getIsFront()){
              focusStack.label.setText(focusStack.notecards.get(focusStack.index).getBack());
              focusStack.notecards.get(focusStack.index).setIsFront(false);
      }
      else{
              focusStack.label.setText(focusStack.notecards.get(focusStack.index).getFront());
              focusStack.notecards.get(focusStack.index).setIsFront(true);
      }
  }
   public void PrevCard(){
      
      if(focusStack.index > 0){//index has to restart when it hits the size of the array
              focusStack.decIndex();
      }
      else{
              focusStack.index = focusStack.notecards.size()-1;
      }
      
      focusStack.label.setText(focusStack.notecards.get(focusStack.index).getFront());
      /*focusStack.projector.imageFiles.clear();
      focusStack.projector.currentImageView.setImage(null);
      focusStack.projector.currentIndex = -1;
      setupLoadWoDrag();*/
  }
  
  public void nextCard(){
      
      if(focusStack.index < focusStack.notecards.size()-1){//index has to restart when it hits the size of the array
              focusStack.incIndex();
      }
      else{
              focusStack.resetIndex();
      }
      /*if(focusStack.notecards.get(focusStack.index).isHasPics()){
          picIcon.setVisible(true);
      }
      else{
          picIcon.setVisible(false);
      }*/
      focusStack.label.setText(focusStack.notecards.get(focusStack.index).getFront());
      /*focusStack.projector.imageFiles.clear();
      focusStack.projector.currentImageView.setImage(null);
      focusStack.projector.currentIndex = -1;
      setupLoadWoDrag();*/
  }

    public VBox initDropDown(){
        VBox container = new VBox();
        container.setMaxHeight(focusStack.label.getHeight()/2);
        container.setId("menuContainer");
        VBox menu = new VBox();
        menu.setId("menu");
        menu.setStyle(" -fx-background-color: #6666ff; -fx-text-fill: white; -fx-font: 12px 'Segoe Script'; -fx-padding:3;");

        container.setPrefWidth(75);
        container.setMinWidth(USE_PREF_SIZE);
        container.setMaxWidth(USE_PREF_SIZE);
        Label edit = new Label();
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                edit.setStyle("-fx-text-fill:black");
                final Stage editStage = new Stage(StageStyle.UNDECORATED);
                editStage.initModality(Modality.APPLICATION_MODAL);
                editStage.initOwner(primaryStage);
                HBox buttonArea = new HBox();

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
                TextArea EditorFld = new TextArea();
                String currentText;
                if(focusStack.notecards.get(focusStack.index).getIsFront()){
                    currentText = focusStack.notecards.get(focusStack.index).getFront();

                }
                else{
                    currentText = focusStack.notecards.get(focusStack.index).getBack();

                }
                EditorFld.setText(currentText);
                EditorFld.setPrefRowCount(10);
                EditorFld.setPrefColumnCount(100);
                EditorFld.setWrapText(true);
                EditorFld.setPrefWidth(400);
                submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent mouseEvent) {
                        String sent = EditorFld.getText();
                        NoteCard currentNotecard = focusStack.notecards.get(focusStack.index);

                        if (currentNotecard.getIsFront()) {
                            currentNotecard.setFront(sent);
                        } else {
                            currentNotecard.setBack(sent);
                        }

                        System.out.println(currentNotecard.toString());
                        model.updateNoteCard(currentNotecard, userSingleton.getUser().getUserId());
                        editStage.close();
                    }
                });
                exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent mouseEvent) {
                        editStage.close();

                    }
                });
                VBox editArea = new VBox(EditorFld, buttonArea);
                Scene editScene = new Scene(editArea, 500, 200);
                editStage.setScene(editScene);
                editStage.show();
            }
        });
        edit.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                edit.setStyle("-fx-text-fill:black");
            }
        });
        edit.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                edit.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman';");
            }
        });
        edit.setMinWidth(75);
        edit.setText("Edit");
        edit.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman';");
        Label delete = new Label();
        delete.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                delete.setStyle("-fx-text-fill:black");
            }
        });
        delete.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                delete.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman'; ");
            }
        });
        delete.setText("Delete");
        delete.setMinWidth(75);
        delete.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman'; ");
        Label addURL = new Label();
        addURL.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                addURL.setStyle("-fx-text-fill:black");
            }
        });
        addURL.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                addURL.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman'; ");
            }
        });
        addURL.setText("Add URL");
        addURL.setMinWidth(75);
        addURL.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman'; ");
        Label addImg = new Label();
        addImg.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                addImg.setStyle("-fx-text-fill:black");
            }
        });
        addImg.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                addImg.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman'; ");
            }
        });
        addImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                //dragDrop();
            }
        });
        addImg.setText("Add Img");
        addImg.setMinWidth(75);
        addImg.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman'; ");
        Label fullscreen = new Label();
         fullscreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
            final Stage fullStage = new Stage(StageStyle.UNDECORATED);
            fullStage.initModality(Modality.APPLICATION_MODAL);
            fullStage.initOwner(primaryStage);  
            fullStage.setFullScreen(true);
            Label fullLabel = new Label();
            fullLabel.setStyle("-fx-background-radius: 5;  -fx-text-fill:black;  -fx-font: 45px 'Segoe Script'; -fx-font-weight: bold;; -fx-padding:10; -fx-border-color: white; -fx-border-width: 4px; -fx-background-image: url('notecardBackFixed.png');");
            fullLabel.setAlignment(Pos.CENTER);
            fullLabel.setWrapText(true);
           
           fullStage.setFullScreenExitHint("Double Click to exit fullscreen");
            fullLabel.textProperty().bindBidirectional(focusStack.label.textProperty());
            Scene editScene = new Scene(fullLabel);
             fullLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    System.out.println("we are in double click");
                    fullStage.close();
                    
                }
                }
              });
             editScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override public void handle(KeyEvent keyEvent) {
                    if(keyEvent.getCode().equals(KeyCode.E)){
                        System.out.println("we are in escape");
                        fullStage.setFullScreen(false);
                        fullLabel.setVisible(false);
                        fullStage.close();
                    }
                }
                });
            setKeyEvents(editScene);
            fullStage.setScene(editScene);
            fullStage.show();
          
        }
      });
        fullscreen.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                fullscreen.setStyle("-fx-text-fill:black");
            }
        });
        fullscreen.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                fullscreen.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman';");
            }
        });
        fullscreen.setMinWidth(75);
        fullscreen.setText("Fullscreen");
        fullscreen.setStyle("-fx-text-fill: white; -fx-font: 16px 'Times New Roman';");
        menu.getChildren().addAll(edit, delete, addURL, addImg, fullscreen);
        menu.setVisible(true);
        menu.setPrefWidth(75);
        menu.setMinWidth(USE_PREF_SIZE);
        HBox iconAndPic = new HBox();
        picIcon = new ImageView(new Image("resources/pic.png"));
        if(focusStack.notecards.get(focusStack.index).isHasPics()){
            picIcon.setVisible(true);
        }
        else{
            picIcon.setVisible(false);
        }

        ImageView imageIcon = new ImageView(new Image("resources/Icon.PNG"));
        picIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                focusStack.projector.projectorPane.setVisible(true);
            }
        });

        imageIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                menu.setVisible(true);
            }
        });
        container.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {

                menu.setVisible(false);
            }
        });
        picIcon.setFitWidth(30);
        picIcon.setFitHeight(40);
        imageIcon.setFitWidth(40);
        imageIcon.setFitHeight(40);
        iconAndPic.getChildren().addAll(imageIcon, picIcon);
        container.getChildren().addAll(iconAndPic, menu);
        menu.setVisible(false);
        return container;
    }

    public void dragDrop(){
      
        Group projectorGrp = new Group();
        
        focusStack.projector.projectorPane = makeProjector();
        focusStack.projector.projectorPane.setStyle("-fx-border-color:black; -fx-background-color:black;");
        focusStack.projector.currentImageView = createImageView(focusStack.projector.projectorPane.widthProperty());
        
        
        // set up drag and drop file abilities
        setupDragNDrop(focusStack.projector.projectorPane);
        
        // create button panel controls (left & right arrows)
        Group buttonGroup = createButtonPanel(focusStack.projector.projectorPane);
        
        // create a progress indicator 
        focusStack.projector.progressIndicator = createProgressIndicator(focusStack.projector.projectorPane);
        
        Arc exitButton = new Arc(12,16, 15, 15, -30, 60);
        exitButton.setRotate(90);
        exitButton.setType(ArcType.ROUND);
        exitButton.getStyleClass().add("left-arrow");
        exitButton.translateXProperty().bind(focusStack.projector.projectorPane.widthProperty().subtract(exitButton.maxWidth(USE_PREF_SIZE)+20));
        exitButton.addEventHandler(MouseEvent.MOUSE_PRESSED, 
            (mouseEvent) -> {
                //System.out.println("busy loading? " + loading.get());
                // if no previous image or currently loading.
                focusStack.projector.projectorPane.setVisible(false);
                
        });
        
        focusStack.projector.projectorPane.getChildren().addAll(focusStack.projector.currentImageView, 
                                  buttonGroup, 
                                  focusStack.projector.progressIndicator, exitButton);
        
        
        StackPane.setAlignment(focusStack.projector.projectorPane, Pos.BOTTOM_CENTER);
        focusStack.sp.getChildren().addAll(focusStack.projector.projectorPane);
        
              
      
  }
  
  private Group createButtonPanel(Pane scene){
        // create button panel
        Group buttonGroup = new Group();
        
        Rectangle buttonArea = new Rectangle(0, 0, 60, 30);
        buttonArea.getStyleClass().add("button-panel");
        buttonGroup.getChildren().add(buttonArea);

        // left arrow button
        Arc leftButton = new Arc(12,16, 15, 15, -30, 60);
        leftButton.setType(ArcType.ROUND);
        leftButton.getStyleClass().add("left-arrow");
        
       
        
        // return to previous image
        leftButton.addEventHandler(MouseEvent.MOUSE_PRESSED, 
            (mouseEvent) -> {
                //System.out.println("busy loading? " + loading.get());
                // if no previous image or currently loading.
                if (focusStack.projector.currentIndex == 0 || focusStack.projector.loading.get()) return;
                int indx = gotoImageIndex(0);
                if (indx > -1) {
                    loadImage(focusStack.projector.imageFiles.get(indx));
                }
        });
        
        // right arrow button
        Arc rightButton = new Arc(12,16, 15, 15, 180-30, 60);
        rightButton.setType(ArcType.ROUND);
        rightButton.getStyleClass().add("right-arrow");
        
        // advance to next image
        rightButton.addEventHandler(MouseEvent.MOUSE_PRESSED, 
            (mouseEvent) -> {
                //System.out.println("busy loading? " + focusStack.projector.loading.get());
                // if no next image or currently loading.
                if (focusStack.projector.currentIndex == focusStack.projector.imageFiles.size()-1 
                        || focusStack.projector.loading.get()) return;
                
                int indx = gotoImageIndex(1);
                if (indx > -1) {
                    loadImage(focusStack.projector.imageFiles.get(indx));
                }
        });
        // add buttons to button group
        buttonGroup.getChildren().addAll(leftButton, rightButton);
        
        
        // move button group when scene is resized
        buttonGroup.translateXProperty()
                .bind(scene.widthProperty()
                           .subtract(buttonArea.getWidth() + 6));
        buttonGroup.translateYProperty()
                .bind(scene.heightProperty()
                           .subtract(buttonArea.getHeight() + 6));
        return buttonGroup;
    }
    
  
   private ProgressIndicator createProgressIndicator(Pane scene) {
        ProgressIndicator progress = new ProgressIndicator(0);
        progress.setVisible(false);
        progress.layoutXProperty()
                .bind(scene.widthProperty()
                           .subtract(progress.widthProperty())
                           .divide(2));
        progress.layoutYProperty()
                .bind(scene.heightProperty()
                           .subtract(progress.heightProperty())
                           .divide(2));
        return progress;
    }
    
  
  private boolean isValidImageFile(String url) {
      System.out.println("in isvalidImage");
        List<String> imgTypes = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".PNG" );
        return imgTypes.stream()
                       .anyMatch(t -> url.endsWith(t));
 }
  
 private void addImage(String url) {
     System.out.println("in addImage");
        if (isValidImageFile(url)) {
            
            focusStack.projector.currentIndex +=1;
            focusStack.projector.imageFiles.add(focusStack.projector.currentIndex, url);
            
            
        }
 }
 
 /*public int getDBId(){
     int Id = 0;
     PreparedStatement pstmt = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmtBack = null;
        try {
          // get connection to an Oracle database
          conn = getMySqlConnection();
          
          
          stmt = conn.createStatement();
          String sql;
          System.out.println("frontdata: " + focusStack.notecards.get(focusStack.index).frontData);
          System.out.println("backdata: " + focusStack.notecards.get(focusStack.index).backData);
          sql = "SELECT id FROM notecard where front=? AND back=?;";
          pstmt = conn.prepareStatement(sql); // create a statement
          pstmt.setString(1, focusStack.notecards.get(focusStack.index).frontData); // set input parameter 1
          pstmt.setString(2, focusStack.notecards.get(focusStack.index).backData);
          //ResultSet rs = stmt.executeQuery(sql);
          ResultSet rs = pstmt.executeQuery();
          //ResultSet rs = stmt.executeQuery(sql);
          
          
          while(rs.next()){
              Id = (rs.getInt("id"));
          } 
          
         
         
              
          }
          
          catch (Exception e) {
          // handle the exception
          e.printStackTrace();
          System.exit(1);
        } finally {
          // release database resources
          try {
            conn.close();
          } catch (Exception ignore) {
          }
        }
     
     
     
     return Id;
 }*/


 private ArrayList<String> getAssocImgs(int id){
     System.out.println("id is "+id);
     ArrayList<String> imgPaths = new ArrayList<String>();
     PreparedStatement pstmt = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmtBack = null;
        try {
          // get connection to an Oracle database
          conn = getMySqlConnection();
          
          
          stmt = conn.createStatement();
          String sql;
          
          //System.out.println("param1: " + param1);
          sql = "SELECT path FROM image where id = ?;";
          pstmt = conn.prepareStatement(sql); // create a statement
          pstmt.setInt(1, id); // set input parameter 1
          
          //ResultSet rs = stmt.executeQuery(sql);
          ResultSet rs = pstmt.executeQuery();
          //ResultSet rs = stmt.executeQuery(sql);
          
          while(rs.next()){
              
              imgPaths.add(rs.getString("path"));
          }
         
              
          }
          
          catch (Exception e) {
          // handle the exception
          e.printStackTrace();
          System.exit(1);
        } finally {
          // release database resources
          try {
            conn.close();
          } catch (Exception ignore) {
          }
        }
     
     
     //System.out.println("SIZE: "+ imgPaths.size());
     
     return imgPaths;
 }
 
 
 public void insertPictoDb(String param2){
     System.out.println("we in this");
     ArrayList<String> front = new ArrayList<String>();
        PreparedStatement pstmt = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmtBack = null;
        try {
          // get connection to an Oracle database
          conn = getMySqlConnection();
          
          
          stmt = conn.createStatement();  //TODO Write query to add images
          String sql;
          String param1 = focusStack.notecards.get(focusStack.index).getId();
          System.out.println(param1+" "+ param2);
          sql = "insert into image (id, path) values (?, ?)";
          pstmt = conn.prepareStatement(sql); // create a statement
       //   pstmt.setInt(1, param1); // set input parameter 1
          pstmt.setString(2, param2);
          int count = pstmt.executeUpdate();
          //ResultSet rs = stmt.executeQuery(sql);
         
              
          }
          
          catch (Exception e) {
          // handle the exception
          e.printStackTrace();
          System.exit(1);
        } finally {
          // release database resources
          try {
            conn.close();
          } catch (Exception ignore) {
          }
        }
     
 }
 
  private void setupLoadWoDrag(){
      
      
      List<String> imgs = focusStack.notecards.get(focusStack.index).getImgPaths();
      System.out.println("imgs.size: " + imgs.size());
      for(int i = 0; i < imgs.size(); ++i){
          addImage(imgs.get(i));
          Image image = new Image(imgs.get(i));
          focusStack.projector.currentImageView.setImage(image);
          focusStack.projector.projectorPane.setVisible(false);
      }
     
      
  }
  
  private void setupDragNDrop(Pane scene) {
        System.out.println("in sudnd");
        // Dragging over surface
        scene.setOnDragOver((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if ( db.hasFiles() 
                    || (db.hasUrl() && isValidImageFile(db.getUrl()))) {
                System.out.println("url setupDragNDrop " + db.getUrl());
                event.acceptTransferModes(TransferMode.LINK);
                System.out.println("1");
            } else {
                System.out.println("2");
                event.consume();
            }
        });
        
        // Dropping over surface
        scene.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard(); 
            System.out.println("3");
            // image from the local file system.
            if (db.hasFiles() && !db.hasUrl()) {
                System.out.println("4");
                db.getFiles()
                  .stream()
                  .forEach( file -> {
                      
                    try {
                        //System.out.println("dropped file: "+ file.toURI().toURL().toString());
                        
                        addImage(file.toURI().toURL().toString());
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                  });
            } else {
                //System.out.println("dropped url: "+ db.getUrl());
                System.out.println("5");
                focusStack.notecards.get(focusStack.index).addImg(db.getUrl());
                
                insertPictoDb(db.getUrl());
                // image from some host
                addImage(db.getUrl());                     
            }
            System.out.println(focusStack.projector.currentIndex);
            if (focusStack.projector.currentIndex > -1) {
                System.out.println("6");
                loadImage(focusStack.projector.imageFiles.get(focusStack.projector.currentIndex));
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }
  
  private Task createWorker(final String url) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                // on the worker thread...
                
                Image image = new Image(url, false);
                
                Platform.runLater(() -> {
                    // on the JavaFX Application Thread....
                    //System.out.println("done loading image " + url);
                    focusStack.projector.currentImageView.setImage(image);
                    focusStack.projector.progressIndicator.setVisible(false);
                    focusStack.projector.loading.set(false); // free lock
                });
                return true;
            }
        };
    }
  
  private void loadImage(String url) {
      System.out.println("in loadImage url: " + url);
      Task loadImage;
        // do not begin task until current 
        // task is finished loading (atomic)
        if (!focusStack.projector.loading.getAndSet(true)) { 
            //System.out.println("loadImage spawned ");
            
            
                loadImage = createWorker(url);
                focusStack.projector.progressIndicator.setVisible(true);
                focusStack.projector.progressIndicator.progressProperty().unbind();
                focusStack.projector.progressIndicator.progressProperty()
                                 .bind(loadImage.progressProperty());
                new Thread(loadImage).start();
            
           
        }
    }
  
  /*private void loadImageWoDrag(String url) {
      System.out.println("in loadImage url: " + url);
        // do not begin task until current 
        // task is finished loading (atomic)
      Image image = new Image(url, false);
      currentImageView.setImage(image);
      progressIndicator.setVisible(false);
      loading.set(false); // free lock
    }*/
  
   private Task createWorkerWoDrag(final String url) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                // on the worker thread...
                
                Image image = new Image(url, false);
                
                
                    // on the JavaFX Application Thread....
                    //System.out.println("done loading image " + url);
                    focusStack.projector.currentImageView.setImage(image);
                    focusStack.projector.progressIndicator.setVisible(false);
                    focusStack.projector.loading.set(false); // free lock
                
                return true;
            }
        };
    }
  
  private int gotoImageIndex(int direction) {
        int size = focusStack.projector.imageFiles.size();
        if (size == 0) {
            focusStack.projector.currentIndex = -1;
        } else if (direction == 1 
                && size > 1 
                && focusStack.projector.currentIndex < size - 1) {
            focusStack.projector.currentIndex += 1;
        } else if (direction == 0
                && size > 1 
                && focusStack.projector.currentIndex > 0) {
            focusStack.projector.currentIndex -= 1;
        }

        return focusStack.projector.currentIndex;
    
    }
  
   private ImageView createImageView(ReadOnlyDoubleProperty widthProperty) {
        // maintain aspect ratio
        ImageView imageView = new ImageView();  
        // set aspect ratio
        imageView.setPreserveRatio(true);
        // resize based on the scene
        imageView.fitWidthProperty().bind(widthProperty);
        return imageView;
    }
   
  public Pane makeProjector(){
      Pane rect = new Pane();
      rect.prefWidthProperty().bindBidirectional(focusStack.label.prefWidthProperty());
      rect.prefHeightProperty().bindBidirectional(focusStack.label.prefHeightProperty());
      rect.setStyle(" -fx-background-insets: 5; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, black, 5, 0, 0, 0);");

      
      return rect;
  }
  public void makeTimeline(){
      HBox hTimeline = new HBox();
      ArrayList<Label> effect = new ArrayList<Label>();
      for(int i = 0; i < focusStack.notecards.size(); ++i){
            final Label label = new Label(focusStack.notecards.get(i).getFront());
            String id = Integer.toString(i);
            label.setId(id);
            final int idx = i;
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                  //System.out.println("we in sp on mouse clicked label");
                  if (mouseEvent.getClickCount() == 2) {
                    focusStack.prevIndex = focusStack.index;
                    focusStack.index = idx;
                    focusStack.setIdx(idx);
                    final Label label = (Label)focusStack.sp.getChildren().get(0);
                    label.setText(focusStack.notecards.get(focusStack.index).getFront());
                  }
                  }
             });
            
            //-fx-background-image: url('flipThickGrey.png');ladder(" + bgColor +", lavender 49%, midnightblue 50%);-fx-opacity: 0.5;
            //String bgColor = "#" + color.deriveColor(color.getHue(), color.getSaturation(), color.getBrightness(), random.nextDouble() * 0.5 + 0.5).toString().substring(2, 10);
            label.setStyle("-fx-background-radius: 5;  -fx-text-fill:black;  -fx-font: 6px 'Segoe Script'; -fx-font-weight: bold;; -fx-padding:10; -fx-border-color: white; -fx-border-width: 4px; -fx-background-image: url('resources/notecardBackFixed.png');");
            label.setWrapText(true);
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            final DropShadow dropShadow = new DropShadow();
            final Glow glow = new Glow();
            label.setEffect(dropShadow);
            // give the quote a random fixed size and position.
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            int prefSizeX = 100;
            int prefSizeY = 50;
            label.setPrefSize(prefSizeX, prefSizeY);
            
            effect.add(label);
            
            
            hTimeline.getChildren().add(label);
      }
      
      focusStack.timeline = hTimeline;
      focusStack.timelineLabels = effect;
     
      messageBoard.post();
      
      
  }
  
  public void timelineRise(){
      
        focusStack.timelineLabels.get(focusStack.prevIndex).setScaleX(1);
        focusStack.timelineLabels.get(focusStack.prevIndex).setTranslateY(0);
        
            
            if(focusStack.index > 9){
                scrollPane.setHvalue(focusStack.index);
                
            }
            
            
            
        focusStack.timelineLabels.get(focusStack.index).setScaleX(2.5);
        focusStack.timelineLabels.get(focusStack.index).setTranslateY(-50);
            //effect.get(focusStack.index).toFront();
     
  }

  // records relative x and y co-ordinates.
  class Delta { double x, y; }

 

  // a selection of colors for the text boxes.
  class Colors {
    final String[][] smallPalette = {
      {"aliceblue", "#f0f8ff"},{"antiquewhite", "#faebd7"},{"aqua", "#00ffff"},{"aquamarine", "#7fffd4"},
      {"azure", "#f0ffff"},{"beige", "#f5f5dc"},{"bisque", "#ffe4c4"},{"black", "#000000"},
      {"blanchedalmond", "#ffebcd"},{"blue", "#0000ff"},{"blueviolet", "#8a2be2"},{"brown", "#a52a2a"},
      {"burlywood", "#deb887"},{"cadetblue", "#5f9ea0"},{"chartreuse", "#7fff00"},{"chocolate", "#d2691e"},
      {"coral", "#ff7f50"},{"cornflowerblue", "#6495ed"},{"cornsilk", "#fff8dc"},{"crimson", "#dc143c"},
      {"cyan", "#00ffff"},{"darkblue", "#00008b"},{"darkcyan", "#008b8b"},{"darkgoldenrod", "#b8860b"},
    };

    private Color next() {
      return Color.valueOf(smallPalette[random.nextInt(smallPalette.length)][0]);
    }
  }
  
  public ArrayList<String> fillFront(String param1, String param2){
        ArrayList<String> front = new ArrayList<String>();
        PreparedStatement pstmt = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmtBack = null;
        try {
          // get connection to an Oracle database
          conn = getMySqlConnection();
          
          
          stmt = conn.createStatement();
          String sql;
          String os = "os";
          sql = "select front from notecard where subcategory1=? AND stackname=?;";
          pstmt = conn.prepareStatement(sql); // create a statement
          pstmt.setString(1, param1); // set input parameter 1
          pstmt.setString(2, param2);
          ResultSet rs = pstmt.executeQuery();
          //ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              front.add(rs.getString("front"));
          }
              
          }
          
          catch (Exception e) {
          // handle the exception
          e.printStackTrace();
          System.exit(1);
        } finally {
          // release database resources
          try {
            conn.close();
          } catch (Exception ignore) {
          }
        }
     return front;     
    }
    
    public ArrayList<String> fillBack(String param1, String param2){
        ArrayList<String> back = new ArrayList<String>();
        PreparedStatement pstmt = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmtBack = null;
        try {
          // get connection to an Oracle database
          conn = getMySqlConnection();
          
          stmt = conn.createStatement();
          String sql;
          sql = "select back from notecard where subcategory1=? AND stackname=?;";
          pstmt = conn.prepareStatement(sql); // create a statement
          pstmt.setString(1, param1); // set input parameter 1
          pstmt.setString(2, param2);
          ResultSet rs = pstmt.executeQuery();
          
          while(rs.next()){
              //.backs.add(rs.getString("back"));
              back.add(rs.getString("back"));
          }
              
          }
          
          catch (Exception e) {
          // handle the exception
          e.printStackTrace();
          System.exit(1);
        } finally {
          // release database resources
          try {
            conn.close();
          } catch (Exception ignore) {
          }
        }
     return back;     
    }
    
    public ArrayList<Integer> fillIDs(String param1, String param2){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        PreparedStatement pstmt = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmtBack = null;
        try {
          // get connection to an Oracle database
          conn = getMySqlConnection();
          
          
          stmt = conn.createStatement();
          String sql;
          String os = "os";
          sql = "select id from notecard where subcategory1=? AND stackname=?;";
          pstmt = conn.prepareStatement(sql); // create a statement
          pstmt.setString(1, param1); // set input parameter 1
          pstmt.setString(2, param2);
          ResultSet rs = pstmt.executeQuery();
          //ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              ids.add(rs.getInt("id"));
          }
              
          }
          
          catch (Exception e) {
          // handle the exception
          e.printStackTrace();
          System.exit(1);
        } finally {
          // release database resources
          try {
            conn.close();
          } catch (Exception ignore) {
          }
        }
     return ids;     
    }
    
    public static Connection getMySqlConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";//com.mysql.jdbc.Driver
        String url = "jdbc:mysql://localhost/capstonedb";
        String username = "root";
        String password = "Play2winya";
        //dbc:mysql://localhost:3306/capstonedb?zeroDateTimeBehavior=convertToNull [root on Default schema]
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
    
       public Task createVoiceWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                // on the worker thread...
                
             Platform.runLater(() -> {
                    helloWorld hw = new helloWorld();
                    HelloWorld HW = new HelloWorld(hw);
                    HW.run();
                });
                return true;
                
                
            }
        };
    }
       
      public String ScheduleService(){
            ScheduledService<String> svc = new ScheduledService<String>() {
           protected Task<String> createTask() {
               return new Task<String>() {
                   protected String call() {
                              helloWorld hw = new helloWorld();
                              HelloWorld HW = new HelloWorld(hw);
                              String resultText = HW.run();
                       return resultText;
                   }
               };
           }
       };
       svc.setPeriod(Duration.seconds(1));
       return null;
    }

     class Stack {
         int index;
         int prevIndex;
         public IntegerProperty Idx = new SimpleIntegerProperty();
         String title;
         ArrayList<NoteCard> notecards;
         ArrayList<String> related = new ArrayList<String>(); // These are query keywords, unitialized stacks. 
         //ArrayList<Integer> distance = new ArrayList<Integer>();
         ArrayList<Label> timelineLabels = new ArrayList<Label>();
         StackPane sp = new StackPane();
         HBox timeline = new HBox();
         Label label = new Label();
         Projector projector = new Projector();
         public Stack(String stackTitle){
             notecards = new ArrayList<NoteCard>();
             title = stackTitle;
             resetIndex();
         }
         
         public int getIdx(){
             return Idx.get();
         }
         
         public void setIdx(int value){
             //System.out.println("value: "+value);
             Idx.set(value);
         }
         
         public IntegerProperty IdxProperty(){
             return Idx;
         }
         
         public void incIndex(){
             prevIndex = index;
             index += 1;
             //System.out.println("incIndex: " + index);
             setIdx(index+1);
         }
         
         public void decIndex(){
             prevIndex = index;
             index -= 1;
             //System.out.println("decIndex: " + index);
             setIdx(index+1);
         }
         
         public void resetIndex(){
             prevIndex = index;
             index = 0;
             //System.out.println("resIndex: " + index);
             setIdx(index+1);
         }
         
         public Stack getStackById(){
             return this;
         }
         
         
         
         
     }
    public class helloWorld{

          public helloWorld(){
              
          }
          public void flipcard(){
              flipCard();
          }
          public void nextcard(){
              nextCard();
          }
          public void prevcard(){
              PrevCard();
          }
         

    
    
}  
     
    
    
}

