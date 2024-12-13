package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewManager {

    private static ViewManager instance;
    private Stage stage;

    private ViewManager() {}

    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchTo(Pane pane) {
        if (stage != null) {
            stage.setScene(new Scene(pane, 800, 600));
        } else {
            throw new IllegalStateException("Stage not initialized. Call setStage() first.");
        }
    }
}
