package sample.models.Views;

/**
 * Created by JOSH on 11/8/2016.
 */
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.notecardModels.NoteCardModel;
import sample.models.notecardModels.UserModel;
import sample.models.notecardModels.noteCards.NoteCard;
import sample.models.notecardModels.noteCards.StackModel;
import sample.models.notecardModels.utils.UserSingleton;

public class LandingPageController extends Switch implements Initializable {


    public Stage primaryStage;

    @FXML
    public HBox stacks;

    @FXML
    public HBox quizzes;

    @FXML
    public Label categories;

    @FXML
    public ListView categoryChoices;

    @FXML
    public ImageView plusBtn;
    @FXML
    public Label plusLabel;

    SessionController maker;
    ObservableList<String> categoryNames = FXCollections.observableArrayList();
    NoteCardModel noteCardModel;
    Map<String, StackModel> stackModels = new HashMap<>();
    UserSingleton userSingleton;

    public LandingPageController() {
    }

    public void initialize(URL url, ResourceBundle rb) {
        UserModel userModel = new UserModel(); //TODO delete once everything works
        noteCardModel= new NoteCardModel();
        stackModels = getStacks();
        this.getCategories();
        this.makeStacks();
        maker = new SessionController();
        userSingleton = UserSingleton.getInstance();
        userSingleton.setUser(userModel.getLoginInfo("test", "12345"));
       /* plusLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("plusBtn");
                newStack();
                event.consume();
            }
        });*/
    }

    @FXML
    private void handleCategories(MouseEvent e) {
        this.categoryChoices.setVisible(true);
        this.categories.setStyle("-fx-border-color:blue;");
    }

    @FXML
    private void handleCategoriesOut(MouseEvent e) {
        this.categoryChoices.setVisible(false);
        this.categories.setStyle("-fx-border-color:white;");
    }

    @FXML
    private void newStack(){
        System.out.print("plus button  ");
        StackModel nStack = new StackModel();
        final Stage catStage = new Stage(StageStyle.UNDECORATED);
        catStage.initModality(Modality.APPLICATION_MODAL);
        catStage.initOwner(primaryStage);
        HBox buttonArea = new HBox();
        HBox labelArea = new HBox();

        Label boxLabel = new Label();
        boxLabel.setText("Create New Stack");
        boxLabel.setStyle(
                "-fx-background-radius: 15em; " +
                        "-fx-min-width: 60px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 300px; " +
                        "-fx-max-height: 30px;"
        );

        Button submit = new Button();
        submit.setText("Submit");
        submit.setStyle(
                "-fx-background-radius: 15em; " +
                        "-fx-min-width: 60px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 120px; " +
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
        labelArea.getChildren().add(boxLabel);
        buttonArea.getChildren().add(submit);
        buttonArea.getChildren().add(exit);
        //TextArea EditorFld = new TextArea();
        TextField course = new TextField();
        course.setPromptText("course");
        TextField subject = new TextField();
        subject.setPromptText("subject");
        TextField stackName = new TextField();
        stackName.setPromptText("stack name");
        String currentText;

        //EditorFld.setText(currentText);
        //EditorFld.setPrefRowCount(10);
        //EditorFld.setPrefColumnCount(100);
        //EditorFld.setWrapText(true);
        //EditorFld.setPrefWidth(400);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                //String sent = EditorFld.getText();
                String courseSent = course.getText();
                String subSent = subject.getText();
                String nameSent = stackName.getText();
                //NoteCard currentNotecard = focusStack.notecards.get(focusStack.index);
                //if (currentNotecard.getIsFront()) {
                //    currentNotecard.setFront(sent);
                //} else {
                //    currentNotecard.setBack(sent);
                //}
                nStack.setName(nameSent);
                nStack.setSubject(subSent);
                nStack.setCourse(courseSent);

                //set first notecard in new stack
                NoteCard newCard = new NoteCard();
                //newCard.setStackIndex(1);
                newCard.setFront("");
                newCard.setBack("");
                //newCard.setId("");
                //newCard.setStackId("");


                //NoteCardModel model = new NoteCardModel();
                //System.out.println(currentNotecard.toString());
                noteCardModel.updateNoteCard(newCard, "test");

                catStage.close();
            }
        });
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                catStage.close();

            }
        });
        VBox newArea = new VBox(labelArea, course, subject, stackName, buttonArea);
        Scene newStkScene = new Scene(newArea, 200, 155);
        catStage.setScene(newStkScene);
        catStage.show();
    }

    public void makeStacks() {

        stackModels.forEach((stackName, stack) -> {
            String subcategory = stack.getCourse();
            String name = stack.getName();
            String labelTitle = subcategory + " " + name;
            StackPane sp = new StackPane();
            Label label = this.getLabel();
            label.setText(labelTitle);
            sp.getChildren().add(label);
            VBox menu = this.initDropDown(sp, stack);
            StackPane.setAlignment(menu, Pos.TOP_LEFT);
            sp.getChildren().add(menu);
            this.stacks.getChildren().add(sp);
        });

    }

    public VBox initDropDown(StackPane focusStack, StackModel stackModel) {
        VBox container = new VBox();
        container.setMaxHeight(focusStack.getHeight() / 2.0D);
        container.setId("menuContainer");
        final VBox menu = new VBox();
        menu.setId("menu");
        menu.setStyle(" -fx-background-color: #6666ff; -fx-text-fill: white; -fx-font: 12px \'Segoe Script\'; -fx-padding:3;");
        container.setPrefWidth(75.0D);
        container.setMinWidth(-1.0D / 0.0);
        container.setMaxWidth(-1.0D / 0.0);
        final Label edit = new Label();
       edit.setOnMouseClicked(new EventHandler() {
           @Override
           public void handle(final Event event) {
               userSingleton.setStack(stackModel);
               switchViews("editpage");
               }


        });
        edit.setOnMouseEntered(new EventHandler() {

            @Override
            public void handle(final Event event) {
                edit.setStyle("-fx-text-fill:black");
            }
        });
        edit.setOnMouseExited(new EventHandler() {

            @Override
            public void handle(final Event event) {
                edit.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\';");
            }
        });
        edit.setMinWidth(75.0D);
        edit.setText("Edit");
        edit.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\';");
        final Label delete = new Label();

        delete.setOnMouseEntered(new EventHandler() {

            @Override
            public void handle(final Event event) {
                delete.setStyle("-fx-text-fill:black");
            }
        });
        delete.setOnMouseExited(new EventHandler() {

            @Override
            public void handle(final Event event) {
                delete.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");
            }
        });

        delete.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(final Event event) {

            }
        });

        delete.setText("Delete");
        delete.setMinWidth(75.0D);
        delete.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");

        final Label study = new Label();
        study.setOnMouseEntered(new EventHandler() {

            @Override
            public void handle(final Event event) {
                study.setStyle("-fx-text-fill:black");
            }
        });

        study.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(final Event event) {
                try {
                    this.toString();
                    maker.startQuote(getPrimaryStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        study.setOnMouseExited(new EventHandler() {

            @Override
            public void handle(final Event event) {
                study.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");
            }
        });
        study.setText("Study");
        study.setMinWidth(75.0D);
        study.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");

        final Label quiz = new Label();
        study.setOnMouseEntered(new EventHandler() {

            @Override
            public void handle(final Event event) {
                quiz.setStyle("-fx-text-fill:black");
            }
        });

        study.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(final Event event) {
                userSingleton.setStack(stackModel);
                switchViews("quizpage");
            }
        });
        study.setOnMouseExited(new EventHandler() {

            @Override
            public void handle(final Event event) {
                quiz.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");
            }
        });
        quiz.setText("Quiz");
        quiz.setMinWidth(75.0D);
        quiz.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");

        menu.getChildren().addAll(new Node[]{edit, delete, study, quiz});
        menu.setVisible(true);
        menu.setPrefWidth(75.0D);
        menu.setMinWidth(-1.0D / 0.0);
        new HBox();
        ImageView imageIcon = new ImageView(new Image("resources/Icon.PNG"));
        imageIcon.setFitWidth(40.0D);
        imageIcon.setFitHeight(40.0D);

        imageIcon.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(final Event event) {
                menu.setVisible(true);
            }
        });
        container.setOnMouseExited(new EventHandler() {

            @Override
            public void handle(final Event event) {
                menu.setVisible(false);
            }
        });
      container.getChildren().addAll(new Node[]{imageIcon, menu});
        menu.setVisible(false);
        return container;
    }

    public void getCategories() {

        stackModels.forEach((stackName, stack) -> {
            if(!this.categoryNames.contains(stack.getCourse())) {
                this.categoryNames.add(stack.getCourse());
            }
        });

        this.categoryChoices.setItems(this.categoryNames);
    }

    public Label getLabel() {
        Label label = new Label();
        label.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(final Event event) {

            }
        });
        label.getStyleClass().add("cardLabel");
        label.setWrapText(true);
        label.setPadding(new Insets(0.0D, 10.0D, 0.0D, 10.0D));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        DropShadow dropShadow = new DropShadow();
        new Glow();
        label.setEffect(dropShadow);
        label.setMinSize(-1.0D / 0.0, -1.0D / 0.0);
        short prefSizeX = 190;
        short prefSizeY = 280;
        label.setPrefSize((double)prefSizeX, (double)prefSizeY);
        return label;
    }


    private Map<String, StackModel> getStacks() {
        System.out.println("getStacks");
        this.stackModels = noteCardModel.getAllStacks("test"); //TODO Change when user can create stacks
        return stackModels;
    }

    private Stage getPrimaryStage () {
        Stage stage = ((Stage)this.getRoot().getScene().getWindow());
        return stage;
    }

    private void switchViews(final String view) {
        this.getSceneManager().switchTo(view);

    }

}

