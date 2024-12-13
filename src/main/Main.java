package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ViewManager;
import view.auth.Login;

public class Main extends Application{
	
    public void start(Stage primaryStage) {
    	ViewManager.getInstance().setStage(primaryStage);
    	
        Scene loginScene = new Scene(new Login(), 800, 600);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login - CaLouselF");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
