package sample.models.Views;

import javafx.scene.Parent;
import sample.models.Views.SceneManager;

/**
 * Created by JOSH
 */
public abstract class Switch {
    private Parent root;
    private SceneManager sceneManager;

    public Switch() {
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Parent getRoot() {
        return this.root;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public SceneManager getSceneManager() {
        return this.sceneManager;
    }
}

