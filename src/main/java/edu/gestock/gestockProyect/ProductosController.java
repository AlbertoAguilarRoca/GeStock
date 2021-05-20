package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.manager.ProductosManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductosController implements Initializable {

	@FXML
	private TextField tfId;
	@FXML
	private TextField tfSubcategoria;
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfTalla;
	@FXML
	private TextField tfColor;
	@FXML
	private TextField tfPrecio;
	@FXML
	private TextArea tfDescripcion;
	@FXML
	private TextField tfRotura;
	@FXML
	private TextField tfCantidad;
	@FXML
	private TextField tfProveedor;
	@FXML
	private Button btModificar;
	@FXML
	private Button btBorrar;
	@FXML
	private Button btProveedor;
	@FXML
	private TextField tfBuscar;
	@FXML
	private TableView<Producto> tvProductos;
	@FXML
	private TableColumn<Producto, String> colId;
	@FXML
	private TableColumn<Producto, String> colNombre;
	@FXML
	private TableColumn<Producto, String> colTalla;
	@FXML
	private TableColumn<Producto, Double> colPrecio;
	@FXML
	private TableColumn<Producto, String> colColor;
	@FXML
	private TableColumn<Producto, String> colSubcategoria;
	@FXML
	private TableColumn<Producto, Integer> colCantidad;
	@FXML
	private TableColumn<Producto, Integer> colRotura;
	
	public void switchToMain() throws IOException {
		App.setRoot("Main");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			mostrarProductos();
			mostrarPrimerProducto();		

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}//

	/**
	 * Funcion para mostrar todos los productos que hay en la base de datos
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void mostrarProductos() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();

		try {
			ObservableList<Producto> productos = new ProductosManager().findAllProductos(con);

			colId.setCellValueFactory(new PropertyValueFactory<Producto, String>("id"));
			colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
			colTalla.setCellValueFactory(new PropertyValueFactory<Producto, String>("talla"));
			colPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));
			colColor.setCellValueFactory(new PropertyValueFactory<Producto, String>("color"));
			colSubcategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcion"));
			colCantidad.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidad"));
			colRotura.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("roturaStock"));
			
			/* Enlazamos la lista con la tabla */
			tvProductos.setItems(productos);

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// end

	/**
	 * Por defecto muestra la primera fila de resultados
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void mostrarPrimerProducto() throws ClassNotFoundException, SQLException {
		
		Connection con = new Conector().getMySQLConnection();

		try {
			ObservableList<Producto> productos = new ProductosManager().findAllProductos(con);

			Producto primerProducto = productos.get(0);
			tfTalla.setText(primerProducto.getTalla());
			tfSubcategoria.setText(primerProducto.getIdSubcategoria());
			tfRotura.setText("" + primerProducto.getRoturaStock());
			tfPrecio.setText("" + primerProducto.getPrecio());
			tfNombre.setText(primerProducto.getNombre());
			tfId.setText(primerProducto.getId());
			tfDescripcion.setText(primerProducto.getDescripcion());
			tfColor.setText(primerProducto.getColor());
			tfCantidad.setText("" + primerProducto.getCantidad());
			tfProveedor.setText(primerProducto.getIdProveedor());


		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// end

	/**
	 * Evento para seleccionar los datos de la tabla
	 * @param event
	 */
	@FXML
	private void handleMouseAction(MouseEvent event) {
		Producto productoSeleccionado = tvProductos.getSelectionModel().getSelectedItem();
		tfTalla.setText(productoSeleccionado.getTalla());
		tfSubcategoria.setText(productoSeleccionado.getIdSubcategoria());
		tfRotura.setText("" + productoSeleccionado.getRoturaStock());
		tfPrecio.setText("" + productoSeleccionado.getPrecio());
		tfNombre.setText(productoSeleccionado.getNombre());
		tfId.setText(productoSeleccionado.getId());
		tfDescripcion.setText(productoSeleccionado.getDescripcion());
		tfColor.setText(productoSeleccionado.getColor());
		tfCantidad.setText("" + productoSeleccionado.getCantidad());
		tfProveedor.setText(productoSeleccionado.getIdProveedor());
	}
	
	/**
	 * Actualizar un producto en específico
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateRecord() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			
			Producto productoAntiguo = tvProductos.getSelectionModel().getSelectedItem();
			
			Double precio = Double.parseDouble(tfPrecio.getText());
			Integer cantidad = Integer.parseInt(tfCantidad.getText());
			Integer rotura = Integer.parseInt(tfRotura.getText());
			Producto productoModificado = new Producto(tfId.getText(), tfNombre.getText(), precio, 
					tfTalla.getText(), cantidad , tfColor.getText(), tfProveedor.getText(), tfSubcategoria.getText(), tfDescripcion.getText(), rotura);
			new ProductosManager().updateProductos(con, productoModificado , productoAntiguo.getId());
			mostrarProductos();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//
	
	public void deleteRecord() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			
			Producto productoAntiguo = tvProductos.getSelectionModel().getSelectedItem();
			
			new ProductosManager().deleteProductos(con, productoAntiguo.getId());
			
			mostrarProductos();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//

}
