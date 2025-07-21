package panaderia.dao;

import panaderia.conexion.ConexionOracle;
import panaderia.modelo.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class VentaDAO {

    public void insertarVenta() {
    try (Connection conn = ConexionOracle.obtenerConexion();
         CallableStatement stmt = conn.prepareCall("{call insertar_venta()}")) {

        stmt.execute();
        System.out.println("Venta insertada correctamente.");
    } catch (SQLException e) {
        System.err.println("Error al insertar venta: " + e.getMessage());
    }
}


    public List<Venta> obtenerVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "{call obtener_ventas(?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                Venta v = new Venta();
                v.setIdVenta(rs.getInt("id_venta"));
                v.setFecha(rs.getDate("fecha"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas: " + e.getMessage());
        }
        System.out.println("Número de ventas: " + lista.size());
        return lista;
    }


    public void eliminarVenta(int idVenta) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionOracle.obtenerConexion();
            cs = con.prepareCall("{call eliminar_venta(?)}");
            cs.setInt(1, idVenta);
            cs.execute();

            System.out.println("Venta eliminada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al eliminar venta: " + e.getMessage());
        } 
    }
}
