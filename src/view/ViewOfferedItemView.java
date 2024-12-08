package view;

import controller.OfferController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Offer;
import model.Item;
import utils.Dialog;

import java.util.Vector;

public class ViewOfferedItemView {

    private String sellerId; // Seller ID to filter items

    public ViewOfferedItemView(String sellerId) {
        this.sellerId = sellerId; // Pass the seller ID to this view
    }

    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Table for displaying offers
        TableView<Offer> tableView = new TableView<>();
        TableColumn<Offer, String> itemIdColumn = new TableColumn<>("Item ID");
//        itemIdColumn.setCellValueFactory(data -> data.getValue().itemIdProperty());

        TableColumn<Offer, String> buyerIdColumn = new TableColumn<>("Buyer ID");
//        buyerIdColumn.setCellValueFactory(data -> data.getValue().userIdProperty());

        TableColumn<Offer, Integer> offerPriceColumn = new TableColumn<>("Offer Price");
//        offerPriceColumn.setCellValueFactory(data -> data.getValue().offerPriceProperty().asObject());

        tableView.getColumns().addAll(itemIdColumn, buyerIdColumn, offerPriceColumn);

        // Populate table with data from the controller
        OfferController offerController = new OfferController();
//        Vector<Offer> offers = offerController.viewOffersForSeller(sellerId);
//        ObservableList<Offer> offerList = FXCollections.observableArrayList(offers);
//        tableView.setItems(offerList);

        root.setCenter(tableView);

        // Bottom buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button acceptOfferButton = new Button("Accept Offer");
        Button declineOfferButton = new Button("Decline Offer");
        buttonBox.getChildren().addAll(acceptOfferButton, declineOfferButton);
        root.setBottom(buttonBox);

        // Accept Offer Button Action
        acceptOfferButton.setOnAction(e -> {
            Offer selectedOffer = tableView.getSelectionModel().getSelectedItem();
            if (selectedOffer != null) {
//                var response = offerController.acceptOffer(selectedOffer.getOfferId());
                Dialog dialog = new Dialog();
//                if (response.success) {
//                    dialog.showSuccessDialog("Offer accepted successfully!");
//                    tableView.getItems().remove(selectedOffer); // Remove accepted offer
//                } else {
//                    dialog.showErrorDialog(response.message);
//                }
            } else {
                showAlert("No Offer Selected", "Please select an offer to accept.");
            }
        });

        // Decline Offer Button Action
//        declineOfferButton.setOnAction(e -> {
//            Offer selectedOffer = tableView.getSelectionModel().getSelectedItem();
//            if (selectedOffer != null) {
//                TextInputDialog declineDialog = new TextInputDialog();
//                declineDialog.setTitle("Decline Offer");
//                declineDialog.setHeaderText("Reason for declining the offer:");
//                declineDialog.setContentText("Reason:");
//
//                declineDialog.showAndWait().ifPresent(reason -> {
//                    if (!reason.isEmpty()) {
//                        var response = offerController.declineOffer(selectedOffer.getOfferId(), reason);
//                        Dialog dialog = new Dialog();
//                        if (response.success) {
//                            dialog.showSuccessDialog("Offer declined successfully!");
//                            tableView.getItems().remove(selectedOffer); // Remove declined offer
//                        } else {
//                            dialog.showErrorDialog(response.message);
//                        }
//                    } else {
//                        showAlert("Invalid Input", "Reason cannot be empty.");
//                    }
//                });
//            } else {
//                showAlert("No Offer Selected", "Please select an offer to decline.");
//            }
//        });

        // Create the scene and show it
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("View Offered Items");
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
