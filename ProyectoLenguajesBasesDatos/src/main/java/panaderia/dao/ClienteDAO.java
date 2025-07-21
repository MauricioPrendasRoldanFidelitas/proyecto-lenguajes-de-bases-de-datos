package panaderia.dao;

import panaderia.conexion.ConexionOracle;
import panaderia.modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ClienteDAO {

    public void insertarCliente(Cliente cliente) {
        String sql = "{call insertar_cliente(?, ?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.execute();
            conn.commit();
            System.out.println("Cliente insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "{call obtener_clientes(?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }

        return lista;
    }

    public void actualizarCliente(Cliente cliente) {
        String sql = "{call actualizar_cliente(?, ?, ?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.execute();
            conn.commit();
            System.out.println("Cliente actualizado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    public void eliminarCliente(int idCliente) {
        String sql = "{call eliminar_cliente(?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCliente);
            stmt.execute();
            conn.commit();
            System.out.println("Cliente eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
}

