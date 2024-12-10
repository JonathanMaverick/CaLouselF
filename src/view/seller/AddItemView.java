package view.seller;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class AddItemView implements Viewable {

    private final BorderPane borderPane;
    private final GridPane grid;

    public AddItemView(ViewManager vm) {
    	borderPane = new BorderPane();

    	Navbar navbar = Navbar.getInstance(vm, LoggedUser.getInstance().getCurrentUser().getRoles());
    	borderPane.setTop(navbar);
    	
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label nameLabel = new Label("Item Name:");
        grid.add(nameLabel, 0, 0);

        TextField nameField = new TextField();
        grid.add(nameField, 1, 0);

        Label sizeLabel = new Label("Size :");
        grid.add(sizeLabel, 0, 1);

        TextField sizeField = new TextField();
        grid.add(sizeField, 1, 1);

        Label priceLabel = new Label("Price: ");
        grid.add(priceLabel, 0, 2);

        TextField priceField = new TextField();
        grid.add(priceField, 1, 2);

        Label categoryLabel = new Label("Category:");
        grid.add(categoryLabel, 0, 3);

        TextField categoryField = new TextField();
        grid.add(categoryField, 1, 3);

        Button addButton = new Button("Tambah Item");
        grid.add(addButton, 1, 4);

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
        borderPane.setCenter(grid);
    }

    @Override
    public Scene getView() {
        return new Scene(borderPane, 800, 600);
    }
}
