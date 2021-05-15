package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.manager.EmpleadoManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
					
			Empleado empleado = new Empleado("pruebaJava", "4515115f", "PRUEBA", "PRUEBA", "pepito", Date.valueOf(LocalDate.now()), "Standard");
			//empleado.setUserPassword("password");
			//new EmpleadoManager().deleteEmpleado(con, "pruebaJava");
			new EmpleadoManager().findAllEmployee(con).forEach(System.out::println);

			
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
