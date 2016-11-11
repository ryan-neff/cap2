package sample.models.Views;

/**
 * Created by JOSH on 11/8/2016.
 */
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.models.notecardModels.NoteCardModel;
import sample.models.notecardModels.noteCards.StackModel;

public class LandingPageController extends Switch implements Initializable {


    @FXML
    public HBox stacks;

    @FXML
    public HBox quizzes;

    @FXML
    public Label categories;

    @FXML
    public ListView categoryChoices;

    QuoteMaker maker;
    ObservableList<String> categoryNames = FXCollections.observableArrayList();
    NoteCardModel noteCardModel;
    Map<String, StackModel> stackModels = new HashMap<>();

    public LandingPageController() {
    }

    public void initialize(URL url, ResourceBundle rb) {
        noteCardModel= new NoteCardModel();
        stackModels = getStacks();
        this.getCategories();
        this.makeStacks();
        maker = new QuoteMaker();
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

    public void makeStacks() {

        stackModels.forEach((stackName, stack) -> {
            String subcategory = stack.getCourse();
            String name = stack.getName();
            String labelTitle = subcategory + " " + name;
            StackPane sp = new StackPane();
            Label label = this.getLabel();
            label.setText(labelTitle);
            sp.getChildren().add(label);
            VBox menu = this.initDropDown(sp);
            StackPane.setAlignment(menu, Pos.TOP_LEFT);
            sp.getChildren().add(menu);
            this.stacks.getChildren().add(sp);
        });

    }

    public VBox initDropDown(StackPane focusStack) {
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
        delete.setText("Delete");
        delete.setMinWidth(75.0D);
        delete.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");

        final Label study = new Label();
        //primaryStage = ((Stage)this.getRoot().getScene().getWindow());
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

        menu.getChildren().addAll(new Node[]{edit, delete, study});
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
}

