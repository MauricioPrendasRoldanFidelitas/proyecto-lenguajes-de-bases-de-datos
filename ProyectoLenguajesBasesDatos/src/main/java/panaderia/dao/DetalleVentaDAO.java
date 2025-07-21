package panaderia.dao;

import panaderia.conexion.ConexionOracle;
import panaderia.modelo.DetalleVenta;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO {

    public void insertarDetalleVenta(int idVenta, int idProducto, int cantidad) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionOracle.obtenerConexion();
            cs = con.prepareCall("{call insertar_detalle_venta(?, ?, ?)}");

            cs.setInt(1, idVenta);
            cs.setInt(2, idProducto);
            cs.setInt(3, cantidad);

            cs.execute();
            System.out.println("Detalle de venta insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar detalle de venta: " + e.getMessage());
        }
    }

    public List<DetalleVenta> obtenerDetallesVenta() {
        List<DetalleVenta> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = ConexionOracle.obtenerConexion();
            cs = con.prepareCall("{call obtener_detalles_venta(?)}");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdDetalleVenta(rs.getInt("id_detalle_venta"));
                dv.setIdVenta(rs.getInt("id_venta"));
                dv.setIdProducto(rs.getInt("id_producto"));
                dv.setCantidad(rs.getInt("cantidad"));
                lista.add(dv);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener detalles de venta: " + e.getMessage());
        } 

        return lista;
    }

    public void eliminarDetalleVenta(int idDetalleVenta) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionOracle.obtenerConexion();
            cs = con.prepareCall("{call eliminar_detalle_venta(?)}");
            cs.setInt(1, idDetalleVenta);
            cs.execute();
            System.out.println("Detalle de venta eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle de venta: " + e.getMessage());
        }
    }
}
