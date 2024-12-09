package view;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.LoggedUser;
import view.component.Navbar;

public class AppView {

    public AppView(Stage primaryStage) {
        BorderPane root = new BorderPane();

        MenuBar navbar = new Navbar().createNavbar(LoggedUser.getInstance().getCurrentUser().getRoles());
        root.setTop(navbar);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
