package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.manager.EmpleadoManager;
import edu.gestock.persistence.manager.ProductosManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
			 		
		//	Empleado empleado = new Empleado("pruebaJava", "4515115f", "PRUEBA", "PRUEBA", "pepito", Date.valueOf(LocalDate.now()), "Standard");
			//empleado.setUserPassword("password");
			//new EmpleadoManager().deleteEmpleado(con, "pruebaJava");
		//	new EmpleadoManager().findAllEmployee(con).forEach(System.out::println);
			new ProductosManager().findAllProducts(con).forEach(productos ->System.out.println(productos));
			//System.out.println(con.getCatalog());
			
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
