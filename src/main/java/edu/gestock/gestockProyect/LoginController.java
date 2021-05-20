package edu.gestock.gestockProyect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.manager.EmpleadoManager;
import edu.gestock.services.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	public LoginController() {
		
	}

	@FXML
	private TextField fdId;
	@FXML
	private PasswordField fdPassword;
	@FXML
	private Button btLogin;
	@FXML
	private Label lbError;
	
	@FXML
	public void userLogIn() throws IOException, ClassNotFoundException, SQLException {
		checkUserLog();
	}
	
	/**
	 * Método que comprueba las credenciales de los usuarios. Además guarda en memoria la sesión del usuario.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	public void checkUserLog() throws ClassNotFoundException, SQLException, IOException {
		
		Connection con = new Conector().getMySQLConnection();
		
		try {
			
			int contador = new EmpleadoManager().checkUserLogin(con, fdId.getText(), fdPassword.getText());
			if(fdId.getText().isEmpty() || fdPassword.getText().isEmpty()) {
				lbError.setText("Alguno de los campos está vacío.");
			} else if(contador == 0) {
				lbError.setText("El Usuario/Contraseña introducido es incorrecto");
			} else {
				Empleado empleado = new EmpleadoManager().findEmpleadoById(con, fdId.getText());				
				
				/*Guardamos la sesion en un objeto usersesion*/
				App.setUserSesion(new UserSession(empleado));				
				App.setRoot("Main");
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}//end check
	
}
