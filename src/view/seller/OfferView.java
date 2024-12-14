package view.seller;

import java.util.Vector;

import controller.OfferController;
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
import model.Offer;
import utils.Dialog;
import utils.LoggedUser;
import utils.Response;
import utils.SceneCreator;
import view.component.Navbar;

public class OfferView extends BorderPane implements SceneCreator {

    private GridPane grid;
    private Dialog dialog;
    private MenuBar navbar;
    private TableView<Offer> table;
    private Button approveButton, declineButton;
    private HBox buttonContainer;
    private Stage declineStage;
    
    public OfferView() {
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
		table = new TableView<>();
		approveButton = new Button("Approve");
		declineButton = new Button("Decline");
		buttonContainer = new HBox(20);
	}

	@Override
	public void design() {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.add(table, 0, 0);
        buttonContainer.getChildren().addAll(approveButton, declineButton);
        grid.add(buttonContainer, 0, 1);
        
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
        
        TableColumn<Offer, Integer> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSize()).asObject());

        TableColumn<Offer, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());

        TableColumn<Offer, Integer> offerPrice = new TableColumn<>("Offer Price");
        offerPrice.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getOfferPrice()).asObject());

        double columnWidth = 600.0 / 7;
        offerIdColumn.setPrefWidth(columnWidth);
        itemIdColumn.setPrefWidth(columnWidth);
        itemNameColumn.setPrefWidth(columnWidth);
        sizeColumn.setPrefWidth(columnWidth);
        priceColumn.setPrefWidth(columnWidth);
        sizeColumn.setPrefWidth(columnWidth);
        offerPrice.setPrefWidth(columnWidth);
        
        table.getColumns().addAll(offerIdColumn, itemIdColumn, itemNameColumn, sizeColumn, priceColumn, offerPrice);
	}
	
	private void setAction() {
		approveButton.setOnAction(event -> {
			Offer selectedOffer = table.getSelectionModel().getSelectedItem();
			if (selectedOffer != null) {
				OfferController.getInstance().acceptOffer(selectedOffer.getOfferId());
				fetch();
			}
			else{
				dialog.showErrorDialog("Please select an item to buy.");
			}
		});
		
		declineButton.setOnAction(event -> {
			Offer selectedOffer = table.getSelectionModel().getSelectedItem();
			if (selectedOffer != null) {
				showDeclineForm(selectedOffer);
				fetch();
			}
			else{
				dialog.showErrorDialog("Please select an item to buy.");
			}
		});
	}
	
	 private void showDeclineForm(Offer selectedOffer) {
        declineStage = new Stage();
        declineStage.setTitle("Decline Offer");

        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(10));
        formLayout.setAlignment(Pos.CENTER);

        Label reasonLabel = new Label("Decline Reason:");
        TextField reasonField = new TextField();
        reasonField.setPromptText("Enter reason here...");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmitRejection(selectedOffer, reasonField));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> declineStage.close());

        HBox buttonBox = new HBox(10, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        formLayout.getChildren().addAll(reasonLabel, reasonField, buttonBox);

        Scene scene = new Scene(formLayout, 300, 200);
        declineStage.setScene(scene);
        declineStage.show();
    }
	 
    private void handleSubmitRejection(Offer selectedOffer, TextField reasonField) {
        String reason = reasonField.getText().trim();
        if (!reason.isEmpty()) {
            Response<Offer> res = OfferController.getInstance().declineOffer(selectedOffer.getOfferId(), reason);
            if (res.success) {
                dialog.showSuccessDialog("Offer rejected successfully.");
                fetch();
                declineStage.close();
            } else {
                dialog.showErrorDialog(res.message);
            }
        } else {
            dialog.showErrorDialog("Rejection reason cannot be empty.");
        }
    }

    private void fetch() {
        Response<Vector<Offer>> response = OfferController.getInstance().getOfferBySellerId(LoggedUser.getInstance().getCurrentUser().getUserId());
        if (response.success) {
            table.getItems().setAll(response.data);
        } else {
            dialog.showErrorDialog(response.message);
        }
	}


}
