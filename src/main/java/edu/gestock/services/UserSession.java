package edu.gestock.services;

import edu.gestock.persistence.dao.Empleado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {

	private Empleado empleado;
	
	public UserSession(Empleado empleado) {
		this.empleado = empleado;
	}
	
}
