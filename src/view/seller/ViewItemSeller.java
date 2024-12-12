package view.seller;

import java.util.Vector;

import controller.ItemController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Item;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.Viewable;
import view.ViewManager;
import view.component.Navbar;

public class ViewItemSeller implements Viewable {

    private final BorderPane borderPane;
    private final GridPane grid;

    @SuppressWarnings("unchecked")
    public ViewItemSeller(ViewManager vm) {
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

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Description");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));

        table.getColumns().addAll(itemIdColumn, itemNameColumn, sizeColumn, priceColumn, categoryColumn);
        grid.add(table, 0, 0);

        VBox formContainer = new VBox();
        formContainer.setSpacing(10);

        TextField itemIdField = new TextField();
        itemIdField.setDisable(true); 

        TextField itemNameField = new TextField();
        TextField priceField = new TextField();
        TextField sizeField = new TextField();
        TextField categoryField = new TextField();
        
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                itemIdField.setText(newValue.getItemId());
                itemNameField.setText(newValue.getName());
                priceField.setText(String.valueOf(newValue.getPrice()));
                sizeField.setText(String.valueOf(newValue.getSize()));
                categoryField.setText(newValue.getCategory());
            } else {
                itemIdField.clear();
                itemNameField.clear();
                sizeField.clear();
                priceField.clear();
                categoryField.clear();
            }
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction(event -> {
            try {
                int size = Integer.parseInt(sizeField.getText());
                int price = Integer.parseInt(priceField.getText());
                Response<Item> res = ItemController.getInstance().editItem(itemIdField.getText(), itemNameField.getText(), size, price, categoryField.getText());
                if(res.success) {                	
                	dialog.showSuccessDialog(res.message);
                	fetch(table, dialog);
                }
                else {
                	dialog.showErrorDialog(res.message);
                }
            } catch (NumberFormatException e) {
                dialog.showErrorDialog("Invalid input. Size and price must be integers.");
            }
        });
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            Item selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Implement logic to delete the selected item
            } else {
                // Show error message if no item is selected
            }
        });

        formContainer.getChildren().addAll(
                new Label("Item ID:"), itemIdField,
                new Label("Item Name:"), itemNameField,
                new Label("Size"), sizeField,
                new Label("Price:"), priceField,
                new Label("Description:"), categoryField,
                updateButton,
                deleteButton
        );
        
        grid.add(formContainer, 1,0);

        borderPane.setCenter(grid);
        
        fetch(table, dialog);
    }
    
    private void fetch(TableView<Item> table, Dialog dialog) {
        Response<Vector<Item>> response = ItemController.getInstance().viewItemBySellerId(LoggedUser.getInstance().getCurrentUser().getUserId());
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
