package panaderia.dao;

import panaderia.conexion.ConexionOracle;
import panaderia.modelo.Produccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ProduccionDAO {

    public void insertarProduccion(Produccion produccion) {
        String sql = "{call insertar_produccion(?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, produccion.getIdProducto());
            stmt.execute();
            conn.commit();
            System.out.println("Producción insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar producción: " + e.getMessage());
        }
    }

    public List<Produccion> obtenerProducciones() {
        List<Produccion> lista = new ArrayList<>();
        String sql = "{call obtener_producciones(?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                Produccion produccion = new Produccion();
                produccion.setIdProduccion(rs.getInt("id_produccion"));
                produccion.setIdProducto(rs.getInt("id_producto"));
                produccion.setFecha(rs.getDate("fecha"));
                lista.add(produccion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producciones: " + e.getMessage());
        }

        return lista;
    }

    public void actualizarProduccion(Produccion produccion) {
        String sql = "{call actualizar_produccion(?, ?, ?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, produccion.getIdProduccion());
            stmt.setInt(2, produccion.getIdProducto());
            stmt.setDate(3, produccion.getFecha());
            stmt.execute();
            conn.commit();
            System.out.println("Producción actualizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar producción: " + e.getMessage());
        }
    }

    public void eliminarProduccion(int idProduccion) {
        String sql = "{call eliminar_produccion(?)}";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idProduccion);
            stmt.execute();
            conn.commit();
            System.out.println("Producción eliminada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar producción: " + e.getMessage());
        }
    }
}
