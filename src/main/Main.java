package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;
import view.auth.Login;

public class Main extends Application{
	
	private ViewManager vm;
    public void start(Stage primaryStage) {

    	vm = ViewManager.getInstance(primaryStage);
    	if (vm.getView("login") == null) {
            Login loginView = new Login(vm); 
            vm.registerView("login", loginView.getView()); 
        }

        vm.showView("login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
