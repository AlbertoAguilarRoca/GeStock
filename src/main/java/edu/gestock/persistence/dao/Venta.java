package edu.gestock.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Venta {
	private String nVenta;
	private String idEmpleado;
	private double importe;
	private Date fechaVenta;
	
	public Venta(ResultSet result) {
		try {
			this.nVenta = result.getString("nVenta");
			this.idEmpleado = result.getString("idEmpleado");
			this.importe = result.getDouble("importe");
			this.fechaVenta = result.getDate("fechaVenta");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}//end result
}
