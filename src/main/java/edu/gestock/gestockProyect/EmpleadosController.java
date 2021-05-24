package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;


import edu.gestock.persistence.manager.EmpleadoManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EmpleadosController implements Initializable {

	@FXML
	private Button btninsertar;

	@FXML
	private TextField tfuserPassword;

	@FXML
	private Label lbuserPassword;

	@FXML
	private TableColumn<Empleado, String> colpermisos;

	@FXML
	private Label lbapellidos;

	@FXML
	private Label lbfechaAlta;

	@FXML
	private TableColumn<Empleado, String> colapellidos;

	@FXML
	private Label lbnombre;

	@FXML
	private TableView<Empleado> tvempleados;

	@FXML
	private TableColumn<Empleado, Date> colfechaAlta;

	@FXML
	private AnchorPane apright;

	@FXML
	private TableColumn<Empleado, String> coldni;

	@FXML
	private Label lbid;

	@FXML
	private Button btneliminar;

	@FXML
	private TextField tfpermisos;

	@FXML
	private Button btnmodificar;

	@FXML
	private TextField tfnombre;

	@FXML
	private Label lbpermisos;

	@FXML
	private Label lbdni;

	@FXML
	private TableColumn<Empleado, String> colnombre;

	@FXML
	private AnchorPane apcenter;

	@FXML
	private TextField tfapellidos;

	@FXML
	private TextField tfid;

	@FXML
	private TableColumn<Empleado, String> colid;

	@FXML
	private TextField tfdni;

	@FXML
	private DatePicker dpfechaAlta;

	@FXML
	private PasswordField psfuserPassword;

	@FXML
	private ComboBox<String> cbpermisos;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			showEmpleados();
			ObservableList<String> emplelist = FXCollections.observableArrayList();

			emplelist.add("Administrador");
			emplelist.add("Standard");

			cbpermisos.setItems(emplelist);
			cbpermisos.getSelectionModel().selectFirst();
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

	}

	@FXML
	private void handleMouseAction(MouseEvent event) {
		
		Empleado empleadoSeleccionado = tvempleados.getSelectionModel().getSelectedItem();
		tfid.setText(empleadoSeleccionado.getId());
		tfdni.setText(empleadoSeleccionado.getDni());
		tfnombre.setText(empleadoSeleccionado.getNombre());
		tfapellidos.setText(empleadoSeleccionado.getApellidos());

	}

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ObservableList<Empleado> getEmpleadoList() throws ClassNotFoundException, SQLException {

		ObservableList<Empleado> empleadosList = FXCollections.observableArrayList();

		Connection con = new Conector().getMySQLConnection();

		try {

			empleadosList.addAll(new EmpleadoManager().findAllEmployee(con));

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empleadosList;

	}// end

	public void showEmpleados() throws ClassNotFoundException, SQLException {
		ObservableList<Empleado> List = getEmpleadoList();

		colid.setCellValueFactory(new PropertyValueFactory<Empleado, String>("id"));
		coldni.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dni"));
		colnombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
		colapellidos.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidos"));
		colfechaAlta.setCellValueFactory(new PropertyValueFactory<Empleado, Date>("fechaAlta"));
		colpermisos.setCellValueFactory(new PropertyValueFactory<Empleado, String>("permisos"));

		tvempleados.setItems(List);

	}

	public void insertEmpleado() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();

		try {
			Empleado insertado = new Empleado(tfid.getText(), tfdni.getText(), tfnombre.getText(),
					tfapellidos.getText(), psfuserPassword.getText(), Date.valueOf(LocalDate.now()),
					cbpermisos.getValue());
			new EmpleadoManager().insertarEmpleado(con, insertado);
			showEmpleados();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// end

	public void updateEmpleado() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			Empleado insertado = new Empleado(tfid.getText(), tfdni.getText(), tfnombre.getText(),
					tfapellidos.getText(), psfuserPassword.getText(), Date.valueOf(dpfechaAlta.getValue()),
					cbpermisos.getValue());
			new EmpleadoManager().updateEmpleado(con, insertado, tfid.getText());
			showEmpleados();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// end

	public void deleteEmpleado() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			new EmpleadoManager().deleteEmpleado(con, tvempleados.getSelectionModel().getSelectedItem().getId());
			showEmpleados();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// end
	
	@FXML
	public void switchToInicio() throws IOException {
		App.setRoot("Main");
	}

}
