package view;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.Dialog;
import utils.Response;

public class LoginView extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userNameLabel = new Label("Username:");
        grid.add(userNameLabel, 0, 0);

        TextField userNameField = new TextField();
        grid.add(userNameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);
        
        Hyperlink registerLink = new Hyperlink("Register Here");
        grid.add(registerLink, 1, 3);

        loginButton.setOnAction(e -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            Response loginResponse = UserController.getInstance().login(username, password);
            if(loginResponse.success) {
            	 Dialog successDialog = new Dialog();
            	 successDialog.showSuccessDialog(loginResponse.message);
            	 new AppView(primaryStage);
            }
            else {
            	Dialog errorDialog = new Dialog();
                errorDialog.showErrorDialog(loginResponse.message);
            }
        });
        
        registerLink.setOnAction(e -> {
            RegisterView registerView = new RegisterView();
            registerView.start(primaryStage);
        });

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setTitle("Login View");
        primaryStage.setScene(scene);
        primaryStage.show();
        
       
	}

}
