package panaderia.dao;

import panaderia.conexion.ConexionOracle;
import panaderia.modelo.DetallePedido;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleTypes;

public class DetallePedidoDAO {
    public void insertarDetallePedido(int idPedido, int idProducto, int cantidad, double precioUnitario) {
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall("{call insertar_detalle_pedido(?, ?, ?, ?)}")) {

            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, precioUnitario); 

            stmt.execute();
            System.out.println("Detalle de pedido insertado correctamente.");
        } catch (SQLException e) {
            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    public void obtenerDetallesPedido() {
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall("{call obtener_detalles_pedido(?)}")) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);

            System.out.println("\n--- Lista de Detalles de Pedido ---");
            while (rs.next()) {
                System.out.println("ID Detalle: " + rs.getInt("id_detalle_pedido") +
                                   ", Pedido: " + rs.getInt("id_pedido") +
                                   ", Producto: " + rs.getInt("id_producto") +
                                   ", Cantidad: " + rs.getInt("cantidad"));
            }
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void actualizarDetallePedido(int idDetalle, int idPedido, int idProducto, int cantidad) {
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall("{call actualizar_detalle_pedido(?, ?, ?, ?)}")) {

            stmt.setInt(1, idDetalle);
            stmt.setInt(2, idPedido);
            stmt.setInt(3, idProducto);
            stmt.setInt(4, cantidad);

            stmt.execute();
            System.out.println("Detalle de pedido actualizado correctamente.");
        } catch (SQLException e) {
            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminarDetallePedido(int idDetalle) {
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall("{call eliminar_detalle_pedido(?)}")) {

            stmt.setInt(1, idDetalle);
            stmt.execute();
            System.out.println("Detalle de pedido eliminado correctamente.");
        } catch (SQLException e) {
            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
