package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.dao.Proveedor;
import edu.gestock.persistence.manager.EmpleadoManager;
import edu.gestock.persistence.manager.ProductosManager;
import edu.gestock.persistence.manager.ProveedorManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
			 		
		//	Empleado empleado = new Empleado("pruebaJava", "4515115f", "PRUEBA", "PRUEBA", "pepito", Date.valueOf(LocalDate.now()), "Standard");
			//empleado.setUserPassword("password");
			//new EmpleadoManager().deleteEmpleado(con, "pruebaJava");
		//	new EmpleadoManager().findAllEmployee(con).forEach(System.out::println);
			
			// buscar todos los productos
			//new ProductosManager().findAllProductos(con).forEach(productos ->System.out.println(productos));
			//System.out.println(con.getCatalog());
			
			//Buscar producto por id
			//System.out.println(new ProductosManager().findProductosById(con,"greghe"));;
			
			//insertamos un nuevo producto
			
			//Producto producto = new Producto("arhh", "producto6", 34.55, "XL", 16, "Verde", "B93245854", "KRCVB", "eS LO MEJOR PARVERANITO", 3);
			//new ProductosManager().insertProductos(con, producto);
			
			//Modificamos un producto mediante su id
			//Producto producto = new Producto("arhh", "producto6", 34.55, "XL", 16, "Verde", "B93245854", "KRCVB", "eS LO MEJOR palinvierno", 3);
			//new ProductosManager().updateProductos(con, producto, "arhh");
			
			
			//borramos un producto y nos muestra la cantidad de lineas borradas el syso.
			//System.out.println(new ProductosManager().deleteProductos(con, "arhh"));
			
			//Buscar todos los proveedores
			//new ProveedorManager().findAllProveedor(con).forEach(pro->System.out.println(pro));
			
			//Buscar un proveedor por cif
			//System.out.println(new ProveedorManager().FindProveedorByCif(con, "B49515411"));
			
			//insertar proveedor
			//new Proveedor prueba = new Proveedor("B49515455", "Adidas", "", null, null) 
			
			
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
