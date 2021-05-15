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
public class EsVendido {
	 private String idProducto;
	 private String nVenta;
	 private int unidades;
	 
	 public EsVendido(ResultSet result) {
		 try {
			 this.idProducto = result.getString("idProducto");
			 this.nVenta = result.getString("nVenta");
			 this.unidades = result.getInt("unidades");
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
	 }//end result
}
