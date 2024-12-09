package main;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.ViewManager;
import view.auth.LoginView;
import view.auth.RegisterView;

public class Main extends Application{
	
	private ViewManager vm;
    public void start(Stage primaryStage) {

    	vm = ViewManager.getInstance(primaryStage);
        Pane loginView = new LoginView(vm).getView();
        Pane registerView = new RegisterView(vm).getView();

        vm.registerView("login", loginView);
        vm.registerView("register", registerView);

        vm.showView("login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
