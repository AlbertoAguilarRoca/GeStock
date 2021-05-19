package edu.gestock.gestockProyect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.manager.EmpleadoManager;
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
	
	@FXML
	public void checkUserLog() throws ClassNotFoundException, SQLException, IOException {
		
		Connection con = new Conector().getMySQLConnection();
		
		try {
			
			int contador = new EmpleadoManager().checkUserLogin(con, fdId.getText(), fdPassword.getText());
			if(fdId.getText().isEmpty() || fdPassword.getText().isEmpty()) {
				lbError.setText("Uno de los campos est� vac�o.");
			} else if(contador == 0) {
				lbError.setText("El Usuario/Contrase�a introducido es incorrecto");
			} else {
				App.setRoot("primary");
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
