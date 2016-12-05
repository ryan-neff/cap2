/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studyTool.models.controllersAndViews;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import studyTool.models.notecardModels.noteCards.StackModel;
import studyTool.models.notecardModels.utils.UserSingleton;

/**
 * FXML Controller class
 *
 * @author derek
 */
public class StatsController extends Switch implements Initializable {
    public int counter = 0;
    private StackModel stack;
    private UserSingleton userSingleton;
    @FXML
    ImageView icon;
    @FXML
    HBox title;
    @FXML
    BorderPane bp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userSingleton = UserSingleton.getInstance();
        stack = userSingleton.getStack();
        
        final NumberAxis xAxis = new NumberAxis(-1, stack.getNoteCards().size(), 1);
        Text titleTxt = new Text(stack.getName());
        title.getChildren().add(titleTxt);
        title.setStyle("-fx-text-fill: #6666ff; -fx-font: 36px 'Times New Roman';");
        final NumberAxis yAxis = new NumberAxis(0, 100, 1);        
        final ScatterChart<Number,Number> sc = new
            ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Notecard");                
        yAxis.setLabel("Percent Right");
        sc.setTitle("Notecard Scores");
        XYChart.Series series1 = new XYChart.Series();
       
        
        stack.getNoteCards().forEach(i ->{
            System.out.println(stack.getNoteCards().get(counter).getAttemptsCorrect()/stack.getNoteCards().get(counter).getAttempts());
            series1.getData().add(new XYChart.Data(counter, (((double)i.getAttemptsCorrect()/(double)i.getAttempts())*100)));
            ++counter;
         });
        sc.getData().addAll(series1);
        bp.setCenter(sc);
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override public void handle(MouseEvent mouseEvent) {
                    switchViews("landingPage");
               }
         });
    }    
    private void switchViews(final String view) {
        this.getSceneManager().switchTo(view);

    }
}
