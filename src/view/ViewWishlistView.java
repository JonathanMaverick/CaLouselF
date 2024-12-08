package view;

//import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Item;
import utils.Dialog;

import java.util.Vector;

public class ViewWishlistView {
    private String buyerId; // Buyer ID to fetch wishlist items

    public ViewWishlistView(String buyerId) {
        this.buyerId = buyerId; // Pass the buyer ID to this view
    }

    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Table for displaying wishlist items
        TableView<Item> tableView = new TableView<>();
        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
//        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
//        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<Item, Integer> sizeColumn = new TableColumn<>("Size");
//        sizeColumn.setCellValueFactory(data -> data.getValue().sizeProperty().asObject());

        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
//        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        tableView.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);

        // Populate table with data from the controller
//        WishlistController wishlistController = new WishlistController();
//        Vector<Item> wishlistItems = wishlistController.getWishlistItems(buyerId);
//        ObservableList<Item> itemList = FXCollections.observableArrayList(wishlistItems);
//        tableView.setItems(itemList);

        root.setCenter(tableView);

        // Bottom buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button removeFromWishlistButton = new Button("Remove from Wishlist");
        buttonBox.getChildren().add(removeFromWishlistButton);
        root.setBottom(buttonBox);

        // Remove from Wishlist Button Action
        removeFromWishlistButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
//            if (selectedItem != null) {
//                var response = wishlistController.removeItemFromWishlist(selectedItem.getItemId(), buyerId);
//                Dialog dialog = new Dialog();
//                if (response.success) {
//                    dialog.showSuccessDialog("Item removed from wishlist successfully!");
//                    tableView.getItems().remove(selectedItem); // Remove from table after removal
//                } else {
//                    dialog.showErrorDialog(response.message);
//                }
//            } else {
//                showAlert("No Item Selected", "Please select an item to remove from your wishlist.");
//            }
        });

        // Create the scene and show it
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("View Wishlist");
        primaryStage.setScene(scene);
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
