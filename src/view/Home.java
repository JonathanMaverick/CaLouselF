package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.LoggedUser;
import utils.SceneCreator;
import view.component.Navbar;

public class Home extends BorderPane implements SceneCreator {

    private VBox mainContent;
    private Label welcomeLabel;
    private Label additionalInfoLabel;
    private Text footerText;
    private MenuBar navbar;

    public Home() {
        init();
        design();
    }

    @Override
    public void init() {
        navbar = new Navbar(LoggedUser.getInstance().getCurrentUser().getRoles());

        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20, 20, 20, 20));

        footerText = new Text("© 2024 Your Application. All rights reserved.");
    }

    @Override
    public void design() {
        welcomeLabel = new Label("Welcome, " + LoggedUser.getInstance().getCurrentUser().getUsername());
        welcomeLabel.setFont(new Font("Arial", 24));
        welcomeLabel.setStyle("-fx-font-weight: bold;");

        additionalInfoLabel = new Label("Here is your personalized dashboard.");
        additionalInfoLabel.setFont(new Font("Arial", 16));

        mainContent.getChildren().addAll(welcomeLabel, additionalInfoLabel);

        footerText.setFill(Color.GRAY);
        footerText.setStyle("-fx-font-size: 12px;");

        this.setTop(navbar);
        this.setCenter(mainContent);
        this.setBottom(footerText);

        BorderPane.setMargin(footerText, new Insets(10, 0, 10, 0));
    }

}
