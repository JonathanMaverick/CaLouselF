package view;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Item;
import utils.Dialog;
import utils.Response;

public class DeleteItemView {
    private Item item; // The item to be deleted

    public DeleteItemView(Item item) {
        this.item = item; // Pass the item to be deleted to this view
    }

    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        // Display item details to confirm deletion
        grid.add(new Label("Are you sure you want to delete this item?"), 0, 0, 2, 1);
        grid.add(new Label("Item Name:"), 0, 1);
        grid.add(new Label(item.getName()), 1, 1);

        grid.add(new Label("Category:"), 0, 2);
        grid.add(new Label(item.getCategory()), 1, 2);

        grid.add(new Label("Size:"), 0, 3);
        grid.add(new Label(String.valueOf(item.getSize())), 1, 3);

        grid.add(new Label("Price:"), 0, 4);
        grid.add(new Label(String.valueOf(item.getPrice())), 1, 4);

        Button confirmButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");

        grid.add(confirmButton, 0, 5);
        grid.add(cancelButton, 1, 5);

        confirmButton.setOnAction(e -> {
            // Use the ItemController to delete the item
            Response<Item> response = new ItemController().deleteItem(item.getId());

            Dialog dialog = new Dialog();
            if (response.success) {
                dialog.showSuccessDialog("Item deleted successfully!");
                // Redirect to another view or refresh the current one
                primaryStage.close(); // Close the current view
            } else {
                dialog.showErrorDialog(response.message);
            }
        });

        cancelButton.setOnAction(e -> {
            primaryStage.close(); // Close the current view without making changes
        });

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Delete Item");
        primaryStage.show();
    }
}
