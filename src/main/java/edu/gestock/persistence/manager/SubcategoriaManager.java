package edu.gestock.persistence.manager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.gestock.persistence.dao.Subcategoria;

public class SubcategoriaManager {

	/**
	 * Método para mostrar todas las subcategorias de la base de datos
	 * @param con
	 * @return Array list de subcategorias
	 */
	public List<Subcategoria> findAllSubcategories(Connection con) {
		String sql = "SELECT * FROM subcategoria";
		try(Statement stmt = con.createStatement()){
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			List<Subcategoria> subcategorias = new ArrayList<>();
			while(result.next()) {
				subcategorias.add(new Subcategoria(result));
			}
			return subcategorias;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}//end
	
	/**
	 * Busca dentro de la tabla subcategorias un fila que concuerde con el id 
	 * pasado por argumento.
	 * @param con
	 * @param id
	 * @return Un objeto subcategoria
	 */
	public Subcategoria findSudcategoriaById(Connection con, String id) {
		String sql = "SELECT * FROM subcategoria WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, id);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			Subcategoria subcategoria = null;
			if(result.next()) {
				subcategoria = new Subcategoria(result);
			}
			return subcategoria;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Método para insertar una nueva categoria
	 * @param con 
	 * @param subcategoria   
	 * @return Retorna un entero representado el número de filas afectadas
	 */
	public int insertNewSubcategory(Connection con, Subcategoria subcategoria) {
		String sql = "INSERT INTO subcategoria(id, nombre, idcategoria) VALUES(?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, subcategoria.getId());
			ps.setString(2, subcategoria.getNombre());
			ps.setString(3, subcategoria.getIdcategoria());
			int result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
	/**
	 * Método para actualizar una subcategoria
	 * @param con
	 * @param subcategoria   
	 * @return Retorna un entero representado el número de filas afectadas
	 */
	public int updateSubcategoryByID(Connection con, Subcategoria subcategoria, String oldId) {
		String sql = "UPDATE subcategoria SET id = ?, nombre = ?, idcategoria = ? WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, subcategoria.getId());
			ps.setString(2, subcategoria.getNombre());
			ps.setString(3, subcategoria.getIdcategoria());
			ps.setString(4, oldId);
			int result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
	/**
	 * Método para borrar una subcategoria
	 * @param    
	 * @return numero de filas afectadas
	 */
	public int deleteSubcategoryByID(Connection con, String oldId) {
		String sql = "DELETE FROM subcategoria WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, oldId);
			int result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
}
