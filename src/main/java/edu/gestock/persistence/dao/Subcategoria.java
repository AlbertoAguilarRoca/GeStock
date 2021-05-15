package edu.gestock.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Subcategoria {
	private String id;
	private String nombre;
	private String idcategoria;
	
	public Subcategoria(ResultSet result) {
		try {
			this.id = result.getString("id");
			this.nombre = result.getString("nombre");
			this.idcategoria = result.getString("idcategoria");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}//end resulset
}
