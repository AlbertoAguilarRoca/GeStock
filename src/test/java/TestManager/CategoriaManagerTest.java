package TestManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Categoria;
import edu.gestock.persistence.manager.CategoriaManager;
import javafx.collections.ObservableList;

class CategoriaManagerTest {
	
	@BeforeAll
	public static void comienzo() {
		System.out.println("COMIENZAN TODOS LOS TEST");
	}

	@Test
	void testFindAllCategories() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findAll");

		Connection con = new Conector().getMySQLConnection();
		try {
			ObservableList<Categoria> categoria;
			categoria = new CategoriaManager().findAllCategories(con);

			assertNotNull(categoria);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	@Test
	void testfindCategoriesid() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findid");

		Connection con = new Conector().getMySQLConnection();
		try {
			Categoria categoria = new Categoria("ESC", "equipo");
			new CategoriaManager().insertNewCategory(con, categoria);
			
			
		Categoria aux = new CategoriaManager().findCategoriesid(con, "ESC");

			assertEquals(categoria, aux);// esta bien preguntar

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
 
	}
	

	@Test
	void testInsertNewCategory() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando insert");

		try {

			Categoria insertada = new Categoria("WAT", "pelota waterpolo");
			int numero = new CategoriaManager().insertNewCategory(con, insertada);
			Categoria categoria = new CategoriaManager().findCategoriesid(con, "WAT");
			assertEquals(categoria, insertada); // preguntar
			assumeNotNull(categoria);
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
	void testUpdateCategoryByID() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el update");

		try {

			Categoria insertada = new Categoria("NAV", "piraña");
			assumeNotNull(insertada);
			int numero = new CategoriaManager().updateCategoryByID(con, insertada, "MON");
			Categoria categoria = new CategoriaManager().findCategoriesid(con, "NAV");
			assertNotNull(categoria);
			assertEquals(categoria, insertada);
			assertNotEquals(numero, 0);

		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	void testDeleteCategoryByID() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el delete");

		try {
			Categoria borrar = new Categoria("WAT", "agua");
			assumeNotNull(borrar);
			int numero = new CategoriaManager().deleteCategoryByID(con, "WAT");
			Categoria categori = new CategoriaManager().findCategoriesid(con, "WAT");//preguntar
			assertNull(null, categori);
			assertNotEquals(numero, 0);

		} finally {
			try {
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	@AfterAll
	public static void finish() {
		System.out.println("FINAL DE TODOS LOS TEST");
	}

}
