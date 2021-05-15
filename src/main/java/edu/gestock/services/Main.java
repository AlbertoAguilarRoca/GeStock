package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Subcategoria;
import edu.gestock.persistence.manager.SubcategoriaManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
			
			Subcategoria sc = new Subcategoria(Id.generator(7, 10), "BICICLETAS DE MONTAÑA", "CICL");
			
			new SubcategoriaManager().deleteSubcategoryByID(con, "yZafSsM");
			
			new SubcategoriaManager().findAllSubcategories(con).forEach(System.out::println);
			
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
