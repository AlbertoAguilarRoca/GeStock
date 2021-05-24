package TestManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Categoria;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.manager.ProductosManager;
import javafx.collections.ObservableList;

class ProductosManagerTest {

	@Test
	void testFindAllProductos() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		try {
			ObservableList<Producto> producto;
			producto = new ProductosManager().findAllProductos(con);

			assertNotNull(producto);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
 
		}
	}

	@Test
	void testFindProductosById() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		try {

		Producto aux = new ProductosManager().findProductosById(con, "hyh");

		assertNotNull(aux);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testCountRowProducts() throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		try {

		int numero = new ProductosManager().countRowProducts(con);

		assertNotEquals(numero, 0);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testInsertProductos() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando insert");

		try {

			Producto insertado = new Producto("hyh", "zapatos", 29.85, "xl",7 , "Verde", "B49515411", "gJFAAD", "Son la caña", 8);
			int numero = new ProductosManager().insertProductos(con, insertado);
			Producto prod = new ProductosManager().findProductosById(con, "hyh");
			assertEquals(insertado, prod); // preguntar por que esta bien
			assumeNotNull(prod);
			assertNotEquals(numero, 0);

		} finally {

			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testUpdateProductos() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			Producto insertado = new Producto("hyh", "Guantes de Boxeo", 29.85, "xl",7 , "ROJOS", "B49515411", "gJFAAD", "Son la caña", 8);
			int numero = new ProductosManager().updateProductos(con, insertado, "hyh");
			Producto aux = new ProductosManager().findProductosById(con, "hyh");
			
			assertNotNull(aux);
			assertEquals(insertado, aux);
			assertNotEquals(numero, 0);

		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	void testDeleteProductos() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();

		try {
			
			
			int numero = new ProductosManager().deleteProductos(con, "hyh");
			Producto aux = new ProductosManager().findProductosById(con, "hyh");
			assertNull(null, aux);
			assertNotEquals(numero, 0);

		} finally {
			try {
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
