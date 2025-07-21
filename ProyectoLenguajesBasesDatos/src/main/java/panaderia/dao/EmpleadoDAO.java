package panaderia.dao;

import panaderia.conexion.ConexionOracle;
import panaderia.modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class EmpleadoDAO {

    public boolean insertarEmpleado(Empleado empleado) {
        String sql = "{ call insertar_empleado(?, ?, ?) }";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getPuesto());
            stmt.setDouble(3, empleado.getSalario());

            stmt.execute();
            conn.commit(); // Confirmar inserción
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }

    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "{ call obtener_empleados(?) }";

        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setPuesto(rs.getString("puesto"));
                e.setSalario(rs.getDouble("salario"));
                empleados.add(e);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener empleados: " + e.getMessage());
        }

        return empleados;
    }

    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "{ call actualizar_empleado(?, ?, ?, ?) }";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, empleado.getIdEmpleado());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getPuesto());
            stmt.setDouble(4, empleado.getSalario());

            stmt.execute();
            conn.commit(); // Confirmar actualización
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "{ call eliminar_empleado(?) }";
        try (Connection conn = ConexionOracle.obtenerConexion();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idEmpleado);
            stmt.execute();
            conn.commit(); // Confirmar eliminación
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}
