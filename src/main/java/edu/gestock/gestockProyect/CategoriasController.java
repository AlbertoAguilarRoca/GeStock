package edu.gestock.gestockProyect;


import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.manager.CategoriaManager;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.gestock.persistence.dao.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CategoriasController implements Initializable{

    @FXML
    private Button btninsertar;

    @FXML
    private TableView<Categoria> tvcategorias;

    @FXML
    private Label lbid;

    @FXML
    private TextField tfnombre;

    @FXML
    private TableColumn<Categoria, String> colnombre;

    @FXML
    private AnchorPane apcenter;

    @FXML
    private Label lbnombre;

    @FXML
    private TextField tfid;

    @FXML
    private TableColumn<Categoria, String> colid;

    @FXML
    private Button btneliminar;

    @FXML
    private Button btnmodificar;

    @FXML
    private AnchorPane apright;

    @FXML
    void handleMouseAction(ActionEvent event) {
    	
    	Categoria categorias = tvcategorias.getSelectionModel().getSelectedItem();

    }

   




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
	public ObservableList<Categoria> getCategoriaList() throws ClassNotFoundException, SQLException {

		ObservableList<Categoria> categorialist = FXCollections.observableArrayList();

		Connection con = new Conector().getMySQLConnection();

		try {

			categorialist.addAll(new CategoriaManager().findAllCategories(con));

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categorialist;

	}// end

	public void showCategorias() throws ClassNotFoundException, SQLException {
		ObservableList<Categoria> List = getCategoriaList();

		colid.setCellValueFactory(new PropertyValueFactory<Categoria, String>("id"));
		colnombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("dni"));
		

		tvcategorias.setItems(List);

	}

	public void insertCategoria() throws ClassNotFoundException, SQLException {

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
