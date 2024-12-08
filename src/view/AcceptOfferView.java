package view;

import controller.OfferController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Offer;
import utils.Dialog;
import utils.Response;

public class AcceptOfferView {
    private Offer offer; // The offer to be accepted

    public AcceptOfferView(Offer offer) {
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

        // Buttons for confirmation
        Button confirmButton = new Button("Accept Offer");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 3);

        confirmButton.setOnAction(e -> {
            OfferController offerController = new OfferController();
//            Response<Offer> response = offerController.acceptOffer(offer.getOfferId());

            Dialog dialog = new Dialog();
//            if (response.success) {
//                dialog.showSuccessDialog("Offer accepted successfully!");
//                primaryStage.close(); // Close the view after accepting the offer
//            } else {
//                dialog.showErrorDialog(response.message);
//            }
        });

        cancelButton.setOnAction(e -> primaryStage.close()); // Close the view if canceled

        primaryStage.setScene(new Scene(grid, 400, 300));
        primaryStage.setTitle("Accept Offer");
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
