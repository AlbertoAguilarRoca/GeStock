package edu.gestock.persistence.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Empleado {
	private String id;
	private String dni;
	private String nombre;
	private String apellidos;
	private Blob userPassword;
	private Date fechaAlta;
	private String permisos;

	public Empleado(ResultSet result) {
		try {
			this.id = result.getString("id");
			this.dni = result.getString("dni");
			this.nombre = result.getString("nombre");
			this.apellidos = result.getString("apellidos");
			this.userPassword = result.getBlob("userPassword");
			this.fechaAlta = result.getDate("fechaAlta");
			this.permisos = result.getString("permisor");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
