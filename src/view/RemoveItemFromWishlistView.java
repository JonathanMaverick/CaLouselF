package view;

//import controller.WishlistController;
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

public class RemoveItemFromWishlistView {
    private Item item; // The item to be removed
    private String buyerId; // Buyer ID

    public RemoveItemFromWishlistView(Item item, String buyerId) {
        this.item = item; // Pass the item to this view
        this.buyerId = buyerId; // Pass the buyer ID to this view
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

        // Confirmation buttons
        Button removeButton = new Button("Remove from Wishlist");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, removeButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 4);

        removeButton.setOnAction(e -> {
//            WishlistController wishlistController = new WishlistController();
//            Response<String> response = wishlistController.removeItemFromWishlist(item.getItemId(), buyerId);

            Dialog dialog = new Dialog();
//            if (response.success) {
//                dialog.showSuccessDialog("Item removed from wishlist successfully!");
//                primaryStage.close(); // Close the view after removing the item
//            } else {
//                dialog.showErrorDialog(response.message);
//            }
        });

        cancelButton.setOnAction(e -> primaryStage.close()); // Close the view if canceled

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Remove Item from Wishlist");
        primaryStage.show();
    }
}
