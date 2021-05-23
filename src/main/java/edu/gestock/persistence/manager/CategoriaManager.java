package edu.gestock.persistence.manager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.gestock.persistence.dao.Categoria;
import edu.gestock.persistence.dao.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriaManager {

	/**
	 * Método para mostrar todas las categorias de la base de datos
	 * @param con
	 * @return Array list de categorias
	 */
	public ObservableList<Categoria> findAllCategories(Connection con) {
		String sql = "SELECT * FROM categoria";
		try(Statement stmt = con.createStatement()){
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			ObservableList<Categoria> categorias = FXCollections.observableArrayList();
			while(result.next()) {
				categorias.add(new Categoria(result));
			}
			return categorias;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}//end
	
	
	/**
	 * Método para insertar una nueva categoria
	 * @param Connection con, Categoria categoria   
	 * @return numero de filas afectadas
	 */
	public int insertNewCategory(Connection con, Categoria categoria) {
		String sql = "INSERT INTO categoria(id, nombre) VALUES(?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, categoria.getId());
			ps.setString(2, categoria.getNombre());
			int result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
	/**
	 * Método para actualizar una categoria
	 * @param Connection con, Categoria categoria   
	 * @return numero de filas afectadas
	 */
	public int updateCategoryByID(Connection con, Categoria categoria, String oldId) {
		String sql = "UPDATE categoria SET id = ?, nombre = ? WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, categoria.getId());
			ps.setString(2, categoria.getNombre());
			ps.setString(3, oldId);
			int result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
	/**
	 * Método para borrar una categoria
	 * @param Connection con, Categoria categoria   
	 * @return numero de filas afectadas
	 */
	public int deleteCategoryByID(Connection con, String oldId) {
		String sql = "DELETE FROM categoria WHERE id = ?";
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
