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
import utils.Dialog;
import utils.Response;

import java.util.Vector;

public class ViewRequestedItemView {

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
//        Vector<Item> pendingItems = itemController.viewPendingItems(); // Fetch only pending items
//        ObservableList<Item> itemList = FXCollections.observableArrayList(pendingItems);
//        tableView.setItems(itemList);

        root.setCenter(tableView);

        // Bottom buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button approveButton = new Button("Approve");
        Button declineButton = new Button("Decline");
        buttonBox.getChildren().addAll(approveButton, declineButton);
        root.setBottom(buttonBox);

        // Approve Button Action
//        approveButton.setOnAction(e -> {
//            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
//            if (selectedItem != null) {
//                Response<Item> response = itemController.approveItem(selectedItem.getItemId());
//                Dialog dialog = new Dialog();
//                if (response.success) {
//                    dialog.showSuccessDialog("Item approved successfully!");
//                    tableView.getItems().remove(selectedItem); // Remove from table after approval
//                } else {
//                    dialog.showErrorDialog(response.message);
//                }
//            } else {
//                showAlert("No Item Selected", "Please select an item to approve.");
//            }
//        });

        // Decline Button Action
        declineButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                TextInputDialog declineDialog = new TextInputDialog();
                declineDialog.setTitle("Decline Item");
                declineDialog.setHeaderText("Reason for declining the item:");
                declineDialog.setContentText("Reason:");

//                declineDialog.showAndWait().ifPresent(reason -> {
//                    if (!reason.isEmpty()) {
//                        Response<Item> response = itemController.declineItem(selectedItem.getItemId(), reason);
//                        Dialog dialog = new Dialog();
//                        if (response.success) {
//                            dialog.showSuccessDialog("Item declined successfully!");
//                            tableView.getItems().remove(selectedItem); // Remove from table after decline
//                        } else {
//                            dialog.showErrorDialog(response.message);
//                        }
//                    } else {
//                        showAlert("Invalid Input", "Reason cannot be empty.");
//                    }
//                });
            } else {
                showAlert("No Item Selected", "Please select an item to decline.");
            }
        });

        // Create the scene and show it
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("View Requested Items");
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
