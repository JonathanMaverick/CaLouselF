package view.admin;

import java.util.Vector;

import controller.ItemController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;

public class ViewItemPending extends BorderPane implements SceneCreator {

    private MenuBar navbar;
    private GridPane grid;
    private Dialog dialog;
    private TableView<Item> table;
    private Stage rejectionStage;
    private Button approveButton;
    private Button rejectButton;
    
    public ViewItemPending() {
    	init();
        design();
        fetch();
    }

    @Override
    public void init() {
        dialog = new Dialog();
        navbar = new Navbar(LoggedUser.getInstance().getCurrentUser().getRoles());
        this.setTop(navbar);

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        table = new TableView<>();
        table.setPrefWidth(600);
        createTableColumns();
        
        approveButton = new Button("Approve");
        approveButton.setOnAction(event -> handleApproveButtonClick());

        rejectButton = new Button("Reject");
        rejectButton.setOnAction(event -> handleRejectButtonClick());
        
    }

    @SuppressWarnings("unchecked")
	private void createTableColumns() {
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

        table.getColumns().addAll(itemIdColumn, itemNameColumn, sizeColumn, priceColumn, categoryColumn, statusColumn);
        grid.add(table, 0, 0);
        
        double columnWidth = 600.0 / 6;
        itemIdColumn.setPrefWidth(columnWidth);
        itemNameColumn.setPrefWidth(columnWidth);
        sizeColumn.setPrefWidth(columnWidth);
        priceColumn.setPrefWidth(columnWidth);
        statusColumn.setPrefWidth(columnWidth);
        categoryColumn.setPrefWidth(columnWidth);        
    }

    @Override
    public void design() {
        this.setCenter(grid);

        VBox buttonContainer = createButtons();
        grid.add(buttonContainer, 1, 0);
    }

    private VBox createButtons() {
        VBox buttonContainer = new VBox(10, approveButton, rejectButton);
        buttonContainer.setAlignment(Pos.CENTER);
        return buttonContainer;
    }

    private void handleApproveButtonClick() {
        Item selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Response<Item> res = ItemController.getInstance().approveItem(selectedItem.getItemId());
            if (res.success) {
                dialog.showSuccessDialog("Item approved successfully.");
                fetch();
            } else {
                dialog.showErrorDialog(res.message);
            }
        } else {
            dialog.showErrorDialog("No item selected.");
        }
    }

    private void handleRejectButtonClick() {
        Item selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            showRejectionForm(selectedItem);
        } else {
            dialog.showErrorDialog("No item selected.");
        }
    }

    private void showRejectionForm(Item selectedItem) {
        rejectionStage = new Stage();
        rejectionStage.setTitle("Reject Item");

        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(10));
        formLayout.setAlignment(Pos.CENTER);

        Label reasonLabel = new Label("Rejection Reason:");
        TextField reasonField = new TextField();
        reasonField.setPromptText("Enter reason here...");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmitRejection(selectedItem, reasonField));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> rejectionStage.close());

        HBox buttonBox = new HBox(10, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        formLayout.getChildren().addAll(reasonLabel, reasonField, buttonBox);

        Scene scene = new Scene(formLayout, 300, 200);
        rejectionStage.setScene(scene);
        rejectionStage.show();
    }

    private void handleSubmitRejection(Item selectedItem, TextField reasonField) {
        String reason = reasonField.getText().trim();
        if (!reason.isEmpty()) {
            Response<Item> res = ItemController.getInstance().declineItem(selectedItem.getItemId(), reason);
            if (res.success) {
                dialog.showSuccessDialog("Item rejected successfully.");
                fetch();
                rejectionStage.close();
            } else {
                dialog.showErrorDialog(res.message);
            }
        } else {
            dialog.showErrorDialog("Rejection reason cannot be empty.");
        }
    }

    private void fetch() {
        Response<Vector<Item>> response = ItemController.getInstance().viewItemPending();
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
    }
}
