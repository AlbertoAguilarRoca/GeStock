package edu.gestock.persistence.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import edu.gestock.persistence.dao.Producto;

public class ProductosManager {

	/**
	 * Funcion para encontrar todos los productos de la base de datos
	 * 
	 * @param con
	 * @return Lista de todos los productos
	 */

	public List<Producto> findAllProducts(Connection con) {
		String sql = "SELECT * FROM PRODUCTOS";
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			List<Producto> productos = new ArrayList<>();
			while (result.next()) {
				productos.add(new Producto(result));
			}
			return productos;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}//end
	
	/**
	 * Funcion para encontrar un producto mediante su identificador
	 * @param con
	 * @param id
	 * @return Objeto Producto
	 */
	//public Producto findProductsById(Connection con,String id) {
		//String sql = "SELECT * FROM PRODUCTOS WHERE ID = ?"
		
		
	}


