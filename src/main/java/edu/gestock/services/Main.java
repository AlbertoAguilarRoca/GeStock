package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.manager.EmpleadoManager;
import edu.gestock.persistence.manager.ProductosManager;
import edu.gestock.services.mail.EmailNotification;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
			
//			Producto producto = new ProductosManager().findProductosById(con, "greghe");
//			
//			Integer diferencia = producto.getCantidad() - producto.getRoturaStock();
//			System.out.println(diferencia);
			
		
		
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
