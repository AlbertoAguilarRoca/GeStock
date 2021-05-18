package edu.gestock.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Empleado;
import edu.gestock.persistence.dao.EsVendido;
import edu.gestock.persistence.dao.Producto;
import edu.gestock.persistence.dao.Proveedor;
import edu.gestock.persistence.dao.Venta;
import edu.gestock.persistence.manager.EmpleadoManager;
import edu.gestock.persistence.manager.EsVendidoManager;
import edu.gestock.persistence.manager.ProductosManager;
import edu.gestock.persistence.manager.ProveedorManager;
import edu.gestock.persistence.manager.VentasManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection con = new Conector().getMySQLConnection();
		
		try {
			 		
		//	Empleado empleado = new Empleado("pruebaJava", "4515115f", "PRUEBA", "PRUEBA", "pepito", Date.valueOf(LocalDate.now()), "Standard");
			//empleado.setUserPassword("password");
			//new EmpleadoManager().deleteEmpleado(con, "pruebaJava");
		//	new EmpleadoManager().findAllEmployee(con).forEach(System.out::println);
			//System.out.println(con.getCatalog());
			
			// buscar todos los productos
			//new ProductosManager().findAllProductos(con).forEach(productos ->System.out.println(productos));
			
			
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
			//Proveedor prueba = new Proveedor("B49515455", "Deca", "Decathlon", "632554489", "Declathlon@gmail.com");
			//new ProveedorManager().insertProveedor(con, prueba);
			
			//Modificamos un proveedor
			//Proveedor prueba = new Proveedor("B49515455", "Ast", "Asturias", "632554489", "Declathlon@gmail.com");
			//new ProveedorManager().updateProveedor(con, prueba, "B49515455");
			
			//borrar un proveedor
			//new ProveedorManager().deleteProveedor(con,"B49515455");
			
			//muestra todas las ventas
			//new VentasManager().findAllVenta(con).forEach(venta ->System.out.println(venta));
			
			//buscar venta por Nventa
			//System.out.println(new VentasManager().FindVentaBynVenta(con, "gfrthg"));
				
			//Insertar una venta
			//Venta vendido = new Venta("funcionarrr", "stardard", 35.89,Date.valueOf(LocalDate.now()) );
			//new VentasManager().insertVenta(con, vendido);
			
			//modificar venta
			//Venta vendido = new Venta("funcionarrr", "stardard", 48.99,Date.valueOf(LocalDate.now()) );
			//System.out.println(new VentasManager().updateVenta(con, vendido, "funcionarrr"));
			
			//borrar una venta
			//System.out.println(new VentasManager().deleteVenta(con, "funcionarrr"));
			
			//mostrar toda la relacion Esvendido
			//new EsVendidoManager().findAllesVendido(con).forEach(ve->System.out.println(ve));
			
			//buscar esvendido por idproducto
			//System.out.println(new EsVendidoManager().findEsVendidoByidProducto(con, "greghe"));
		
			//buscar esvendido por nVenta
			//System.out.println(new EsVendidoManager().findEsVendidoBynVenta(con, "gfrthg"));
		
			//Insertar una relacion esvendido
			
			
		
		
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
