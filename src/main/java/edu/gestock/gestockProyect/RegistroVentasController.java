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
import edu.gestock.persistence.manager.VentasManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistroVentasController implements Initializable {

	  @FXML
	    private TableColumn<Venta, Date> colFecha;

	    @FXML
	    private ListView<Producto> listaCompra;

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
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			
	    	try {
				mostrarVentas();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
	    
	    public void mostrarVentas() throws ClassNotFoundException, SQLException {
	    	Connection con = new Conector().getMySQLConnection();
	    	
	    	try {
	    		ObservableList<Venta> totalVentas = new VentasManager().findAllVenta(con);
	    		
	    		colNVenta.setCellValueFactory(new PropertyValueFactory<Venta, String>("nVenta"));
	    		colEmpleado.setCellValueFactory(new PropertyValueFactory<Venta, String>("idEmpleado"));
	    		colImporte.setCellValueFactory(new PropertyValueFactory<Venta, Double>("importe"));
	    		colFecha.setCellValueFactory(new PropertyValueFactory<Venta, Date>("fechaVenta"));
	    		
	    		tvVentas.setItems(totalVentas);
	    		
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

	    @FXML
	    void switchToInformes() {
	    	
	    }

		
	
}
