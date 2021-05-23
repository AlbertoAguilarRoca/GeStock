package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;

public class MainController implements Initializable {
	
	@FXML
	private MenuButton mBtUser;
	@FXML
	private MenuBar mnBar;
	
	public MainController(){
		
	}
	
	/**
	 * El metodo inicialize controla que usuario ha iniciado sesión. En caso de no tener permisos de
	 * administrador, solo podrá realizar ventas.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mBtUser.setText(App.getUserSesion().getEmpleado().getId());
		
		if(App.getUserSesion().getEmpleado().getPermisos().equals("Standard")) {
			mnBar.setVisible(false);
		}
		
	}

	@FXML
	public void switchToEmpleados() throws IOException {
		App.setRoot("Empleados");
	}
	@FXML
	public void switchToNewVenta() throws IOException {		
		App.setRoot("NuevaVenta");		
	}
	
	@FXML
	public void logOut() throws IOException {
		App.setRoot("Login");
	}
	
	@FXML
	public void switchToProductos() throws IOException {
		App.setRoot("Productos");
	}
	
	@FXML
	public void switchToNewProductos() throws IOException {
		App.setRoot("NuevoProductos");
	}
	
}
