package edu.gestock.gestockProyect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.dao.Proveedor;
import edu.gestock.persistence.manager.ProductosManager;
import edu.gestock.persistence.manager.ProveedorManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	@FXML
	private Label pvNombre;
	@FXML
	private Label pvEmail;
	@FXML
	private Label pvTelefono;
	
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
					
			filterTable(productos);

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// end

	/**
	 * Permite al usuario hacer un filtrado mediante un textfield de búsqueda.
	 * @param productos
	 */
	private void filterTable(ObservableList<Producto> productos) {
		
		//Metemos la lista observable en una lista filtrada
		FilteredList<Producto> listaFiltrada = new FilteredList<>(productos, b -> true);
		//Añadimos un listener al textfield del buscador
		tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
			listaFiltrada.setPredicate(Producto -> {
				//Si el buscador está vacio, muestra todo
				if(newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				//Ponemos el texto del buscador en minuscula
				String textoBuscar = newValue.toLowerCase();
				
				if(Producto.getId().toLowerCase().indexOf(textoBuscar) != -1) {
					return true; //encuentra el id
				} else if(Producto.getNombre().toLowerCase().indexOf(textoBuscar) != -1) {
					return true; //encuentra el nombre
				} else if(Producto.getTalla().toLowerCase().indexOf(textoBuscar) != -1) {
					return true; //encuentra la talla
				} else if(String.valueOf(Producto.getPrecio()).indexOf(textoBuscar) != -1) {
					return true; //encuentra el precio
				} else if(Producto.getColor().toLowerCase().indexOf(textoBuscar) != -1) {
					return true; //encuentra el color
				} else if(Producto.getIdProveedor().toLowerCase().indexOf(textoBuscar) != -1) {
					return true; //encuentra el proveedor
				} else if(Producto.getIdSubcategoria().toLowerCase().indexOf(textoBuscar) != -1) {
					return true; //encuentra el categoria
				} else if(Producto.getDescripcion().toLowerCase().indexOf(textoBuscar) != -1) {
					return true; //encuentra el descripcion
				} else {
					return false; //no encuentra nada
				}					
			});
		});
		
		//Pasamos la lista filtrada a una sorted list
		SortedList<Producto> sortedProducts = new SortedList<>(listaFiltrada);
		
		//Unimos el comparador de la sortedlist con el comparador del tableview.
		//en caso de no haber datos, el sortedlist comparator no tendrá efecto
		sortedProducts.comparatorProperty().bind(tvProductos.comparatorProperty());
		
		/* Enlazamos la lista con la tabla */
		tvProductos.setItems(sortedProducts);
	}

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
	
	@FXML
	public void mostrarProveedor() throws IOException, ClassNotFoundException, SQLException {
		
		Connection con = new Conector().getMySQLConnection();
		
		try {		
			Proveedor proveedor = new ProveedorManager().FindProveedorByCif(con, tfProveedor.getText());
			Text nombre = new Text(proveedor.getNombre());
			Text email = new Text(proveedor.getEmail());
			Text telefono = new Text(proveedor.getTelefono());
			Text inputNombre = new Text("Nombre:");
			Text inputEmail = new Text("Email:");
			Text inputTelefono = new Text("Teléfono:");
			Text cabecera = new Text("Información de Contacto:");
			
			inputNombre.setX(24);
			inputNombre.setY(67);
			
			inputEmail.setX(24);
			inputEmail.setY(103);
			
			inputTelefono.setX(24);
			inputTelefono.setY(140);
			
			cabecera.setX(24);
			cabecera.setY(25);
			cabecera.setFont(Font.font("System Bold", 20));
			
			nombre.setX(101);
			nombre.setY(67);
			
			email.setX(101);
			email.setY(103);
			
			telefono.setX(101);
			telefono.setY(140);
			
			Group grupo = new Group(nombre, email, telefono, inputNombre, inputEmail, inputTelefono, cabecera);
			
			Scene scene = new Scene(grupo, 400, 189);
			Stage stage = new Stage();
			stage.setTitle("GeStock");
			stage.setScene(scene);
			
			stage.show();
			
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
	}//end mostrarproveedor

}
