package edu.gestock.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Categoria {

	private String id;
	private String nombre;
	
	public Categoria(ResultSet result) {
		try {
			this.id = result.getString("id");
			this.nombre = result.getString("nombre");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//end constructor
	
}
