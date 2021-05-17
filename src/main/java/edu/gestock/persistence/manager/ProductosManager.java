package edu.gestock.persistence.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	public List<Producto> findAllProductos(Connection con) {
		String sql = "SELECT * FROM productos";
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

	}// end

	/**
	 * Funcion para encontrar un producto mediante su identificador
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
	 * Funcion para modificar productos a traves de su identificador
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
	 * Funcion para borrar un producto determinado a traves de su identificador
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
