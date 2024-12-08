package view;

import controller.OfferController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Item;
import utils.Dialog;
import utils.Response;

public class MakeOfferView {
    private Item item; // The item on which the offer is being made

    public MakeOfferView(Item item) {
        this.item = item; // Pass the item to this view
    }

    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        // Display item details
        grid.add(new Label("Item Name:"), 0, 0);
        grid.add(new Label(item.getName()), 1, 0);

        grid.add(new Label("Category:"), 0, 1);
        grid.add(new Label(item.getCategory()), 1, 1);

        grid.add(new Label("Size:"), 0, 2);
        grid.add(new Label(String.valueOf(item.getSize())), 1, 2);

        grid.add(new Label("Current Price:"), 0, 3);
        grid.add(new Label(String.valueOf(item.getPrice())), 1, 3);

        grid.add(new Label("Enter Offer Price:"), 0, 4);
        TextField offerPriceField = new TextField();
        grid.add(offerPriceField, 1, 4);

        grid.add(new Label("Enter Your User ID:"), 0, 5);
        TextField userIdField = new TextField();
        grid.add(userIdField, 1, 5);

        Button submitButton = new Button("Submit Offer");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 6);

        submitButton.setOnAction(e -> {
            try {
                int offerPrice = Integer.parseInt(offerPriceField.getText());
                String userId = userIdField.getText();

                if (offerPrice <= 0) {
                    showAlert("Invalid Offer", "Offer price must be greater than zero.");
                    return;
                }

                if (userId.isEmpty()) {
                    showAlert("Invalid User ID", "User ID cannot be empty.");
                    return;
                }

                // Call the controller to make the offer
                Response response = new OfferController().createOffer(item.getId(), userId, offerPrice);

                Dialog dialog = new Dialog();
                if (response.success) {
                    dialog.showSuccessDialog("Offer submitted successfully!");
                    primaryStage.close(); // Close the make offer view
                } else {
                    dialog.showErrorDialog(response.message);
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric offer price.");
            }
        });

        cancelButton.setOnAction(e -> primaryStage.close()); // Close the view if canceled

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Make Offer");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
