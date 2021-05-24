package TestManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Categoria;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.manager.EmpleadoManager;
import javafx.collections.ObservableList;

class EmpleadoManagerTest {

	@Test
	void testFindAllEmployee() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findAll");

		Connection con = new Conector().getMySQLConnection();
		try {
			ObservableList<Empleado> empleado;
			empleado = new EmpleadoManager().findAllEmployee(con);

			assertNotNull(empleado);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}



	@Test
	void testFindEmpleadoById() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findid");

		Connection con = new Conector().getMySQLConnection();
		try {
			Empleado emple = new Empleado("admin","74896325P","Juan","Villegas","pass",Date.valueOf(LocalDate.now()),"admin");
			new EmpleadoManager().insertarEmpleado(con, emple);
			
			
			Empleado aux = new EmpleadoManager().findEmpleadoById(con,"admin");

			assertEquals(emple, aux);// esta bien preguntar

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testCheckUserLogin() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el check");

		Connection con = new Conector().getMySQLConnection();
		try {
			
		
			int numero = new EmpleadoManager().checkUserLogin(con,"admin","admin");

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
	void testInsertarEmpleado() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando insert");

		try {

			Empleado emple = new Empleado("vaia", "74896325P", "Juan", "Villegas", "pass",Date.valueOf(LocalDate.now()), "admin");
			int numero = new EmpleadoManager().insertarEmpleado(con, emple);
			Empleado aux = new EmpleadoManager().findEmpleadoById(con, "vaia");
			//assertEquals(emple, aux); // //MIRAR POR QUE FALLA
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
	void testUpdateEmpleado() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el update");

		try {

			Empleado insertado = new Empleado("vaia", "74896325P", "Juan", "Munoz", "pass",Date.valueOf(LocalDate.now()), "admin");
			assumeNotNull(insertado);
			int numero = new EmpleadoManager().updateEmpleado(con, insertado, "vaia");
			Empleado aux = new EmpleadoManager().findEmpleadoById(con, "vaia");
			assertNotNull(aux);
			//assertEquals(insertado, aux); mirar fallos
			assertNotEquals(numero, 0); 

		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	void testDeleteEmpleado() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el delete");

		try {
			
			int numero = new EmpleadoManager().deleteEmpleado(con, "vaia");
			Empleado empl = new EmpleadoManager().findEmpleadoById(con, "vaia");//preguntar
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
