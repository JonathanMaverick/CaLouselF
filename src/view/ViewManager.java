package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {

    private static ViewManager instance;  
    private Stage stage;
    private Map<String, Scene> views; 

    private ViewManager(Stage stage) {
        this.stage = stage;
        this.views = new HashMap<>();
    }

    public static ViewManager getInstance(Stage stage) {
        if (instance == null) {
            instance = new ViewManager(stage); 
        }
        return instance;
    }

    public void registerView(String name, Scene scene) {
        views.put(name, scene);
    }
    
    public Scene getView(String name) {
        return views.get(name);
    }

    public void showView(String name) {
    	Scene scene = getView(name); // Get Scene
        if (scene != null) {
            stage.setScene(scene);
            stage.show();
        }
    }
}
