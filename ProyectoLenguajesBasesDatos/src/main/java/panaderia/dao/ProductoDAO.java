package panaderia.dao;

import panaderia.conexion.ConexionOracle;
import panaderia.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ProductoDAO {

    public boolean insertarProducto(Producto producto) {
        String sql = "{ call insertar_producto(?, ?, ?) }";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            conn.setAutoCommit(false);  // Desactivar autocommit

            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());

            stmt.execute();
            conn.commit();  // Confirmar la transacción
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "{ call obtener_productos(?) }";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                productos.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }

        return productos;
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "{ call actualizar_producto(?, ?, ?, ?) }";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            conn.setAutoCommit(false);  // Desactivar autocommit

            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            stmt.execute();
            conn.commit();  // Confirmar la transacción
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int idProducto) {
        String sql = "{ call eliminar_producto(?) }";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            conn.setAutoCommit(false);  // Desactivar autocommit

            stmt.setInt(1, idProducto);
            stmt.execute();
            conn.commit();  // Confirmar la transacción
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
