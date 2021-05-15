package edu.gestock.persistence.manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.gestock.persistence.dao.Empleado;

public class EmpleadoManager {

	/**
	 * Función para encontrar todos los empleados de la base de datos
	 * @param con
	 * @return Lista con todos los empleados
	 */
	public List<Empleado> findAllEmployee(Connection con) {
		String sql = "SELECT * FROM empleado";
		try(Statement stmt = con.createStatement()){
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			List<Empleado> empleados = new ArrayList<>();
			while(result.next()) {
				empleados.add(new Empleado(result));
			}
			
			return empleados;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}//end
	
	/**
	 * Función para encontrar un empleado mediante su identificador
	 * @param con
	 * @param id
	 * @return Objeto empleado
	 */
	public Empleado findEmpleadoById(Connection con, String id) {
		String sql = "SELECT * FROM empleado WHERE id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, id);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			Empleado empleado = null;
			while(result.next()) {
				empleado = new Empleado(result);
			}			
			return empleado;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}//end 
	
	/**
	 * Funcion para insertar nuevos empleados
	 * @param con
	 * @param empleado
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados.
	 */
	public int insertarEmpleado(Connection con, Empleado empleado) {
		String sql = "INSERT INTO empleado(id, dni, nombre, apellidos, userPassword, fechaAlta, permisos) "
				+ "VALUES(?, ?, ?, ?, AES_ENCRYPT(?, 'keypassword'), ?, ?)";
		try(PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, empleado.getId());
			ps.setString(2, empleado.getDni());
			ps.setString(3, empleado.getNombre());
			ps.setString(4, empleado.getApellidos());
			ps.setString(5, empleado.getUserPassword());
			ps.setDate(6, (Date) empleado.getFechaAlta());
			ps.setString(7, empleado.getPermisos());
			int result = ps.executeUpdate();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}//end
	
	/**
	 * Funcion para modificar empleados a través de su identificador
	 * @param con
	 * @param empleado
	 * @param
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados.
	 */
	public int updateEmpleado(Connection con, Empleado empleado, String idEmpleado) {
		String sql = "UPDATE empleado SET id = ?, dni = ?, nombre = ?, apellidos = ?, "
				+ "userPassword = AES_ENCRYPT(?, 'keypassword'), fechaAlta = ?, permisos = ? WHERE id = ? ";
		try(PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, empleado.getId());
			ps.setString(2, empleado.getDni());
			ps.setString(3, empleado.getNombre());
			ps.setString(4, empleado.getApellidos());
			ps.setString(5, empleado.getUserPassword());
			ps.setDate(6, (Date) empleado.getFechaAlta());
			ps.setString(7, empleado.getPermisos());
			ps.setString(8, idEmpleado);
			int result = ps.executeUpdate();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}//end
	
	/**
	 * Elimina un empleado a través del identificador
	 * @param con
	 * @param idEmpleado
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados.
	 */
	public int deleteEmpleado(Connection con, String idEmpleado) {
		String sql = "DELETE FROM empleado WHERE id = ? ";
		try(PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, idEmpleado);
			int result = ps.executeUpdate();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}//end
}
