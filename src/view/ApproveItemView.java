package view;

import controller.ItemController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.Item;
import utils.Dialog;
import utils.Response;

public class ApproveItemView {

    public static void addApproveButtonAction(Button approveButton, TableView<Item> tableView, ItemController itemController) {
        approveButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Call the controller to approve the item
                Response<Item> response = itemController.approveItem(selectedItem.getId());

                // Show success or error dialog
                Dialog dialog = new Dialog();
                if (response.success) {
                    dialog.showSuccessDialog("Item approved successfully!");
                    tableView.getItems().remove(selectedItem); // Remove from table after approval
                } else {
                    dialog.showErrorDialog(response.message);
                }
            } else {
                // Show warning if no item is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Item Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select an item to approve.");
                alert.showAndWait();
            }
        });
    }
}
