package view.auth;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.User;
import utils.Dialog;
import utils.Response;
import utils.SceneCreator;
import view.ViewManager;

public class Register extends GridPane implements SceneCreator {

    private TextField userNameField, phoneField, addressField;
    private PasswordField passwordField;
    private RadioButton sellerButton, buyerButton;
    private ToggleGroup roleGroup;
    private HBox roleBox;
    private Button registerButton;
    private Hyperlink loginLink;

    public Register() {
        init();
        design();
        setAction();
    }

    @Override
    public void init() {
        userNameField = new TextField();
        passwordField = new PasswordField();
        phoneField = new TextField();
        addressField = new TextField();

        sellerButton = new RadioButton("Seller");
        buyerButton = new RadioButton("Buyer");

        roleGroup = new ToggleGroup();
        sellerButton.setToggleGroup(roleGroup);
        buyerButton.setToggleGroup(roleGroup);

        this.add(new Label("Username:"), 0, 0);
        this.add(new Label("Password:"), 0, 1);
        this.add(new Label("Phone Number:"), 0, 2);
        this.add(new Label("Address:"), 0, 3);
        this.add(new Label("Roles:"), 0, 4);
        
        roleBox = new HBox(10, sellerButton, buyerButton);
        registerButton = new Button("Register");
        loginLink = new Hyperlink("Login Here");
    }

    @Override
    public void design() {
    	this.add(userNameField, 1, 0);
        this.add(passwordField, 1, 1);
        this.add(phoneField, 1, 2);
        this.add(addressField, 1, 3);
        this.add(roleBox, 1, 4);
        this.add(registerButton, 1, 5);
        this.add(loginLink, 1, 6);
    	
        this.setAlignment(Pos.CENTER);
        this.setHgap(10); 
        this.setVgap(10); 
        this.setPadding(new Insets(25, 25, 25, 25)); 

        userNameField.setStyle("-fx-padding: 10;");
        passwordField.setStyle("-fx-padding: 10;");
        phoneField.setStyle("-fx-padding: 10;");
        addressField.setStyle("-fx-padding: 10;");
    }

    public void setAction() {
        Button registerButton = (Button) this.lookup(".button");
        registerButton.setOnAction(e -> handleRegister());

        Hyperlink loginLink = (Hyperlink) this.lookup(".hyperlink");
        loginLink.setOnAction(e -> handleLoginLink());
    }

    private void handleLoginLink() {
    	ViewManager.getInstance().switchTo(new Login());
    }

    private void handleRegister() {
        String username = userNameField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String role = sellerButton.isSelected() ? "Seller" : buyerButton.isSelected() ? "Buyer" : "None";

        Response<User> registerResponse = UserController.getInstance().register(username, password, phone, address, role);

        if (registerResponse.success) {
            Dialog successDialog = new Dialog();
            successDialog.showSuccessDialog(registerResponse.message);
            new Login();
        } else {
            Dialog errorDialog = new Dialog();
            errorDialog.showErrorDialog(registerResponse.message);
        }
    }

}
