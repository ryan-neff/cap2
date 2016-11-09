package sample.models.Views;

/**
 * Created by JOSH on 11/8/2016.
 */
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class landingPageController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    public HBox stacks;
    @FXML
    public HBox quizzes;
    @FXML
    public Label categories;
    @FXML
    public ListView categoryChoices;
    private Stage stage;
    ObservableList<String> categoryNames = FXCollections.observableArrayList();

    public landingPageController() {
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.getCategories();
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

    public void ready(Stage stage) {
        this.stage = stage;
        this.makeStacks();
    }

    public void makeStacks() {
        Connection conn = null;
        Statement stmt = null;
        Object stmtBack = null;

        try {
            conn = getMySqlConnection();
            stmt = conn.createStatement();
            String e = "select distinct subcategory1, stackname from notecard;";
            ResultSet rs = stmt.executeQuery(e);

            while(rs.next()) {
                String subcategory = rs.getString("subcategory1");
                String stackname = rs.getString("stackname");
                String labelTitle = subcategory + " " + stackname;
                StackPane sp = new StackPane();
                Label label = this.getLabel();
                label.setText(labelTitle);
                sp.getChildren().add(label);
                VBox menu = this.initDropDown(sp);
                StackPane.setAlignment(menu, Pos.TOP_LEFT);
                sp.getChildren().add(menu);
                this.stacks.getChildren().add(sp);
            }
        } catch (Exception var20) {
            var20.printStackTrace();
            System.exit(1);
        } finally {
            try {
                conn.close();
            } catch (Exception var19) {
                ;
            }

        }

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
/*        edit.setOnMouseClicked(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
            }
        });
        edit.setOnMouseEntered(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
                edit.setStyle("-fx-text-fill:black");
            }
        });
        edit.setOnMouseExited(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
                edit.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\';");
            }
        });  */
        edit.setMinWidth(75.0D);
        edit.setText("Edit");
        edit.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\';");
        final Label delete = new Label();
/*        delete.setOnMouseEntered(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
                delete.setStyle("-fx-text-fill:black");
            }
        });
        delete.setOnMouseExited(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
                delete.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");
            }
        });
*/      delete.setText("Delete");
        delete.setMinWidth(75.0D);
        delete.setStyle("-fx-text-fill: white; -fx-font: 16px \'Times New Roman\'; ");
        menu.getChildren().addAll(new Node[]{edit, delete});
        menu.setVisible(true);
        menu.setPrefWidth(75.0D);
        menu.setMinWidth(-1.0D / 0.0);
        new HBox();
        ImageView imageIcon = new ImageView(new Image("Icon.PNG"));
        imageIcon.setFitWidth(40.0D);
        imageIcon.setFitHeight(40.0D);
/*        imageIcon.setOnMouseClicked(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
                menu.setVisible(true);
            }
        });
        container.setOnMouseExited(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
                menu.setVisible(false);
            }
        });
*/        container.getChildren().addAll(new Node[]{imageIcon, menu});
        menu.setVisible(false);
        return container;
    }

    public void getCategories() {
        Connection conn = null;
        Statement stmt = null;
        Object stmtBack = null;

        try {
            conn = getMySqlConnection();
            stmt = conn.createStatement();
            String e = "select distinct category from notecard;";
            ResultSet rs = stmt.executeQuery(e);

            while(rs.next()) {
                String category = rs.getString("category");
                this.categoryNames.add(category);
            }
        } catch (Exception var15) {
            var15.printStackTrace();
            System.exit(1);
        } finally {
            try {
                conn.close();
            } catch (Exception var14) {
                ;
            }

        }

        this.categoryChoices.setItems(this.categoryNames);
    }

    public Label getLabel() {
        Label label = new Label();
/*        label.setOnMouseClicked(new EventHandler() {
            public void handle(MouseEvent mouseEvent) {
            }
        }); */
        label.setStyle("-fx-background-radius: 5;  -fx-text-fill:black;  -fx-font: 18px \'Segoe Script\'; -fx-font-weight: bold;; -fx-padding:10; -fx-border-color: white; -fx-border-width: 4px; -fx-background-image: url(\'notecardBackFixed.png\');");
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

    public static Connection getMySqlConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/capstonedb";
        String username = "testuser";
        String password = "12345";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
}

