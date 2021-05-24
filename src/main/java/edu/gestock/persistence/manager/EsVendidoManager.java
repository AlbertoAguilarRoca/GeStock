package edu.gestock.persistence.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.gestock.persistence.dao.EsVendido;
import edu.gestock.services.ListaCompra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class EsVendidoManager {

	/**
	 * Funcion para encontrar la relacion entre productos y ventas de la base de
	 * datos
	 * 
	 * @param con
	 * @return
	 */
	public List<EsVendido> findAllesVendido(Connection con) {
		String sql = "SELECT * FROM esvendido";
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			List<EsVendido> vendidos = new ArrayList<>();
			while (result.next()) {
				vendidos.add(new EsVendido(result));
			}
			return vendidos;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}// end

	/**
	 * Funcion para encontrar una relacion de las tablas ventas y productos por
	 * identificador idProducto
	 * 
	 * @param con
	 * @param idProducto
	 * @return Objeto EsVendido
	 */
	public EsVendido findEsVendidoByidProducto(Connection con, String idProducto) {
		String sql = "SELECT * FROM esvendido WHERE idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, idProducto);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			EsVendido vendidos = null;
			while (result.next()) {
				vendidos = new EsVendido(result);
			}
			return vendidos;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}// end

	/**
	 * Funcion para encontrar una relacion de las tablas ventas y productos por
	 * identificador nVenta
	 * 
	 * @param con
	 * @param nVenta
	 * @return
	 */
	public EsVendido findEsVendidoBynVenta(Connection con, String nVenta) {
		String sql = "SELECT * FROM esvendido WHERE nVenta = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, nVenta);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			EsVendido vendidos = null;
			while (result.next()) {
				vendidos = new EsVendido(result);
			}
			return vendidos;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}// end

	/**
	 * Funcion para insertar un Esvendido
	 * @param con
	 * @param esvendido
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados
	 */
	public int insertEsVendido(Connection con, EsVendido esvendido) {
		String sql = "INSERT INTO esvendido(idProducto, nVenta, unidades)" + "VALUES (?,?,?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, esvendido.getIdProducto());
			ps.setString(2, esvendido.getNVenta());
			ps.setInt(3, esvendido.getUnidades());
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}// end
	
	/**
	 * Funcion para modificar la relacion de las tablas ventas y productos a traves de su identificador de producto idProducto
	 * @param con
	 * @param esvendido
	 * @param idProducto
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados.
	 */
	public int updateEsVendidoByIdProducto(Connection con,EsVendido esvendido, String idProducto) {
		String sql = "UPDATE esvendido SET idProducto = ?, nVenta = ?, unidades = ?, "
				+ "WHERE idProducto = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, esvendido.getIdProducto());
			ps.setString(2, esvendido.getNVenta());
			ps.setInt(3, esvendido.getUnidades());
			ps.setString(4,idProducto);
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}// end	
	
	/**
	 * Funcion para modificar la relacion de las tablas ventas y productos a traves de su identificador de ventas nVenta
	 * @param con
	 * @param esvendido
	 * @param nVenta
	 * @returnun entero que representa la cantidad de filas afectadas por los cambios realizados.
	 */
	public int updateEsVendidoByNventa(Connection con,EsVendido esvendido, String nVenta) {
		String sql = "UPDATE esvendido SET idProducto = ?, nVenta = ?, unidades = ?, "
				+ "WHERE nVenta = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, esvendido.getIdProducto());
			ps.setString(2, esvendido.getNVenta());
			ps.setInt(3, esvendido.getUnidades());
			ps.setString(4,nVenta);
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}// end	
	
	/**
	 * Funcion para borrar una relacion en la tabla esvendido mediante el identificador de producto idProducto
	 * @param con
	 * @param idProducto
	 * @return entero que representa la cantidad de filas afectadas por los cambios realizados.
	 */
	public int deleteEsVendidoByidProducto(Connection con, String idProducto) {
		String sql= "DELETE FROM esvendido WHERE idProducto = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
		ps.setString(1, idProducto);
		int result= ps.executeUpdate();
		return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
	/**
	 * Funcion para borrar una relacion en la tabla esvendido mediante el identificador de ventas nVentas
	 * @param con
	 * @param nVenta
	 * @return entero que representa la cantidad de filas afectadas por los cambios realizados.
	 */
	public int deleteEsVendidoBynVenta(Connection con, String nVenta) {
		String sql= "DELETE FROM esvendido WHERE nVenta = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
		ps.setString(1, nVenta);
		int result= ps.executeUpdate();
		return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}//end
	
	
	
	/**
	 * Metodo para enlazar las ventas con los productos vendidos en dicha compra
	 * @param con
	 * @param nVenta
	 * @return
	 */
	public ObservableList<ListaCompra> productosEnUnaVenta(Connection con, String nVenta) {
		String sql = "select p.id, p.nombre, p.talla, p.precio, ev.unidades "
				+ "from productos p, esVendido ev, ventas v "
				+ "where p.id = ev.idProducto and ev.nVenta = v.nVenta "
				+ "and v.nVenta = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, nVenta);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			ObservableList<ListaCompra> listaCompra = FXCollections.observableArrayList();
			while(result.next()) {
				listaCompra.add(new ListaCompra(result.getString(1), 
						result.getString(2), result.getString(3), result.getDouble(4), result.getInt(5)));
			}
			return listaCompra;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
