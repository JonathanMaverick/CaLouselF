package view;

//import controller.PurchaseHistoryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import model.PurchaseHistory;

import java.util.Vector;

public class ViewPurchaseHistoryView {
    private String buyerId; // Buyer ID to fetch purchase history

    public ViewPurchaseHistoryView(String buyerId) {
        this.buyerId = buyerId; // Pass the buyer ID to this view
    }

    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Table for displaying purchase history
//        TableView<PurchaseHistory> tableView = new TableView<>();
//        TableColumn<PurchaseHistory, String> transactionIdColumn = new TableColumn<>("Transaction ID");
//        transactionIdColumn.setCellValueFactory(data -> data.getValue().transactionIdProperty());

//        TableColumn<PurchaseHistory, String> itemNameColumn = new TableColumn<>("Item Name");
//        itemNameColumn.setCellValueFactory(data -> data.getValue().itemNameProperty());

//        TableColumn<PurchaseHistory, String> categoryColumn = new TableColumn<>("Category");
//        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

//        TableColumn<PurchaseHistory, Integer> sizeColumn = new TableColumn<>("Size");
//        sizeColumn.setCellValueFactory(data -> data.getValue().sizeProperty().asObject());

//        TableColumn<PurchaseHistory, Integer> priceColumn = new TableColumn<>("Price");
//        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

//        tableView.getColumns().addAll(transactionIdColumn, itemNameColumn, categoryColumn, sizeColumn, priceColumn);

        // Populate table with data from the controller
//        PurchaseHistoryController purchaseHistoryController = new PurchaseHistoryController();
//        Vector<PurchaseHistory> purchaseHistory = purchaseHistoryController.getPurchaseHistory(buyerId);
//        ObservableList<PurchaseHistory> historyList = FXCollections.observableArrayList(purchaseHistory);
//        tableView.setItems(historyList);

//        root.setCenter(tableView);

        // Create the scene and show it
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("View Purchase History");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
