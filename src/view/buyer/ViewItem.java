package view.buyer;

import java.util.Vector;

import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Item;
import model.Transaction;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;

public class ViewItem extends BorderPane implements SceneCreator {

    private GridPane grid;
    private Dialog dialog;
    private Navbar navbar;
    private TableView<Item> table;
    private Button buyButton, offerButton, wishlistButton;
    private HBox buttonContainer;

    public ViewItem() {
    	init();
    	design();
    	createTable();
        setAction();
        fetch();
    }

    @Override
    public void init() {
        grid = new GridPane();
        dialog = new Dialog();
        navbar = new Navbar(LoggedUser.getInstance().getCurrentUser().getRoles());
        table = new TableView<>();
        buyButton = new Button("Buy");
        offerButton = new Button("Offer");
        wishlistButton = new Button("Wishlist");
        buttonContainer = new HBox(20);
    }

    @Override
    public void design() {
    	grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(table, 0, 0);
        
        buttonContainer.getChildren().addAll(buyButton, offerButton, wishlistButton);
        grid.add(buttonContainer, 0, 1);
        
        this.setTop(navbar);
        this.setCenter(grid);
    }
    
    public void setAction() {
    	buyButton.setOnAction(event -> {
    		Item selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
            	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Purchase");
                alert.setHeaderText("Are you sure you want to buy this item?");
                alert.setContentText("Item: " + selectedItem.getName() + "\nPrice: " + selectedItem.getPrice());

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        Response<Transaction> responseTransaction = TransactionController.getInstance()
                                 .createTransaction(LoggedUser.getInstance().getCurrentUser().getUserId(), selectedItem.getItemId());

                        if (responseTransaction.success) {
                            dialog.showSuccessDialog(responseTransaction.message);
                        } else {
                             dialog.showErrorDialog(responseTransaction.message);
                        }
                	}else {
                         dialog.showErrorDialog("Purchase cancelled.");
                    }
                });
             }else {
                 dialog.showErrorDialog("Please select an item to buy.");
             }
         });
    	 
    	 
    	offerButton.setOnAction(event -> {
            Item selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
            	showOfferPopUp(selectedItem);
            } else {
                dialog.showErrorDialog("Please select an item to make an offer.");
            }
        });
    	 
		wishlistButton.setOnAction(event -> {
			 Item selectedItem = table.getSelectionModel().getSelectedItem();
			 if(selectedItem != null) {
				 WishlistController.getInstance().addWishlist(LoggedUser.
						 getInstance().getCurrentUser().getUserId(), 
						 selectedItem.getItemId());
				 dialog.showSuccessDialog("Wishlist succesfully added");
			 }else {
				 dialog.showErrorDialog("Please select an item to make an wishlist.");
			 }
		 });
    }
    
    private void showOfferPopUp(Item selectedItem) {
        TextInputDialog offerDialog = new TextInputDialog();
        offerDialog.setTitle("Make an Offer");
        offerDialog.setHeaderText("Enter your offer price for " + selectedItem.getName());
        offerDialog.setContentText("Offer Price:");

        // Show the dialog and handle the response
        offerDialog.showAndWait().ifPresent(offerPrice -> {
            try {
//                int price = Integer.parseInt(offerPrice);
//                if (price <= 0) {
//                    dialog.showErrorDialog("Offer price must be greater than zero.");
//                } else {
//                    Response<String> response = ItemController.getInstance().makeOffer(selectedItem.getItemId(), LoggedUser.getInstance().getCurrentUser().getUserId(), price);
//                    if (response.success) {
//                        dialog.showSuccessDialog(response.message);
//                    } else {
//                        dialog.showErrorDialog(response.message);
//                    }
//                }
            } catch (NumberFormatException e) {
                dialog.showErrorDialog("Invalid input. Please enter a valid number.");
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void createTable() {
    	table.setPrefWidth(600);
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemId()));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Item, Integer> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSize()).asObject());

        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));

        double columnWidth = 600.0 / 5;
        itemIdColumn.setPrefWidth(columnWidth);
        itemNameColumn.setPrefWidth(columnWidth);
        sizeColumn.setPrefWidth(columnWidth);
        priceColumn.setPrefWidth(columnWidth);
        categoryColumn.setPrefWidth(columnWidth);

        table.getColumns().addAll(itemIdColumn, itemNameColumn, sizeColumn, priceColumn, categoryColumn);
    }

    private void fetch() {
        Response<Vector<Item>> response = ItemController.getInstance().viewApprovedStatusItem();
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
    }
}
