package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PerfilController implements Initializable {

    @FXML
    private Label lbFecha;

    @FXML
    private Label lbId;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbDni;

    @FXML
    private Label lbPermiso;

    @FXML
    private Label lbApellidos;
    
    @FXML
    private Button btEditar;

    @FXML
    void switchToInicio() throws IOException {
    	App.setRoot("Main");
    }

    @FXML
    void switchToEmpleado() throws IOException {
    	App.setRoot("Empleados");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		 lbFecha.setText(""+App.getUserSesion().getEmpleado().getFechaAlta());
		 lbId.setText(App.getUserSesion().getEmpleado().getId());
		 lbNombre.setText(App.getUserSesion().getEmpleado().getNombre());
		 lbDni.setText(App.getUserSesion().getEmpleado().getDni());
		 lbPermiso.setText(App.getUserSesion().getEmpleado().getPermisos());
		 lbApellidos.setText(App.getUserSesion().getEmpleado().getApellidos());
		 
		 if(App.getUserSesion().getEmpleado().getPermisos().equals("Standard")) {
			 btEditar.setVisible(false);
		 }
	}

}
