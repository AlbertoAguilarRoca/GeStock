package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.dao.Venta;
import edu.gestock.persistence.manager.EsVendidoManager;
import edu.gestock.persistence.manager.VentasManager;
import edu.gestock.services.ListaCompra;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class RegistroVentasController implements Initializable {

	@FXML
	private TableColumn<Venta, Date> colFecha;

	@FXML
	private Button btInicio;

	@FXML
	private TableColumn<Venta, String> colNVenta;

	@FXML
	private TableView<Venta> tvVentas;

	@FXML
	private TableColumn<Venta, Double> colImporte;

	@FXML
	private TableColumn<Venta, String> colEmpleado;

	@FXML
	private Button btInformes;
	
	@FXML
	private TableView<ListaCompra> tvProducto;
	
	@FXML
	private TableColumn<ListaCompra, String> colRef;
	
	@FXML
	private TableColumn<ListaCompra, String> colNombre;
	
	@FXML
	private TableColumn<ListaCompra, String> colTalla;
	
	@FXML
	private TableColumn<ListaCompra, Integer> colCantidad;
	
	@FXML
	private TableColumn<ListaCompra, Double> colPrecio;
	
	@FXML
	private TextField tfBuscarVenta;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			mostrarVentas();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Muestra una tabla con todos las ventas que ha habido
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void mostrarVentas() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			ObservableList<Venta> totalVentas = new VentasManager().findAllVenta(con);

			colNVenta.setCellValueFactory(new PropertyValueFactory<Venta, String>("nVenta"));
			colEmpleado.setCellValueFactory(new PropertyValueFactory<Venta, String>("idEmpleado"));
			colImporte.setCellValueFactory(new PropertyValueFactory<Venta, Double>("importe"));
			colFecha.setCellValueFactory(new PropertyValueFactory<Venta, Date>("fechaVenta"));
			
			FilteredList<Venta> ventasFiltrada = new FilteredList<>(totalVentas, b -> true);			
			tfBuscarVenta.textProperty().addListener((observable, oldValue, newValue) -> {
				ventasFiltrada.setPredicate(Venta -> {
					//Si el buscador está vacio, muestra todo
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}					
					//Ponemos el texto del buscador en minuscula
					String ventaBuscar = newValue.toLowerCase();
					
					if(Venta.getNVenta().toLowerCase().indexOf(ventaBuscar) != -1) {
						return true; 
					} else if(Venta.getFechaVenta().toString().indexOf(ventaBuscar) != -1) {
						return true;
					} else {
						return false;
					}
				});
			});
			
			SortedList<Venta> sortedVentas = new SortedList<>(ventasFiltrada);
			sortedVentas.comparatorProperty().bind(tvVentas.comparatorProperty());
			tvVentas.setItems(sortedVentas);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//end
	
	@FXML
	private void showListaProducto(MouseEvent event) throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		
		try {
			
			ObservableList<ListaCompra> listaProductosComprados = new EsVendidoManager().productosEnUnaVenta(con, 
					tvVentas.getSelectionModel().getSelectedItem().getNVenta());
			
			colRef.setCellValueFactory(new PropertyValueFactory<ListaCompra, String>("id"));
			colNombre.setCellValueFactory(new PropertyValueFactory<ListaCompra, String>("nombre"));
			colTalla.setCellValueFactory(new PropertyValueFactory<ListaCompra, String>("talla"));
			colCantidad.setCellValueFactory(new PropertyValueFactory<ListaCompra, Integer>("cantidad"));
			colPrecio.setCellValueFactory(new PropertyValueFactory<ListaCompra, Double>("precio"));
			
			tvProducto.setItems(listaProductosComprados);
			
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void switchToInicio() throws IOException {
		App.setRoot("Main");
	}

}
