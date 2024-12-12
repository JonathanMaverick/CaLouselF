package view.admin;

import java.util.Vector;

import controller.ItemController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.Viewable;
import view.ViewManager;
import view.component.Navbar;

public class ViewItemPending implements Viewable {

    private final BorderPane borderPane;
    private final GridPane grid;

    @SuppressWarnings("unchecked")
    public ViewItemPending(ViewManager vm) {
    	Dialog dialog = new Dialog();
    	
        borderPane = new BorderPane();

        Navbar navbar = Navbar.getInstance(vm, LoggedUser.getInstance().getCurrentUser().getRoles());
        borderPane.setTop(navbar);

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TableView<Item> table = new TableView<>();
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

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Description");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        
        TableColumn<Item, String> reasonColumn = new TableColumn<>("Reason");
        reasonColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));

        table.getColumns().addAll(itemIdColumn, itemNameColumn, sizeColumn, priceColumn, categoryColumn, statusColumn);
        grid.add(table, 0, 0);

        TextField itemIdField = new TextField();
        itemIdField.setDisable(true); 

        Button approveButton = new Button("Approve");
        approveButton.setOnAction(event -> {
            Item selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Response<Item> res = ItemController.getInstance().approveItem(selectedItem.getItemId());
                if (res.success) {
                    dialog.showSuccessDialog("Item approved successfully.");
                    fetch(table, dialog);
                } else {
                    dialog.showErrorDialog(res.message);
                }
            } else {
                dialog.showErrorDialog("No item selected.");
            }
        });

        Button rejectButton = new Button("Reject");
        rejectButton.setOnAction(event -> {
            Item selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Response<Item> res = ItemController.getInstance().declineItem(selectedItem.getItemId());
                if (res.success) {
                    dialog.showSuccessDialog("Item rejected successfully.");
                    fetch(table, dialog);
                } else {
                    dialog.showErrorDialog(res.message);
                }
            } else {
                dialog.showErrorDialog("No item selected.");
            }
        });

        borderPane.setCenter(grid);
        
        fetch(table, dialog);
    }
    
    private void fetch(TableView<Item> table, Dialog dialog) {
        Response<Vector<Item>> response = ItemController.getInstance().viewItemPending();
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
	}

    @Override
    public Scene getView() {
        return new Scene(borderPane, 800, 600);
    }
}