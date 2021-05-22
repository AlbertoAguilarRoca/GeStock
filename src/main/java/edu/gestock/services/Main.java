package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.manager.EmpleadoManager;
import edu.gestock.persistence.manager.ProductosManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
			
			Producto producto = new ProductosManager().findProductosById(con, "hla");
			
			if(producto == null) {
				System.out.println("Producto no encontrado");
			} else {
				System.out.println(producto.toString());
			}
		
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
