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
import utils.SceneCreator;
import view.Home;
import view.ViewManager;

public class Login extends GridPane implements SceneCreator {

	private TextField userNameField;
	private PasswordField passwordField;
	private Button loginButton;
	private Hyperlink registerLink;
	private Label usernameLabel, passwordLabel;

	public Login() {
		init();
		design();
		setAction();
	}

	@Override
	public void init() {
		userNameField = new TextField();
		passwordField = new PasswordField();
		usernameLabel = new Label("Username:");
		passwordLabel = new Label("Password:");

		loginButton = new Button("Login");
		this.add(loginButton, 1, 2);

		registerLink = new Hyperlink("Register Here");
		this.add(registerLink, 1, 3);
	}

	@Override
	public void design() {
		this.add(usernameLabel, 0, 0);
		this.add(userNameField, 1, 0);
		this.add(passwordLabel, 0, 1);
		this.add(passwordField, 1, 1);

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		userNameField.setStyle("-fx-padding: 10;");
		passwordField.setStyle("-fx-padding: 10;");
		loginButton.setStyle("-fx-padding: 10 20;");
		registerLink.setStyle("-fx-text-fill: #0066cc;");
	}

	public void setAction() {
		loginButton.setOnAction(e -> handleLogin());
		registerLink.setOnAction(e -> handleRegisterLink());
	}

	private void handleLogin() {
		String username = userNameField.getText();
		String password = passwordField.getText();
		Response<User> loginResponse = UserController.getInstance().login(username, password);

		if (loginResponse.success) {
			showSuccessDialog(loginResponse.message);
			ViewManager.getInstance().switchTo(new Home());
		} else {
			showErrorDialog(loginResponse.message);
		}
	}

	private void handleRegisterLink() {
		ViewManager.getInstance().switchTo(new Register());
	}

	private void showSuccessDialog(String message) {
		Dialog successDialog = new Dialog();
		successDialog.showSuccessDialog(message);
	}

	private void showErrorDialog(String message) {
		Dialog errorDialog = new Dialog();
		errorDialog.showErrorDialog(message);
	}
}
