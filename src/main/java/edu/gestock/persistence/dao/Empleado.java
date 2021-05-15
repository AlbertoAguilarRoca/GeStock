package edu.gestock.persistence.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class Empleado {
	private String id;
	private String dni;
	private String nombre;
	private String apellidos;
	private String userPassword;
	private Date fechaAlta;
	private String permisos;

	public Empleado(ResultSet result) {
		try {
			this.id = result.getString("id");
			this.dni = result.getString("dni");
			this.nombre = result.getString("nombre");
			this.apellidos = result.getString("apellidos");
			this.userPassword = result.getString("userPassword");
			this.fechaAlta = result.getDate("fechaAlta");
			this.permisos = result.getString("permisos");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", fechaAlta=" + fechaAlta + ", permisos=" + permisos + "]";
	}
	
	

}
