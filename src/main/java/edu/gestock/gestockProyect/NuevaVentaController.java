package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.EsVendido;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.dao.Proveedor;
import edu.gestock.persistence.dao.Venta;
import edu.gestock.persistence.manager.EsVendidoManager;
import edu.gestock.persistence.manager.ProductosManager;
import edu.gestock.persistence.manager.ProveedorManager;
import edu.gestock.persistence.manager.VentasManager;
import edu.gestock.services.Id;
import edu.gestock.services.ListaCompra;
import edu.gestock.services.mail.EmailNotification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;

public class NuevaVentaController implements Initializable {

    @FXML
    private TableView<ListaCompra> tvListaProductos;

    @FXML
    private Button btCalculaCambio;

    @FXML
    private TableColumn<ListaCompra, String> colNombre;

    @FXML
    private Button btDeleteProducto;

    @FXML
    private TextField tfCantidad;

    @FXML
    private TextField tfRefProducto;

    @FXML
    private TextField tfPagoCliente;

    @FXML
    private Label lbCambio;

    @FXML
    private TextField tfDescuento;

    @FXML
    private TableColumn<ListaCompra, String> colTalla;

    @FXML
    private TableColumn<ListaCompra, Double> colPrecio;

    @FXML
    private Label lbTotalImporte;

    @FXML
    private Button btnPagar;

    @FXML
    private TableColumn<ListaCompra, String> colId;

    @FXML
    private TableColumn<ListaCompra, Integer> colCantidad;

    @FXML
    private Button btAddProduct;
    
    @FXML
    private Label lbProductNotFound;
    
    @FXML
    private Button btDescontar;
    
    @FXML
    private Label lbExito;
    
    ObservableList<ListaCompra> listaCompra = FXCollections.observableArrayList();
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {

    	
    	if(tfDescuento.getText().isBlank() || tfDescuento.getText().equals("0")) {
    		btDescontar.disableProperty();
    		btDescontar.setCursor(Cursor.WAIT);
    	}
		
	}
    
    /**
     * Busca en la base de datos la referencia pasada como producto y la mete en una tabla.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void addProductToTable() throws ClassNotFoundException, SQLException {
    	
    	Connection con = new Conector().getMySQLConnection();
    	
    	try {   	

    		Producto productoNuevo = new ProductosManager().findProductosById(con, tfRefProducto.getText().trim());
    		
    		colId.setCellValueFactory(new PropertyValueFactory<ListaCompra, String>("id"));
    		colNombre.setCellValueFactory(new PropertyValueFactory<ListaCompra, String>("nombre"));
    		colTalla.setCellValueFactory(new PropertyValueFactory<ListaCompra, String>("talla"));
    		colCantidad.setCellValueFactory(new PropertyValueFactory<ListaCompra, Integer>("cantidad"));
    		colPrecio.setCellValueFactory(new PropertyValueFactory<ListaCompra, Double>("precio"));
    		    		
    		if(productoNuevo == null) {
    			lbProductNotFound.setVisible(true);
    			lbProductNotFound.setText("Referencia no v?lida. Producto no encontrado.");
    		} else {
    			
    			Integer cantidad = 1;
    			
    			if(!tfCantidad.getText().isBlank()) {
    				cantidad = Integer.parseInt(tfCantidad.getText());
    			}
    			
    			ListaCompra productoLista = new ListaCompra(productoNuevo.getId(), productoNuevo.getNombre(),
        				productoNuevo.getTalla(), productoNuevo.getPrecio(), cantidad);
    			
    			listaCompra.add(productoLista);
    			calculaImporte(listaCompra);
    			tvListaProductos.setItems(listaCompra);
    			lbProductNotFound.setVisible(false);
    			
    		}
    		 		
    	} finally {
    		try {
    			con.close();
    		} catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	
    }//end addproducto

    /**
     * Con este m?todo se gener?n las ventas en la base de datos. Adem?s de controlar el stock y
     * enviar mensajes por correo en caso de 
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void confirmarPago() throws ClassNotFoundException, SQLException, IOException {
    	Connection con = new Conector().getMySQLConnection();
    	
    	try {
    		
    		String idVenta = Id.generator(7, 20);
    		
    		Venta nuevaVenta = new Venta(idVenta, App.getUserSesion().getEmpleado().getId(), 
    				Double.parseDouble(lbTotalImporte.getText()), Date.valueOf(LocalDate.now()));
    		
    		new VentasManager().insertVenta(con, nuevaVenta);
    		
    		listaCompra.forEach(producto -> {
    			new EsVendidoManager().insertEsVendido(con, new EsVendido(producto.getId(), idVenta, producto.getCantidad()));
    			//Borrar cantidad de producto de la bd
    			new ProductosManager().reduceStock(con, producto.getId(), producto.getCantidad());
    			
    			notificaRoturaStock(con, producto.getId());
    		});
    		
    		Alert alerta = new Alert(AlertType.INFORMATION);
    		alerta.setTitle("Informaci?n:");
    		alerta.setHeaderText("La venta se ha realizado con ?xito.");
    	
    		alerta.showAndWait();
    		
    		App.setRoot("Main");
    		
    	} finally {
    		try {
    			con.close();
    		} catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	
    }
    
    /**
     * Funci?n para calcular el importe total de la compra. Si le incluye el descuento, que si tiene
     * contenido distinto de cero lo aplicar? al importe.
     * @param lista
     */
    public void calculaImporte(ObservableList<ListaCompra> lista) {
    	DecimalFormatSymbols punto = new DecimalFormatSymbols();
		punto.setDecimalSeparator('.');
		DecimalFormat f = new DecimalFormat("#.00", punto);
    	double importeTotal = 0.00;
    	if(lista.size() == 0) {
    		lbTotalImporte.setText(""+0.00);
    	} else {
    		for(ListaCompra producto:lista) {
    			importeTotal = importeTotal + producto.getCantidad() * producto.getPrecio();
    		}
    				
    		lbTotalImporte.setText(f.format(importeTotal));   		
    	}
    }
    
    /**
     * Aplica un descuento al importe total
     */
    public void descontarImporte() {
    	DecimalFormatSymbols punto = new DecimalFormatSymbols();
		punto.setDecimalSeparator('.');
		DecimalFormat f = new DecimalFormat("#.00", punto);
    	if(tfDescuento.getText().isBlank() || tfDescuento.getText().equals("0")) {
    		btDescontar.disableProperty();
    		btDescontar.setCursor(Cursor.WAIT);
    	} else {
    		Double descuento = Double.parseDouble(tfDescuento.getText()) / 100;
    		Double importeDesconsatar = Double.parseDouble(lbTotalImporte.getText())*descuento;
    		Double importe = Double.parseDouble(lbTotalImporte.getText()) - importeDesconsatar;
    		lbTotalImporte.setText(f.format(importe));
    		btDescontar.setCursor(Cursor.DEFAULT);
    	}
    }

    /**
     * Calcula el cambio ha devolver al cliente.
     */
    @FXML
    void calcularCambio() {
    	Double cambio = Double.parseDouble(tfPagoCliente.getText()) - Double.parseDouble(lbTotalImporte.getText());
    	
    	DecimalFormatSymbols punto = new DecimalFormatSymbols();
		punto.setDecimalSeparator('.');
		DecimalFormat f = new DecimalFormat("#.00", punto);
    	
    	lbCambio.setText(f.format(cambio));    	
    }

    /**
     * Borra un elemento de la lista de productos.
     */
	public void borrarElemento() {
		int indiceProducto = tvListaProductos.getSelectionModel().getSelectedIndex();
		
		listaCompra.remove(indiceProducto);
		calculaImporte(listaCompra);
	}
	
	/**
	 * Funci?n para notificar las roturas de stock por correo
	 * @param con
	 * @param idProducto
	 */
	public void notificaRoturaStock(Connection con, String idProducto) {
		//Creamos un nuevo prod
		Producto producto = new ProductosManager().findProductosById(con, idProducto);	
		Integer diferencia = producto.getCantidad() - producto.getRoturaStock();
		Proveedor proveedor = new ProveedorManager().FindProveedorByCif(con, producto.getIdProveedor());
		
		//Si la diferencia es 0 o negativa se envia una notificaci?n por correo
		if(diferencia == 0 || diferencia < 0) {
			String subject = "Aviso de Rotura de Stock. Producto: "+producto.getId();
			String contenido = "<h1>Aviso de Rotura de Stock</h1>El producto: <b>"+producto.getNombre()+"</b> est? bajo m?nimos de stock. <b>Se recomienda "
					+ "solicitar un reabasticimiento del producto lo antes posible.<br>Qued?n "+producto.getCantidad()+" productos en el almac?n."
					+ "<h2>Datos del proveedor</h2>"
					+ "<ul><li>Nombre: "+proveedor.getNombre()+"</li>"
							+ "<li>CIF: "+proveedor.getCif()+"</li>"
									+ "<li>Email: "+proveedor.getEmail()+"</li>"
											+ "<li> Tel?fono: "+proveedor.getTelefono()+"</li></ul>";
			new EmailNotification().send("gestorstockapp@gmail.com", "gestorstockapp@gmail.com", subject, contenido);
		}
	}

}
