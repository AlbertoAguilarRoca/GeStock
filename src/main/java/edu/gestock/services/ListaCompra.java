package edu.gestock.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListaCompra {

	private String id;
	private String nombre;
	private String talla;
	private Double precio;
	private Integer cantidad;
	
	@Override
	public String toString() {
		return id + " "+ nombre+ " "+talla+ " "+cantidad + " "+precio;
	}
	
	
}
