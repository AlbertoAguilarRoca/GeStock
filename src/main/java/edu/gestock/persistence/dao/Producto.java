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
public class Producto {
	private String id;
	private String nombre;
	private double precio;
	private String talla;
	private int cantidad;
	private String color;
	private String idProveedor;
	private String idSubcategoria;
	private String descripcion;
	private int roturaStock;

	public Producto(ResultSet result) {
		try {
			this.id = result.getString("id");
			this.nombre = result.getString("nombre");
			this.precio = result.getDouble("precio");
			this.talla = result.getString("talla");
			this.cantidad = result.getInt("cantidad");
			this.color = result.getString("color");
			this.idProveedor = result.getString("idProveedor");
			this.idSubcategoria = result.getString("idSubcategoria");
			this.descripcion = result.getString("descripcion");
			this.roturaStock = result.getInt("roturaStock");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}//end result

}
