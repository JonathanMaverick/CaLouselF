package view.auth;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.User;
import utils.Dialog;
import utils.Response;
import utils.Viewable;
import view.HomeView;
import view.ViewManager;

public class LoginView implements Viewable{

	private final GridPane grid;

	public LoginView(ViewManager vm) {
        grid = new GridPane();
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
            Response<User> loginResponse = UserController.getInstance().login(username, password);
            if(loginResponse.success) {
            	 Dialog successDialog = new Dialog();
            	 successDialog.showSuccessDialog(loginResponse.message);
            	 if (vm.getView("home") == null) {
 		            HomeView homeView = new HomeView(vm);
 		            vm.registerView("home", homeView.getView());
 		            vm.showView("home");
 		        }
            }
            else {
            	Dialog errorDialog = new Dialog();
                errorDialog.showErrorDialog(loginResponse.message);
            }
        });
        
        registerLink.setOnAction(e -> {
            vm.showView("register");
        });        
	}
	
	@Override
	public GridPane getView() {
		return grid;
	}

}
