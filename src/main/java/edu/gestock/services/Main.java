package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.manager.EmpleadoManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
			
			System.out.println(new EmpleadoManager().checkUserLogin(con, "admin", "adminpassword"));
			//new EmpleadoManager().findAllEmployee(con).forEach(System.out::println);
		
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
