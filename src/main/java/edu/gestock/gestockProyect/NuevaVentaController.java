package edu.gestock.gestockProyect;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.manager.ProductosManager;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class NuevaVentaController implements Initializable {

	@FXML
	private Label lbRefProducto;
	@FXML
	private TextField tfRefProducto;
	@FXML
	private TextField tfCantidad;
	@FXML
	private Label lbCantidad;
	@FXML
	private Label lbImporte;
	@FXML
	private Label lbPagar;
	@FXML
	private Button btnPagar;
	@FXML
	private TextField tfImporte;
	@FXML
	private Label lbAñadir;
	@FXML
	private Button btnAñadir;
	@FXML
	private TableView<Producto> tvProductos;
	@FXML
	private TableColumn<Producto, String> colRefProducto;
	@FXML
	private TableColumn<Producto, String> colNombre;
	@FXML
	private TableColumn<Producto, Double> colPrecio;
	@FXML
	private TableColumn<Producto, Integer> colCantidad;

	@FXML
	private void handlebutton(ActionEvent event) {
		if (event.getSource() == btnAñadir) {

		} else if (event.getSource() == btnPagar) {

		}
	}// end button

	@FXML
	public void add() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		try {

			Producto productoAdd = new ProductosManager().findProductosById(con, tfRefProducto.getText());
			HashMap<Producto, Integer> listaCompra = new HashMap<>();

			listaCompra.put(productoAdd, Integer.parseInt(tfCantidad.getText()));
			colRefProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("id"));
			colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
			colPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));
			colCantidad.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidad"));

			tvProductos.setItems(getproductosList()); // meterle el observable list
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public ObservableList<Producto> getproductosList() throws ClassNotFoundException, SQLException {
		ObservableList<Producto> producto = FXCollections.observableArrayList();

		Connection con = new Conector().getMySQLConnection();
		try {
			producto.addAll(new ProductosManager().findAllProductos(con));
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return producto;

	}

	@FXML
	public void showProductos() throws ClassNotFoundException, SQLException {
		ObservableList<Producto> list = getproductosList();
		Connection con = new Conector().getMySQLConnection();
		try {

			colRefProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("id"));
			colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
			colPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));
			colCantidad.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidad"));

			tvProductos.setItems(list);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		try {
			showProductos();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
