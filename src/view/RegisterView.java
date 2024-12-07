package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.User;
import utils.Dialog;
import utils.Response;


class RegisterView {
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add UI elements
        Label userNameLabel = new Label("Username:");
        grid.add(userNameLabel, 0, 0);

        TextField userNameField = new TextField();
        grid.add(userNameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        Label phoneLabel = new Label("Phone Number:");
        grid.add(phoneLabel, 0, 2);

        TextField phoneField = new TextField();
        grid.add(phoneField, 1, 2);

        Label addressLabel = new Label("Address:");
        grid.add(addressLabel, 0, 3);

        TextField addressField = new TextField();
        grid.add(addressField, 1, 3);

        Label roleLabel = new Label("Roles:");
        grid.add(roleLabel, 0, 4);

        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton sellerButton = new RadioButton("Seller");
        sellerButton.setToggleGroup(roleGroup);
        RadioButton buyerButton = new RadioButton("Buyer");
        buyerButton.setToggleGroup(roleGroup);

        HBox roleBox = new HBox(10, sellerButton, buyerButton);
        grid.add(roleBox, 1, 4);

        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 5);

        registerButton.setOnAction(e -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String role = sellerButton.isSelected() ? "Seller" : buyerButton.isSelected() ? "Buyer" : "None";
            Response<User> registerResponse = UserController.getInstance().register(username, password, phone, address, role);
            if(registerResponse.success) {
            	Dialog successDialog = new Dialog();
            	successDialog.showSuccessDialog(registerResponse.message);
            	LoginView loginView = new LoginView();
            	try {
					loginView.start(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
            else {
            	Dialog errorDialog = new Dialog();
                errorDialog.showErrorDialog(registerResponse.message);
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("Register View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}