package TestManager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.dao.Proveedor;
import edu.gestock.persistence.manager.ProveedorManager;
import javafx.collections.ObservableList;

class ProveedorManagerTest {

	@Test
	void testFindAllProveedor() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findAll");

		Connection con = new Conector().getMySQLConnection();
		try {
			ObservableList<Proveedor> proveedor;
			proveedor = new ProveedorManager().findAllProveedor(con);

			assertNotNull(proveedor);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
 
		}
	}

	@Test
	void testFindProveedorByCif() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findid");

		Connection con = new Conector().getMySQLConnection();
		try {

		Proveedor aux = new ProveedorManager().FindProveedorByCif(con, "B49515411");

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
	void testInsertProveedor() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando insert");

		try {

			Proveedor insertado = new Proveedor("B76587","DEC" , "DECATLON", "785987", "decatlon@gmail.com");
			int numero = new ProveedorManager().insertProveedor(con, insertado);
			Proveedor pro = new ProveedorManager().FindProveedorByCif(con, "B76587");
			//assertEquals(insertado, pro); // preguntar por que esta bien
			assumeNotNull(pro);
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
	void testUpdateProveedor() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el update");

		try {
			Proveedor insertado = new Proveedor("B76587","DEC" , "verdecora", "785987", "verdecora@gmail.com");
			int numero = new ProveedorManager().updateProveedor(con, insertado, "B76587");
			Proveedor aux = new ProveedorManager().FindProveedorByCif(con, "B76587");
			
			assertNotNull(aux);
			//assertEquals(insertado, aux);// preguntar por que esta bien
			assertNotEquals(numero, 0);

		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	void testDeleteProveedor() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el delete");

		try {
			
			
			int numero = new ProveedorManager().deleteProveedor(con, "B76587");
			Proveedor aux = new ProveedorManager().FindProveedorByCif(con, "B76587");
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
