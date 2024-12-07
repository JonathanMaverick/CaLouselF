package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Dialog {

    public void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    public void showSuccessDialog(String message) {
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Success");
        successAlert.setContentText(message);
        successAlert.showAndWait();
    }

    
}
