package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainController implements Initializable {
	
	@FXML
	private Label lbEmpleado;
	
	public MainController(){
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbEmpleado.setText(App.getUserSesion().getEmpleado().getId());		
	}

	@FXML
	public void switchToNewVenta() throws IOException {
		
		App.setRoot("NuevaVenta");
		
	}	
	
}
