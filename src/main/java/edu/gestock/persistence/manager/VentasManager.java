package edu.gestock.persistence.manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import edu.gestock.persistence.dao.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VentasManager {

	/**
	 * Funcion que muestra todos las ventas de la base de datos
	 * 
	 * @param con
	 * @return una lista de todas las ventas.
	 */
	public ObservableList<Venta> findAllVenta(Connection con) {
		String sql = "SELECT * FROM ventas";
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery(sql);
			result.beforeFirst();
			ObservableList<Venta> ventas = FXCollections.observableArrayList();
			while (result.next()) {
				ventas.add(new Venta(result));
			}
			return ventas;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}// end

	/**
	 * Funcion que nos encuentra una venta mediante su identificador nVentas
	 * 
	 * @param con
	 * @param nVenta
	 * @return Objeto Venta
	 */
	public Venta FindVentaBynVenta(Connection con, String nVenta) {
		String sql = "SELECT * FROM ventas WHERE nVenta = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, nVenta);
			ResultSet result = ps.executeQuery();
			result.beforeFirst();
			Venta ventas = null;
			while (result.next()) {
				ventas = new Venta(result);

			}
			return ventas;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

	}// end

	/**
	 * Funcion para insertar nuevas ventas
	 * 
	 * @param con
	 * @param venta
	 * @return un entero que representa la cantidad de filas afectadas por los
	 *         cambios
	 */
	public int insertVenta(Connection con, Venta venta) {
		String sql = "INSERT INTO ventas(nVenta, idEmpleado, importe, fechaVenta)" + "VALUES (?,?,?,?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, venta.getNVenta());
			ps.setString(2, venta.getIdEmpleado());
			ps.setDouble(3, venta.getImporte());
			ps.setDate(4, (Date) venta.getFechaVenta());

			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}// end

	/**
	 * Funcion para modificar ventas a traves de su identificador nVentas
	 * 
	 * @param con
	 * @param venta
	 * @param nVenta
	 * @return un entero que representa la cantidad de filas afectadas por los
	 *         cambios
	 */
	public int updateVenta(Connection con, Venta venta, String nVenta) {

		String sql = "UPDATE ventas SET nVenta = ?, idEmpleado = ?, importe = ?, fechaVenta = ?" + " WHERE nVenta = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, venta.getNVenta());
			ps.setString(2, venta.getIdEmpleado());
			ps.setDouble(3, venta.getImporte());
			ps.setDate(4, (Date) venta.getFechaVenta());
			ps.setString(5, nVenta);
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}// end

	/**
	 * Funcion que elimina una venta determinada a traves de su identificador nVentas
	 * @param con
	 * @param nVenta
	 * @return un entero que representa la cantidad de filas afectadas por los cambios realizados
	 */
	public int deleteVenta(Connection con, String nVenta) {
		String sql = "DELETE FROM ventas WHERE nVenta = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, nVenta);
			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}// end

}
