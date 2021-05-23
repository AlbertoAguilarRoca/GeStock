package edu.gestock.persistence.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import edu.gestock.persistence.dao.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductosManager {

	/**
	 * Funcion para encontrar todos los productos de la base de datos
	 * 
	 * @param con
	 * @return Lista de todos los productos
	 */

	public ObservableList<Producto> findAllProductos(Connection con) {
		String sql = "SELECT * FROM productos";
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			ObservableList<Producto> productos = FXCollections.observableArrayList();
			while (result.next()) {
				productos.add(new Producto(result));
			}
			return productos;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}// end

	/**
	 * Funcion para encontrar un producto mediante su identificador id
	 * 
	 * @param con
	 * @param id
	 * @return Objeto Producto
	 */

	public Producto findProductosById(Connection con, String id) {
		String sql = "SELECT * FROM productos WHERE ID = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, id);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			Producto productos = null;
			while (result.next()) {
				productos = new Producto(result);
			}
			return productos;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}// end
	
	/**
	 * Cuenta la cantidad de productos que hay en la base de datos
	 * @param con
	 * @return número de filas de la columna
	 */
	public int countRowProducts(Connection con) {
		String sql = "SELECT count(*) as rowcount FROM productos";
		try (Statement stmt = con.createStatement()){
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			int contador = 0;
			while(result.next()) {
				contador = result.getInt("rowcount");
			}
			return contador;
		} catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Reduce el stock en base a la cantidad de producto vendido
	 * @param con
	 * @param id
	 * @param unidadesVendidas
	 */
	public void reduceStock(Connection con, String id, int unidadesVendidas) {
		String sql = "UPDATE productos SET cantidad = (cantidad - ?) WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, unidadesVendidas);
			ps.setString(2, id);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * Funcion para insertar nuevos productos
	 * @param con
	 * @param producto
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados
	 */
	public int insertProductos(Connection con, Producto producto) {
		String sql = "INSERT INTO productos(id, nombre, precio, talla, cantidad, color, idProveedor, idSubcategoria, descripcion, roturaStock) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, producto.getId());
			ps.setString(2, producto.getNombre());
			ps.setDouble(3, producto.getPrecio());
			ps.setString(4, producto.getTalla());
			ps.setInt(5, producto.getCantidad());
			ps.setString(6, producto.getColor());
			ps.setString(7, producto.getIdProveedor());
			ps.setString(8, producto.getIdSubcategoria());
			ps.setString(9, producto.getDescripcion());
			ps.setInt(10, producto.getRoturaStock());
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}//end
	
	/**
	 * Funcion para modificar productos a traves de su identificador id
	 * @param con
	 * @param producto
	 * @param idProducto
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados
	 */
	
	public int updateProductos(Connection con,Producto producto,String idProducto) {
		String sql="UPDATE productos SET id = ?, nombre = ?, precio = ?, talla = ?, cantidad = ?, color = ?, idProveedor = ?, idSubcategoria = ?, descripcion = ?, roturaStock = ? "
				+ "WHERE id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, producto.getId());
			ps.setString(2, producto.getNombre());
			ps.setDouble(3, producto.getPrecio());
			ps.setString(4, producto.getTalla());
			ps.setInt(5, producto.getCantidad());
			ps.setString(6, producto.getColor());
			ps.setString(7, producto.getIdProveedor());
			ps.setString(8, producto.getIdSubcategoria());
			ps.setString(9, producto.getDescripcion());
			ps.setInt(10, producto.getRoturaStock());
			ps.setString(11, idProducto);
			
			int result = ps.executeUpdate();
			return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
	
	
	 
	/**
	 * Funcion para borrar un producto determinado a traves de su identificador id
	 * @param con
	 * @param idProducto
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados
	 */
	public int deleteProductos(Connection con, String idProducto) {
		String sql= "DELETE FROM productos WHERE id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
		ps.setString(1, idProducto);
		int result= ps.executeUpdate();
		return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end

	
}
