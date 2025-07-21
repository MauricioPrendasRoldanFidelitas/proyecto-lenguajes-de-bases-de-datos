package panaderia.dao;

import panaderia.modelo.Pedido;
import panaderia.conexion.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class PedidoDAO {

    public void insertarPedido(Pedido pedido) {
        String sql = "{call insertar_pedido(?)}";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.execute();
            System.out.println("Pedido insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar pedido: " + e.getMessage());
        }
    }


    public List<Pedido> obtenerPedidos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "{call obtener_pedidos(?)}";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    Pedido p = new Pedido();
                    p.setIdPedido(rs.getInt("id_pedido"));
                    p.setIdCliente(rs.getInt("id_cliente"));
                    p.setFecha(rs.getString("fecha"));
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos: " + e.getMessage());
        }
        return lista;
    }

    public void actualizarPedido(Pedido pedido) {
        String sql = "{call actualizar_pedido(?, ?, ?)}";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, pedido.getIdPedido());
            stmt.setInt(2, pedido.getIdCliente());
            stmt.setString(3, pedido.getFecha());
            stmt.execute();
            System.out.println("Pedido actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    public void eliminarPedido(int idPedido) {
        String sql = "{call eliminar_pedido(?)}";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPedido);
            stmt.execute();
            System.out.println("Pedido eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
}
