package edu.gestock.gestockProyect;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void switchToMainScene() throws IOException {
        App.setRoot("Main");
    }
    
    @FXML
    private void switchToLogin() throws IOException {
    	App.setRoot("Login");
    }
}
