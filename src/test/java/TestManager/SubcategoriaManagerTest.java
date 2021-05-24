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
import edu.gestock.persistence.dao.Subcategoria;
import edu.gestock.persistence.manager.SubcategoriaManager;
import javafx.collections.ObservableList;

class SubcategoriaManagerTest {

	@Test
	void testFindAllSubcategories() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findAll");

		Connection con = new Conector().getMySQLConnection();
		try {
			ObservableList<Subcategoria> subcategoria;
			subcategoria = new SubcategoriaManager().findAllSubcategories(con);

			assertNotNull(subcategoria);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testFindSudcategoriaById() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findid");

		Connection con = new Conector().getMySQLConnection();
		try {
			Subcategoria subcategoria = new Subcategoria("UYRRR","equipo","GYM");
			new SubcategoriaManager().insertNewSubcategory(con, subcategoria);
			
			
		Subcategoria aux = new SubcategoriaManager().findSudcategoriaById(con, "UYRRR");

			assertEquals(subcategoria, aux);// esta bien preguntar

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
 
	}

	@Test
	void testInsertNewSubcategory() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando insert");

		try {

			Subcategoria subcategoria  = new Subcategoria("UYRRR","equipo","GYM");
			int numero = new SubcategoriaManager().insertNewSubcategory(con, subcategoria);
			Subcategoria aux = new SubcategoriaManager().findSudcategoriaById(con, "UYRRR");
			//assertEquals(subcategoria, aux); // preguntar
			assumeNotNull(subcategoria);
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
	void testUpdateSubcategoryByID() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el update");

		try {

			Subcategoria insertada = new Subcategoria("KRCVB","ROJO","GYM");
			assumeNotNull(insertada);
			int numero = new SubcategoriaManager().updateSubcategoryByID(con, insertada, "KRCVB");
			Subcategoria subcategoria = new SubcategoriaManager().findSudcategoriaById(con, "KRCVB");
			assertNotNull(subcategoria);
			assertEquals(subcategoria, insertada);
			assertNotEquals(numero, 0);

		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	void testDeleteSubcategoryByID() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el delete");

		try { 
			
		
			int numero = new SubcategoriaManager().deleteSubcategoryByID(con, "JLJTVQ");
			Subcategoria subca = new SubcategoriaManager().findSudcategoriaById(con, "JLJTVQ");//preguntar
			assertNull(null, subca);
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
