package edu.gestock.gestockProyect;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainController {

	@FXML
	public void switchToNewVenta() throws IOException {
		App.setRoot("NuevaVenta");
	}
	
}
