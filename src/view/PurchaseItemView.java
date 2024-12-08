package view;

import controller.ItemController;
//import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Item;
import utils.Dialog;
import utils.Response;

public class PurchaseItemView {
    private Item item; // The item to be purchased

    public PurchaseItemView(Item item) {
        this.item = item; // Pass the item to be purchased to this view
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

        grid.add(new Label("Price:"), 0, 3);
        grid.add(new Label(String.valueOf(item.getPrice())), 1, 3);

        grid.add(new Label("Confirm Buyer ID:"), 0, 4);
        TextField buyerIdField = new TextField(); // Simulate buyer entering their ID
        grid.add(buyerIdField, 1, 4);

        Button confirmButton = new Button("Confirm Purchase");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 5);

        confirmButton.setOnAction(e -> {
            String buyerId = buyerIdField.getText();

            if (buyerId.isEmpty()) {
                Dialog dialog = new Dialog();
                dialog.showErrorDialog("Buyer ID cannot be empty!");
                return;
            }

            // Call the controller to create a transaction
//            Response response = new TransactionController().createTransaction(item.getItemId(), buyerId);

            Dialog dialog = new Dialog();
//            if (response.success) {
//                dialog.showSuccessDialog("Purchase successful! Transaction ID: " + response.data);
//                primaryStage.close(); // Close the purchase view
//            } else {
//                dialog.showErrorDialog(response.message);
//            }
        });

        cancelButton.setOnAction(e -> primaryStage.close()); // Close the view if canceled

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Purchase Item");
        primaryStage.show();
    }
}
