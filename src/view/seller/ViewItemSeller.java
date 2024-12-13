package view.seller;

import java.util.Vector;

import controller.ItemController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
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
import utils.SceneCreator;
import view.component.Navbar;

public class ViewItemSeller extends BorderPane implements SceneCreator {

    private GridPane grid;
    private Dialog dialog;
    private MenuBar navbar;
    private VBox formContainer;
    private TableView<Item> table;
    private TextField itemIdField, itemNameField, priceField, sizeField, categoryField;
    
    public ViewItemSeller() {
    	init();
    	design();
    	createTable();
    	setAction();        
        fetch();
    }
    
	@Override
	public void init() {
		grid = new GridPane();
		navbar = new Navbar(LoggedUser.getInstance().getCurrentUser().getRoles());
		dialog = new Dialog();
		formContainer = new VBox();
		table = new TableView<>();
		
        itemIdField = new TextField();        
        itemNameField = new TextField();
        priceField = new TextField();
        sizeField = new TextField();
        categoryField = new TextField();
	}

	@Override
	public void design() {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        formContainer.setSpacing(10);
        itemIdField.setDisable(true); 
        
        grid.add(table, 0, 0);
        grid.add(formContainer, 1,0);
        this.setTop(navbar);
        this.setCenter(grid);
	}
	
	@SuppressWarnings("unchecked")
	public void createTable() {
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
	}
	
	private void setAction() {
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
                	fetch();
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
                ItemController.getInstance().deleteItem(selectedItem.getItemId());
            } else {
            	dialog.showErrorDialog("You need to select the item first!");
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
	}

    private void fetch() {
        Response<Vector<Item>> response = ItemController.getInstance().viewItemBySellerId(LoggedUser.getInstance().getCurrentUser().getUserId());
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
	}


}
