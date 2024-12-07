package view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppView {

    public AppView(Stage primaryStage) {
        StackPane root = new StackPane();

        Text text = new Text("Halo");
        root.getChildren().add(text);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("App View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
