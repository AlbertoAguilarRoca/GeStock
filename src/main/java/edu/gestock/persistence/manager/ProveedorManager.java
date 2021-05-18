package edu.gestock.persistence.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import edu.gestock.persistence.dao.Proveedor;

public class ProveedorManager {

	/**
	 * Funcion que muestra todos los proveedores de la base de datos
	 * 
	 * @param con
	 * @return una lista de todos los proveedores
	 */
	public List<Proveedor> findAllProveedor(Connection con) {
		String sql = "SELECT * FROM proveedor";
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			List<Proveedor> proveedores = new ArrayList<>();
			while (result.next()) {
				proveedores.add(new Proveedor(result));
			}
			return proveedores;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}// end

	/**
	 * Funcion que nos encuentra un proveedor mediante su identificador
	 * 
	 * @param con
	 * @param cif
	 * @return Objeto proveedor
	 */
	public Proveedor FindProveedorByCif(Connection con, String cif) {
		String sql = "SELECT * FROM proveedor WHERE CIF = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, cif);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			Proveedor proveedores = null;
			while (result.next()) {
				proveedores = new Proveedor(result);

			}
			return proveedores;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

	}// end

	
	/**
	 * Funcion para insertar nuevos proveedores
	 * @param con
	 * @param proveedor
	 * @return un entero que representa la cantidad de filas afectadas por los cambios
	 */
	public int insertProveedor(Connection con, Proveedor proveedor) {
		String sql = "INSERT INTO proveedor(cif, id, nombre, telefono, email)" + "VALUES (?,?,?,?,?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, proveedor.getCif());
			ps.setString(2, proveedor.getId());
			ps.setString(3, proveedor.getNombre());
			ps.setString(4, proveedor.getTelefono());
			ps.setString(5, proveedor.getEmail());
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}//end
	
	
	/**
	 * Funcion para modificar proveedor a traves de su identificador
	 * @param con
	 * @param proveedor
	 * @param cifProveedor
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados
	 */
	public int updateProveedor(Connection con, Proveedor proveedor, String cifProveedor) {
		
		String sql = "UPDATE proveedor SET cif = ?, id = ?, nombre = ?, telefono = ?, email = ?"
				+ " WHERE cif = ?";
		
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, proveedor.getCif());
			ps.setString(2, proveedor.getId());
			ps.setString(3, proveedor.getNombre());
			ps.setString(4, proveedor.getTelefono());
			ps.setString(5, proveedor.getEmail());
			ps.setString(6, cifProveedor);
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}//end
	
	
	/**
	 * Funcion para borrar un proveedor determinado a traves de su identificador
	 * @param con
	 * @param cifProveedor
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados
	 */
	public int deleteProveedor(Connection con, String cifProveedor) {
		String sql= "DELETE FROM proveedor WHERE cif = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
		ps.setString(1, cifProveedor);
		int result= ps.executeUpdate();
		return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
}
