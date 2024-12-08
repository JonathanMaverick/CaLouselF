package view;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.Dialog;
import utils.Response;

public class UploadItemView {
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        grid.add(new Label("Item Name:"), 0, 0);
        TextField nameField = new TextField();
        grid.add(nameField, 1, 0);

        grid.add(new Label("Category:"), 0, 1);
        TextField categoryField = new TextField();
        grid.add(categoryField, 1, 1);

        grid.add(new Label("Size:"), 0, 2);
        TextField sizeField = new TextField();
        grid.add(sizeField, 1, 2);

        grid.add(new Label("Price:"), 0, 3);
        TextField priceField = new TextField();
        grid.add(priceField, 1, 3);

        Button uploadButton = new Button("Upload");
        grid.add(uploadButton, 1, 4);

        uploadButton.setOnAction(e -> {
            String name = nameField.getText();
            String category = categoryField.getText();
            int size = Integer.parseInt(sizeField.getText());
            int price = Integer.parseInt(priceField.getText());

            Response response = new ItemController().uploadItem(name, size, price, category);

            Dialog dialog = new Dialog();
            if (response.success) {
                dialog.showSuccessDialog(response.message);
            } else {
                dialog.showErrorDialog(response.message);
            }
        });

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Upload Item");
        primaryStage.show();
    }
}
