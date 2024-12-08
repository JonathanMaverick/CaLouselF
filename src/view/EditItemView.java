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

public class EditItemView {
    private Item item; // The item to be edited

    public EditItemView(Item item) {
        this.item = item; // Pass the item to be edited to this view
    }

    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        // Display the current details of the item for editing
        grid.add(new Label("Item Name:"), 0, 0);
        TextField nameField = new TextField(item.getName());
        grid.add(nameField, 1, 0);

        grid.add(new Label("Category:"), 0, 1);
        TextField categoryField = new TextField(item.getCategory());
        grid.add(categoryField, 1, 1);

        grid.add(new Label("Size:"), 0, 2);
        TextField sizeField = new TextField(String.valueOf(item.getSize()));
        grid.add(sizeField, 1, 2);

        grid.add(new Label("Price:"), 0, 3);
        TextField priceField = new TextField(String.valueOf(item.getPrice()));
        grid.add(priceField, 1, 3);

        Button saveButton = new Button("Save Changes");
        grid.add(saveButton, 1, 4);

        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String category = categoryField.getText();
            int size = Integer.parseInt(sizeField.getText());
            int price = Integer.parseInt(priceField.getText());

            // Use the ItemController to edit the item
            Response<Item> response = new ItemController().editItem(item.getId(), name, size, price, category);

            Dialog dialog = new Dialog();
            if (response.success) {
                dialog.showSuccessDialog("Item updated successfully!");
                // Redirect to another view or refresh
            } else {
                dialog.showErrorDialog(response.message);
            }
        });

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Edit Item");
        primaryStage.show();
    }
}
