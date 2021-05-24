package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;

import edu.gestock.persistence.dao.Subcategoria;

import edu.gestock.persistence.manager.SubcategoriaManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SubcategoriasController implements Initializable {

	@FXML
	private Button btninsertar;

	@FXML
	private Label lbid;

	@FXML
	private TableColumn<Subcategoria, String> colidcategoria;

	@FXML
	private Button btneliminar;

	@FXML
	private Button btnmodificar;

	@FXML
	private Label lbcategoria;

	@FXML
	private TextField tfnombre;

	@FXML
	private TableColumn<Subcategoria, String> colnombre;

	@FXML
	private TableView<Subcategoria> tvsubcategorias;

	@FXML
	private AnchorPane apcenter;

	@FXML
	private Label lbnombre;

	@FXML
	private TextField tfid;

	@FXML
	private TableColumn<Subcategoria, String> colid;

	@FXML
	private AnchorPane apright;

	@FXML
	private TextField tfidcategoria;

	@FXML
	void handleMousesubca(MouseEvent event) {

		Subcategoria subcategoria = tvsubcategorias.getSelectionModel().getSelectedItem();

		tfid.setText(subcategoria.getId());
		tfnombre.setText(subcategoria.getNombre());
		tfidcategoria.setText(subcategoria.getIdcategoria());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			showSubcategorias();

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ObservableList<Subcategoria> getSubcategoriaList() throws ClassNotFoundException, SQLException {

		ObservableList<Subcategoria> categorialist = FXCollections.observableArrayList();

		Connection con = new Conector().getMySQLConnection();

		try {

			categorialist.addAll(new SubcategoriaManager().findAllSubcategories(con));

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categorialist;

	}// end

	public void showSubcategorias() throws ClassNotFoundException, SQLException {
		ObservableList<Subcategoria> List = getSubcategoriaList();

		colid.setCellValueFactory(new PropertyValueFactory<Subcategoria, String>("id"));
		colnombre.setCellValueFactory(new PropertyValueFactory<Subcategoria, String>("nombre"));
		colidcategoria.setCellValueFactory(new PropertyValueFactory<Subcategoria, String>("idcategoria"));
		tvsubcategorias.setItems(List);

	}

	public void insertSubcategoria() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();

		try {
			Subcategoria insertada = new Subcategoria(tfid.getText(), tfnombre.getText(), tfidcategoria.getText());
			new SubcategoriaManager().insertNewSubcategory(con, insertada);

			showSubcategorias();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// end

	public void updateSubcategoria() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			Subcategoria insertada = new Subcategoria(tfid.getText(), tfnombre.getText(), tfidcategoria.getText());
			new SubcategoriaManager().updateSubcategoryByID(con, insertada, tfid.getText());

			showSubcategorias();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// end

	public void deleteSubcategoria() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			new SubcategoriaManager().deleteSubcategoryByID(con,
					tvsubcategorias.getSelectionModel().getSelectedItem().getId());

			showSubcategorias();
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