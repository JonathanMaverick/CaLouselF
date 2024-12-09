package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {

    private static ViewManager instance;  
    private Stage stage;
    private Map<String, Pane> views; 

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

    public void registerView(String name, Pane view) {
        views.put(name, view);
    }
    
    public Pane getView(String name) {
        return views.get(name);
    }

    public void showView(String name) {
        Pane view = getView(name);
        if (view != null) {
            Scene scene = new Scene(view, 800, 800);
            stage.setScene(scene);
            stage.show();
        }
    }
}
