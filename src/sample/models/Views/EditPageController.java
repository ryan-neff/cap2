package sample.models.Views;

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
        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.ScrollPane;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.scene.effect.DropShadow;
        import javafx.scene.effect.Glow;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.StackPane;
        import javafx.scene.layout.VBox;
        import javafx.scene.text.Text;
        import javafx.scene.text.TextAlignment;
        import javafx.stage.Stage;
        import sample.models.notecardModels.NoteCardModel;
        import sample.models.notecardModels.noteCards.StackModel;
        import sample.models.notecardModels.utils.UserSingleton;

/**
 *
 * @author derek
 */
public class EditPageController extends Switch implements Initializable {

    private Stage stage;
    @FXML
    private Label label;
    @FXML
    private ScrollPane scrollpaneNC;
    @FXML
    private BorderPane root;
    @FXML
    private TextArea textareaFront;
    @FXML
    private TextArea textareaBack;
    @FXML
    private Button submitBut;
    @FXML
    private Button deleteBut;
    @FXML
    private Button homeButton;
    @FXML
    private Button newBut;
    @FXML
    private ImageView icon;
    @FXML
    public HBox hTimeline;

    @FXML
    public HBox titleBox;

    public ArrayList<String> front = new ArrayList<String>();
    public ArrayList<String> back = new ArrayList<String>();
    public ArrayList<Integer> ids = new ArrayList<Integer>();
    public int frontCounter = 0;
    public int backCounter = 0;
    public int idCounter = 0;
    public int globId = 0;
    boolean newPressed = false;
    private UserSingleton userSingleton;
    private StackModel stack;
    private NoteCardModel noteCardModel;


    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    public EditPageController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userSingleton = UserSingleton.getInstance();
        noteCardModel = new NoteCardModel();
        stack = userSingleton.getStack();
        initStyle();
        String datitle = stack.getName() + " " + stack.getCourse();
        Text titleText = new Text();
        titleText.setText(datitle);
        titleText.setStyle("-fx-font: 35px 'Segoe Script';");
        titleBox.getChildren().add(titleText);
        initButtons();
        makeStacks();
    }


    public void initStyle() {

        root.setStyle("-fx-background-color:  #0080ff");
        icon = new ImageView(new Image("resources/Icon.PNG"));
    }

    public void initButtons() {

        submitBut.setOnMouseClicked(mouseEvent -> {
            //System.out.println("we in sp on mouse clicked label");

            String front1 = textareaFront.getText();
            String back1 = textareaBack.getText();
            if (newPressed) {
                insertFront(front1);
                insertBack(back1);
                newPressed = false;
                textareaFront.setStyle("-fx-textarea-box-border: transparent;");
                textareaBack.setStyle("-fx-textarea-box-border: transparent;");

            } else {
                // updateFront(front);
                //updateBack(back);
            }

        });


        newBut.setOnMouseClicked(mouseEvent -> {
            //System.out.println("we in sp on mouse clicked label");
              /*String front = textareaFront.getText();
              String back = textareaBack.getText();
              insertFront(front);
              insertBack(back);*/
            textareaFront.clear();
            textareaBack.clear();
            textareaFront.setStyle("-fx-border-color: red; -fx-border-width: 4px;");
            textareaBack.setStyle("-fx-border-color: red; -fx-border-width: 4px;");
            newPressed = true;
        });

        homeButton.setOnMouseClicked(event -> {
            switchViews("landingPage");
        });
}

    public void insertFront(String param2) {
        //Implement insert statement

    }

    public void insertBack(String param2) {
        //Implement insert statment
    }

    public void makeStacks() {
        //makeData();
        for (int i = 0; i < stack.getNoteCards().size(); ++i) {
            Label label = getLabel();
            label.setText(stack.getNoteCards().get(i).getFront());
            String id = stack.getNoteCards().get(i).getId();
            label.setId(id);

            //VBox menu = initDropDown(sp);
            //StackPane.setAlignment(menu, Pos.TOP_LEFT);
            //sp.getChildren().add(menu);
            hTimeline.getChildren().add(label);
        }


    }

    public void setFrontBack(String id) {

        int Id = Integer.parseInt(id);

        textareaFront.setText(stack.getNoteCards().stream().filter(x -> x.getId().equals(id)).findFirst().get().getFront());
        textareaBack.setText(stack.getNoteCards().stream().filter(x -> x.getId().equals(id)).findFirst().get().getBack());
        globId = Id;
    }

    public Label getLabel() {
        Label label = new Label();
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("we in sp on mouse clicked label");
                if (mouseEvent.getClickCount() == 2) {
                    setFrontBack(label.getId());
                }
            }
        });

        //-fx-background-image: url('flipThickGrey.png');ladder(" + bgColor +", lavender 49%, midnightblue 50%);-fx-opacity: 0.5;
        //String bgColor = "#" + color.deriveColor(color.getHue(), color.getSaturation(), color.getBrightness(), random.nextDouble() * 0.5 + 0.5).toString().substring(2, 10);
        label.setStyle("-fx-background-radius: 5;  -fx-text-fill:black;  -fx-font: 14px 'Segoe Script'; -fx-font-weight: bold;; -fx-padding:10; -fx-border-color: white; -fx-border-width: 4px; -fx-background-image: url('notecardBackFixed.png');");
        label.setWrapText(true);
        label.setPadding(new Insets(0, 10, 0, 10));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        final DropShadow dropShadow = new DropShadow();
        final Glow glow = new Glow();
        label.setEffect(dropShadow);
        // give the quote a random fixed size and position.
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        int prefSizeX = 150;
        int prefSizeY = 200;
        label.setPrefSize(prefSizeX, prefSizeY);

        return label;
    }


    public void makeTimeline() {
        hTimeline = new HBox();
        for (int i = 0; i < stack.getNoteCards().size(); ++i) {
            final Label label = new Label();
            label.setText(stack.getNoteCards().get(i).getFront());
            String id = Integer.toString(i);
            label.setId(id);
            final int idx = i;
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    //System.out.println("we in sp on mouse clicked label");
                    if (mouseEvent.getClickCount() == 2) {

                    }
                }
            });

            //-fx-background-image: url('flipThickGrey.png');ladder(" + bgColor +", lavender 49%, midnightblue 50%);-fx-opacity: 0.5;
            //String bgColor = "#" + color.deriveColor(color.getHue(), color.getSaturation(), color.getBrightness(), random.nextDouble() * 0.5 + 0.5).toString().substring(2, 10);
            label.setStyle("-fx-background-radius: 5;  -fx-text-fill:black;  -fx-font: 6px 'Segoe Script'; -fx-font-weight: bold;; -fx-padding:10; -fx-border-color: white; -fx-border-width: 4px; -fx-background-image: url('notecardBackFixed.png');");
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


            hTimeline.getChildren().add(label);
        }

        int i = 0;
        System.out.println("htimeline: " + hTimeline.getChildren().size());


    }

    private void switchViews(final String view) {
        this.getSceneManager().switchTo(view);
    }
    //private void makeData() {
      //  userSingleton = UserSingleton.getInstance();
       // stack = userSingleton.getStack();
   // }
}
