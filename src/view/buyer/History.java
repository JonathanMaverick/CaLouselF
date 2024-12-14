package view.buyer;

import java.util.Vector;

import controller.TransactionController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Transaction;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;


public class History extends BorderPane implements SceneCreator {

    private GridPane grid;
    private Dialog dialog;
    private Navbar navbar;
    private TableView<Transaction> table;

    public History() {
    	init();
    	design();
    	createTable();
        fetch();
    }

    @Override
    public void init() {
        grid = new GridPane();
        dialog = new Dialog();
        navbar = new Navbar(LoggedUser.getInstance().getCurrentUser().getRoles());
        table = new TableView<>();
    }

    @Override
    public void design() {
    	grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(table, 0, 0);
                
        this.setTop(navbar);
        this.setCenter(grid);
    }

    @SuppressWarnings("unchecked")
    private void createTable() {
    	table.setPrefWidth(600);
        TableColumn<Transaction, String> transactionIdColumn = new TableColumn<>("Transaction ID");
        transactionIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTransactionId()));
    	
        TableColumn<Transaction, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemId()));

        TableColumn<Transaction, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemName()));
        
        TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemCategory()));

        TableColumn<Transaction, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getItemPrice()).asObject());

        TableColumn<Transaction, Integer> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getItemSize()).asObject());
        
        double columnWidth = 600.0 / 6;
        transactionIdColumn.setPrefWidth(columnWidth);
        itemIdColumn.setPrefWidth(columnWidth);
        itemNameColumn.setPrefWidth(columnWidth);
        priceColumn.setPrefWidth(columnWidth);
        categoryColumn.setPrefWidth(columnWidth);
        sizeColumn.setPrefWidth(columnWidth);
        
        table.getColumns().addAll(transactionIdColumn, itemIdColumn, itemNameColumn, priceColumn, categoryColumn, sizeColumn);
    }

    private void fetch() {
    	table.getItems().clear();
        Response<Vector<Transaction>> response = 
    			TransactionController.getInstance()
    			.viewHistory(LoggedUser.getInstance().getCurrentUser().getUserId());
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
    }
    
}
