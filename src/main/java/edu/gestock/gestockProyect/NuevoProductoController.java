package edu.gestock.gestockProyect;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.dao.Proveedor;
import edu.gestock.persistence.dao.Subcategoria;
import edu.gestock.persistence.manager.ProductosManager;
import edu.gestock.persistence.manager.ProveedorManager;
import edu.gestock.persistence.manager.SubcategoriaManager;
import edu.gestock.services.Id;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class NuevoProductoController implements Initializable {
	@FXML
	private TextField tfColor;

	@FXML
	private TextField tfPrecio;

	@FXML
	private TextArea tfDescripcion;

	@FXML
	private TextField tfNombre;

	@FXML
	private Button btCargaMasiva;

	@FXML
	private ComboBox<Proveedor> cbProveedor;

	@FXML
	private TextField tfRotura;

	@FXML
	private TextField tfCantidad;

	@FXML
	private TextField tfTalla;

	@FXML
	private Button btCrear;

	@FXML
	private Button btCancelar;

	@FXML
	private ComboBox<Subcategoria> cbSubcategoria;
	
	@FXML
	private Label lbMensaje;
	
	@FXML
	private Label lbMensajeError;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillCombos();
	}

	/**
	 * Hacemos una llamada a la base de datos y metemos los datos de los proveedores
	 * y categorias.
	 */
	public void fillCombos() {
		Connection con = null;

		try {
			con = new Conector().getMySQLConnection();
			ObservableList<Proveedor> proveedorObs = new ProveedorManager().findAllProveedor(con);
			ObservableList<Subcategoria> subcategoriaObs = new SubcategoriaManager().findAllSubcategories(con);

			cbSubcategoria.setItems(subcategoriaObs);
			cbSubcategoria.getSelectionModel().selectFirst();
			cbProveedor.setItems(proveedorObs);
			cbProveedor.getSelectionModel().selectFirst();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void switchToMain() throws IOException {
		App.setRoot("Main");
	}

	@FXML
	void switchToProductos() throws IOException {
		App.setRoot("Productos");
	}
	
	/**
	 * A través de este método se obtienen todos los valores de los textfield para ser subidos a la base de datos.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void crearProducto() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();

		try {

			Producto nuevoProducto = new Producto(Id.generator(7, 15), tfNombre.getText(),
					Double.parseDouble(tfPrecio.getText()), tfTalla.getText(), Integer.parseInt(tfCantidad.getText()),
					tfColor.getText(), cbProveedor.getSelectionModel().getSelectedItem().getCif(),
					cbSubcategoria.getSelectionModel().getSelectedItem().getId(), tfDescripcion.getText(),
					Integer.parseInt(tfRotura.getText()));

			int rowAfected = new ProductosManager().insertProductos(con, nuevoProducto);

			if(rowAfected == 1) {
				lbMensaje.setText("El producto se ha subido correctamente");
				lbMensajeError.setVisible(false);
			} else {
				lbMensajeError.setText("Error al subir");
				lbMensaje.setVisible(false);
			}
			
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//
	
	/**
	 * Borra todos los datos que hay escritos
	 */
	public void resetSelection() {
		tfNombre.setText("");
		tfPrecio.setText("");
		tfTalla.setText("");
		tfCantidad.setText("");
		tfColor.setText("");
		tfDescripcion.setText("");
		tfRotura.setText("");
	}
	
	/**
	 * Recolecta un archivo 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	@FXML
	public void uploadFile() throws ClassNotFoundException, SQLException {
		
		Connection con = new Conector().getMySQLConnection();
		
		try {
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Archivos CSV", "*.csv"));
		File archivo = fc.showOpenDialog(null);
				
		if(archivo != null) {
			String ruta = archivo.getAbsolutePath();
			List<Producto> productos = new ArrayList<>();
			try (Stream<String> streamfile = Files.lines(Paths.get(ruta))){
				productos = streamfile.map(linea -> linea.split(";")).map(atributo -> {
				Producto nuevoProducto = new Producto(atributo[0], atributo[1], Double.parseDouble(atributo[2]), atributo[3], Integer.parseInt(atributo[4]), 
						atributo[5], atributo[6], atributo[7], 
						atributo[8], Integer.parseInt(atributo[9]));
				return nuevoProducto;
				}).collect(Collectors.toList());
				
				productos.forEach(producto -> new ProductosManager().insertProductos(con, producto));
						
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		}//end if
		} finally {
			try {
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}//end upload

}
