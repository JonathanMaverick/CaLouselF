package view.seller;

import java.util.Vector;

import controller.OfferController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Offer;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;

public class OfferHistory extends BorderPane implements SceneCreator {

    private GridPane grid;
    private Dialog dialog;
    private MenuBar navbar;
    private TableView<Offer> table;
    
    public OfferHistory() {
    	init();
    	design();
    	createTable();
        fetch();
    }
    
	@Override
	public void init() {
		grid = new GridPane();
		navbar = new Navbar(LoggedUser.getInstance().getCurrentUser().getRoles());
		dialog = new Dialog();
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
	public void createTable() {
		TableColumn<Offer, String> offerIdColumn = new TableColumn<>("Offer ID");
		offerIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOfferId()));

		TableColumn<Offer, String> itemIdColumn = new TableColumn<>("Item ID");
		itemIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemId()));
		
        TableColumn<Offer, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        
        TableColumn<Offer, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());

        TableColumn<Offer, Integer> offerPrice = new TableColumn<>("Offer Price");
        offerPrice.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getOfferPrice()).asObject());
        
        TableColumn<Offer, String> reasonColumn = new TableColumn<>("Reason");
        reasonColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReason()));
        
        double columnWidth = 600.0 / 6;
        offerIdColumn.setPrefWidth(columnWidth);
        itemIdColumn.setPrefWidth(columnWidth);
        itemNameColumn.setPrefWidth(columnWidth);
        reasonColumn.setPrefWidth(columnWidth);
        priceColumn.setPrefWidth(columnWidth);
        offerPrice.setPrefWidth(columnWidth);
        
        table.getColumns().addAll(offerIdColumn, itemIdColumn, itemNameColumn, priceColumn, offerPrice, reasonColumn);
	}

    private void fetch() {
        Response<Vector<Offer>> response = OfferController.getInstance().getOfferHistoryBySellerId(LoggedUser.getInstance().getCurrentUser().getUserId());
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
	}


}
