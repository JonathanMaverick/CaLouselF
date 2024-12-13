package view.seller;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;

public class AddItem extends BorderPane implements SceneCreator {

    private GridPane grid;
    private MenuBar navbar;
    private Label nameLabel, sizeLabel, priceLabel, categoryLabel;
    private TextField nameField, sizeField, priceField, categoryField;
    private Button addButton;
    
    public AddItem() {
    	init();
    	design();
    	setAction();
    }
    
	@Override
	public void init() {
		navbar = new Navbar(LoggedUser.getInstance().getCurrentUser().getRoles());
		grid = new GridPane();
		
		this.setTop(navbar);
		nameLabel = new Label("Item Name:");
		sizeLabel = new Label("Size :");
		priceLabel = new Label("Price: ");
		categoryLabel = new Label("Category:");
		
		nameField = new TextField();
		priceField = new TextField();
		categoryField = new TextField();
		sizeField = new TextField();
		
		addButton = new Button("Tambah Item");
	}

	@Override
	public void design() {
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(sizeLabel, 0, 1);
        grid.add(sizeField, 1, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(categoryLabel, 0, 3);
        grid.add(categoryField, 1, 3);
        grid.add(addButton, 1, 4);
        this.setCenter(grid);		
	}
	
	public void setAction() {
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String sizeText = sizeField.getText();
            String priceText = priceField.getText();
            String category = categoryField.getText();

            if (name.isEmpty() || sizeText.isEmpty() || priceText.isEmpty() || category.isEmpty()) {
            	Dialog errorDialog = new Dialog();
            	errorDialog.showErrorDialog("All values can't be empty");
                return;
            }
            try {
                int size = Integer.parseInt(sizeText);
                int price = Integer.parseInt(priceText);

                Response<Item> response = ItemController.getInstance().uploadItem(name, size, price, category);
                if (response.success) {
                    Dialog successDialog = new Dialog();
                    successDialog.showSuccessDialog(response.message);
                } else {
                    Dialog errorDialog = new Dialog();
                    errorDialog.showErrorDialog(response.message);
                }

                nameField.clear();
                sizeField.clear();
                priceField.clear();
                categoryField.clear();

            } catch (NumberFormatException ex) {
                Dialog errorDialog = new Dialog();
                errorDialog.showErrorDialog("Price and Size must be number.");
            }
        });
	}
}
