package view;

import controller.ItemController;
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

import java.util.Vector;

public class ViewItemView {

    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Table for displaying items
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
        ItemController itemController = new ItemController();
        Vector<Item> items = itemController.viewItem();
        ObservableList<Item> itemList = FXCollections.observableArrayList(items);
        tableView.setItems(itemList);

        root.setCenter(tableView);

        // Bottom buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button purchaseButton = new Button("Purchase");
        Button makeOfferButton = new Button("Make Offer");
        buttonBox.getChildren().addAll(purchaseButton, makeOfferButton);
        root.setBottom(buttonBox);

        // Action for Purchase Button
        purchaseButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
//                new PurchaseItemView(selectedItem).start(primaryStage); // Navigate to Purchase Item View
            } else {
                showAlert("No Item Selected", "Please select an item to purchase.");
            }
        });

        // Action for Make Offer Button
        makeOfferButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
//                new MakeOfferView(selectedItem).start(primaryStage); // Navigate to Make Offer View
            } else {
                showAlert("No Item Selected", "Please select an item to make an offer.");
            }
        });

        // Create the scene and show it
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("View Items");
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
