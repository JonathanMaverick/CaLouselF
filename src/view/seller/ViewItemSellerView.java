package view.seller;

import java.util.Vector;

import controller.ItemController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.Viewable;
import view.ViewManager;
import view.component.Navbar;

public class ViewItemSellerView implements Viewable {

    private final BorderPane borderPane;
    private final GridPane grid;

    @SuppressWarnings("unchecked")
	public ViewItemSellerView(ViewManager vm) {
        borderPane = new BorderPane();

        Navbar navbar = Navbar.getInstance(vm, LoggedUser.getInstance().getCurrentUser().getRoles());
        borderPane.setTop(navbar);

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        TableView<Item> table = new TableView<>();
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Description");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));

        table.getColumns().addAll(itemIdColumn, itemNameColumn, priceColumn, categoryColumn);
        grid.add(table, 0, 2, 2, 1);

        Response<Vector<Item>> response = ItemController.getInstance().viewItemBySellerId(LoggedUser.getInstance().getCurrentUser().getUserId());
        if (response.success) {
        	System.out.println(response.data);
            table.getItems().setAll(response.data); 
        } else {
            Dialog errorDialog = new Dialog();
            errorDialog.showErrorDialog(response.message);
        }

        borderPane.setCenter(grid);
    }

    @Override
    public Scene getView() {
        return new Scene(borderPane, 800, 600);
    }
}
