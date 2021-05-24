package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.SQLException;

import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;

import edu.gestock.persistence.manager.CategoriaManager;

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

import edu.gestock.persistence.dao.Categoria;

public class CategoriasController implements Initializable {

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
	void handleMousetable(MouseEvent event) {

		Categoria categorias = tvcategorias.getSelectionModel().getSelectedItem();

		tfid.setText(categorias.getId());
		tfnombre.setText(categorias.getNombre());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			showCategorias();

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
		colnombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombre"));

		tvcategorias.setItems(List);

	}

	public void insertCategoria() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();

		try {
			Categoria insertada = new Categoria(tfid.getText(), tfnombre.getText());
			new CategoriaManager().insertNewCategory(con, insertada);

			showCategorias();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// end

	public void updateCategoria() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			Categoria insertado = new Categoria(tfid.getText(), tfnombre.getText());
			new CategoriaManager().updateCategoryByID(con, insertado, tfid.getText());

			showCategorias();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// end

	public void deleteCategoria() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			new CategoriaManager().deleteCategoryByID(con, tvcategorias.getSelectionModel().getSelectedItem().getId());

			showCategorias();
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