package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.LoggedUser;
import utils.Viewable;
import view.component.Navbar;

public class HomeView implements Viewable {

	private final BorderPane grid;
	

    public HomeView(ViewManager viewManager) {
        grid = new BorderPane();

        MenuBar navbar = new Navbar().createNavbar(LoggedUser.getInstance().getCurrentUser().getRoles());
        grid.setTop(navbar);

        VBox mainContent = createMainContent();
        grid.setCenter(mainContent);
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox();
        mainContent.setSpacing(20); 

        Label welcomeLabel = new Label("Welcome, " + LoggedUser.getInstance().getCurrentUser().getUsername());
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label additionalInfoLabel = new Label("Here is your personalized dashboard.");
        
        mainContent.getChildren().addAll(welcomeLabel, additionalInfoLabel);

        return mainContent;
    }

	@Override
	public Pane getView() {
		return grid;
	}
    
}
