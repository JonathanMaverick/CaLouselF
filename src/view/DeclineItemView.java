package view;

import controller.ItemController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import model.Item;
import utils.Dialog;
import utils.Response;

public class DeclineItemView {

    public static void addDeclineButtonAction(Button declineButton, TableView<Item> tableView, ItemController itemController) {
        declineButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Open a dialog to get the reason for declining
                TextInputDialog reasonDialog = new TextInputDialog();
                reasonDialog.setTitle("Decline Item");
                reasonDialog.setHeaderText("Provide a reason for declining the item:");
                reasonDialog.setContentText("Reason:");

                reasonDialog.showAndWait().ifPresent(reason -> {
                    if (!reason.isEmpty()) {
                        // Call the controller to decline the item
//                        Response<Item> response = itemController.declineItem(selectedItem.getId(), reason);

                        // Show success or error dialog
                        Dialog dialog = new Dialog();
//                        if (response.success) {
//                            dialog.showSuccessDialog("Item declined successfully!");
//                            tableView.getItems().remove(selectedItem); // Remove the item from the table
//                        } else {
//                            dialog.showErrorDialog(response.message);
//                        }
                    } else {
                        // Show an error if the reason is empty
                        showAlert("Invalid Input", "Reason cannot be empty.");
                    }
                });
            } else {
                // Show warning if no item is selected
                showAlert("No Item Selected", "Please select an item to decline.");
            }
        });
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
