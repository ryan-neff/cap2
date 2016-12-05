package studyTool.models.controllersAndViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.HashMap;

/**
 * Created by JOSH
 */
public class SceneManager {
    private Scene scene;
    private final HashMap<String, Switch> controllers = new HashMap();

    public SceneManager() {
    }

    public SceneManager(Scene scene) {
        this.scene = scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
    }

    public Switch add(String name) {
        Switch controller = null;
        if(controller == null) {
            try {
                System.out.println(name + ".fxml");
                FXMLLoader ex = new FXMLLoader(this.getClass().getResource(name + ".fxml"));
                Parent root = (Parent)ex.load();
                
                controller = (Switch)ex.getController();
                controller.setRoot(root);
                controller.setSceneManager(this);
                this.controllers.put(name, controller);
            } catch (Exception var5) {
                controller = null;
                System.out.println(var5);
            }
        }
        return controller;
    }

    public void switchTo(String name) {
        Switch controller = null;
        if(controller == null) {
            controller = this.add(name);
            System.out.println(name);
            System.out.println(this.controllers.get(name));
        }

        if(controller != null && this.scene != null) {
            this.scene.setRoot(controller.getRoot());
        }

    }
}

