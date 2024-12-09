package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.auth.LoginView;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		LoginView loginView = new LoginView();
		loginView.start(primaryStage);
		
	}
}
