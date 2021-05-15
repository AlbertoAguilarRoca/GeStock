package edu.gestock.persistence.conector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

public class Conector {
	
	@Getter
	@Setter
	Properties prop = new Properties();

	public Conector() {

		try {
			/* Cargamos el archivo conf.properties */
			prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// end constructor

	/*
	 * Creamos el constructor de la conexion con la base de datos Será necesario el
	 * driver MySQL
	 */

	public Connection getMySQLConnection() throws ClassNotFoundException, SQLException {

		try {
			/* Indicamos el driver que vamos a usar */
			Class.forName(prop.getProperty(MySQLConstants.DRIVER));

			try {
				// Creamos la conexion con la url de la bd.
				return DriverManager.getConnection(getURL());
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw ex;
			}
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			throw c;
		}

	}

	// generamos un metodo para obtener la url de la base de datos

	private String getURL() {
		return new StringBuilder().append(prop.getProperty(MySQLConstants.URL_PREFIX))
				.append(prop.getProperty(MySQLConstants.URL_HOST)).append(":")
				.append(prop.getProperty(MySQLConstants.URL_PORT)).append("/")
				.append(prop.getProperty(MySQLConstants.URL_SCHEMA)).append("?user=")
				.append(prop.getProperty(MySQLConstants.USER)).append("&password=")
				.append(prop.getProperty(MySQLConstants.PASSWD)).append("&useSSL=")
				.append(prop.getProperty(MySQLConstants.URL_SSL)).toString();
	}
}
