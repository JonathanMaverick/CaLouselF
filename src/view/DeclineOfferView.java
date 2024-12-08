package view;

import controller.OfferController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Offer;
import utils.Dialog;
import utils.Response;

public class DeclineOfferView {
    private Offer offer; // The offer to be declined

    public DeclineOfferView(Offer offer) {
        this.offer = offer; // Pass the offer to this view
    }

    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        // Display offer details
        grid.add(new Label("Item ID:"), 0, 0);
//        grid.add(new Label(offer.getItemId()), 1, 0);

        grid.add(new Label("Buyer ID:"), 0, 1);
//        grid.add(new Label(offer.getUserId()), 1, 1);

        grid.add(new Label("Offer Price:"), 0, 2);
        grid.add(new Label(String.valueOf(offer.getOfferPrice())), 1, 2);

        grid.add(new Label("Reason for Declining:"), 0, 3);
        TextField reasonField = new TextField();
        grid.add(reasonField, 1, 3);

        // Buttons for confirmation
        Button confirmButton = new Button("Decline Offer");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 4);

        confirmButton.setOnAction(e -> {
            String reason = reasonField.getText();

            if (reason.isEmpty()) {
                showAlert("Invalid Input", "Reason cannot be empty.");
                return;
            }

            OfferController offerController = new OfferController();
//            Response<Offer> response = offerController.declineOffer(offer.getOfferId(), reason);

            Dialog dialog = new Dialog();
//            if (response.success) {
//                dialog.showSuccessDialog("Offer declined successfully!");
//                primaryStage.close(); // Close the view after declining the offer
//            } else {
//                dialog.showErrorDialog(response.message);
//            }
        });

        cancelButton.setOnAction(e -> primaryStage.close()); // Close the view if canceled

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Decline Offer");
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
