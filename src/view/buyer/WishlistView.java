package view.buyer;

import java.util.Vector;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Transaction;
import model.Wishlist;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;


public class WishlistView extends BorderPane implements SceneCreator {

    private GridPane grid;
    private Dialog dialog;
    private Navbar navbar;
    private TableView<Wishlist> table;
    private Button checkOutButton, deleteButton;
    private HBox buttonContainer;

    public WishlistView() {
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
        checkOutButton = new Button("Checkout");
        deleteButton = new Button("Delete");
        buttonContainer = new HBox(20);
    }

    @Override
    public void design() {
    	grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(table, 0, 0);
        
        buttonContainer.getChildren().addAll(checkOutButton, deleteButton);
        grid.add(buttonContainer, 0, 1);
        
        this.setTop(navbar);
        this.setCenter(grid);
    }
    
    public void setAction() {
    	checkOutButton.setOnAction(event -> {
    		Wishlist selectedWishlist = table.getSelectionModel().getSelectedItem();
			if (selectedWishlist != null) {
            	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Purchase");
                alert.setHeaderText("Are you sure you want to checkout this item?");
                alert.setContentText("Item: " + selectedWishlist.getName() + "\nPrice: " + selectedWishlist.getPrice());

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        Response<Transaction> responseTransaction = TransactionController.getInstance()
                             .createTransaction(LoggedUser.getInstance()
                    		 .getCurrentUser().getUserId(), selectedWishlist.getItemId());

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
    	 
		deleteButton.setOnAction(event -> {
			 Wishlist selectedWishlist = table.getSelectionModel().getSelectedItem();
			 if(selectedWishlist != null) {
				 WishlistController.getInstance().removeWishlist(selectedWishlist.getWishlistId());
				 dialog.showSuccessDialog("Wishlist succesfully remove");
			 }else {
				 dialog.showErrorDialog("Please select an item to make an wishlist.");
			 }
			 fetch();
		 });
    }

    @SuppressWarnings("unchecked")
    private void createTable() {
    	table.setPrefWidth(600);
        TableColumn<Wishlist, String> wishlistIdColumn = new TableColumn<>("Wishlist ID");
        wishlistIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWishlistId()));
    	
        TableColumn<Wishlist, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemId()));

        TableColumn<Wishlist, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Wishlist, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());

        double columnWidth = 600.0 / 4;
        wishlistIdColumn.setPrefWidth(columnWidth);
        itemIdColumn.setPrefWidth(columnWidth);
        itemNameColumn.setPrefWidth(columnWidth);
        priceColumn.setPrefWidth(columnWidth);

        table.getColumns().addAll(wishlistIdColumn, itemIdColumn, itemNameColumn, priceColumn);
    }

    private void fetch() {
    	table.getItems().clear();
        Response<Vector<Wishlist>> response = 
    			WishlistController.getInstance().
    			viewWishlist(LoggedUser.getInstance().getCurrentUser().getUserId());
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
    }
}
