package view.admin;

import java.util.Vector;

import controller.ItemController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;
import utils.Dialog;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;

public class ViewAllItem extends BorderPane implements SceneCreator {

    private GridPane grid;
    private Dialog dialog;
    private MenuBar navbar;
    private TableView<Item> table;
    
	public ViewAllItem() {
    	init();
    	design();
        
        fetch();
    }
    
	@Override
	public void init() {
		dialog = new Dialog();
		navbar = new Navbar("Admin");
		grid = new GridPane();
		table = new TableView<>();
		createTable();
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
	public void createTable() {
		table.setPrefWidth(600);
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemId()));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        
        TableColumn<Item, Integer> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSize()).asObject());

        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());
        
        TableColumn<Item, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        
        TableColumn<Item, String> reasonColumn = new TableColumn<>("Reason");
        reasonColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReason()));
        
        double columnWidth = 600.0 / 7;
        
        itemIdColumn.setPrefWidth(columnWidth);
        itemNameColumn.setPrefWidth(columnWidth);
        sizeColumn.setPrefWidth(columnWidth);
        priceColumn.setPrefWidth(columnWidth);
        statusColumn.setPrefWidth(columnWidth);
        categoryColumn.setPrefWidth(columnWidth);
        reasonColumn.setPrefWidth(columnWidth);

        table.getColumns().addAll(itemIdColumn, itemNameColumn, sizeColumn, priceColumn, categoryColumn, statusColumn, reasonColumn);
	}
    
    private void fetch() {
        Response<Vector<Item>> response = ItemController.getInstance().viewItem();
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
	}
}