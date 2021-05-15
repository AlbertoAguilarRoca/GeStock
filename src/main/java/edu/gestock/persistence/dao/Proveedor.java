package edu.gestock.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Proveedor {
	private String cif;
	private String id;
	private String nombre;
	private String telefono;
	private String email;
	
	public Proveedor(ResultSet result) {
		try {
			this.cif = result.getString("cif");
			this.id = result.getString("id");
			this.nombre = result.getString("nombre");
			this.telefono = result.getString("telefono");
			this.email = result.getString("email");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//end result
}
