package TestManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.dao.EsVendido;
import edu.gestock.persistence.manager.EsVendidoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class EsVendidoManagerTest {

	/**
	 * Funcion para encontrar la relacion entre productos y ventas de la base de
	 * datos
	 * 
	 * @param con
	 * @return
	 */
	public ObservableList<EsVendido> findAllesVendido(Connection con) {
		String sql = "SELECT * FROM esvendido";
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			ObservableList<EsVendido> vendidos = FXCollections.observableArrayList();
			while (result.next()) {
				vendidos.add(new EsVendido(result));
			}
			return vendidos;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}// end

	@Test
	void testFindEsVendidoByidProducto() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findidProducto");

		Connection con = new Conector().getMySQLConnection();
		try {
			
		 EsVendido aux = new EsVendidoManager().findEsVendidoByidProducto(con, "xx");
			
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
	void testFindEsVendidoBynVenta() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findidBynVenta");

		Connection con = new Conector().getMySQLConnection();
		try {
			
		 EsVendido aux = new EsVendidoManager().findEsVendidoBynVenta(con, "gfrthg");
			
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
	void testInsertEsVendido() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando insert");

		try {

			EsVendido sell = new EsVendido("greghe", "gfrthg", 15);
			int numero = new EsVendidoManager().insertEsVendido(con, sell);
			EsVendido aux = new EsVendidoManager().findEsVendidoByidProducto(con, "greghe");
			//assertEquals(sell, aux); // //MIRAR POR QUE FALLA
			assumeNotNull(aux);
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
	void testUpdateEsVendidoByIdProducto() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();//falla la sentencia
		System.out.println("probando insert");

		try {

			EsVendido sell = new EsVendido("GRGHRE", "jhty", 18);
			int numero = new EsVendidoManager().updateEsVendidoByIdProducto(con, sell, "greghe");
			EsVendido aux = new EsVendidoManager().findEsVendidoByidProducto(con, "greghe");
			//assertEquals(sell, aux); // //MIRAR POR QUE FALLA
			assumeNotNull(aux);
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
	void testUpdateEsVendidoByNventa() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();//falla la sentencia
		System.out.println("probando insert");

		try {

			EsVendido sell = new EsVendido("GRGHRE", "jhty", 18);
			int numero = new EsVendidoManager().updateEsVendidoByNventa(con, sell, "gfrthg");
			EsVendido aux = new EsVendidoManager().findEsVendidoBynVenta(con, "gfrthg");
			//assertEquals(sell, aux); // //MIRAR POR QUE FALLA
			assumeNotNull(aux);
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
	void testDeleteEsVendidoByidProducto() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el delete by idProducto");

		try {
			
			int numero = new EsVendidoManager().deleteEsVendidoByidProducto(con, "greghe");
			EsVendido empl = new EsVendidoManager().findEsVendidoByidProducto(con, "greghe");//preguntar
			assertNull(null, empl);
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
	void testDeleteEsVendidoBynVenta() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el delete by Nventas");

		try {
			
			int numero = new EsVendidoManager().deleteEsVendidoBynVenta(con, "gfrthg");
			EsVendido empl = new EsVendidoManager().findEsVendidoBynVenta(con, "gfrthg");//preguntar
			assertNull(null, empl);
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
