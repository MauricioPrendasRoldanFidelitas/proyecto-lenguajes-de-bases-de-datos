/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panaderia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    public static Connection obtenerConexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Asegúrate de esta línea
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String user = "hr";
            String password = "hr";

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conexión exitosa a Oracle.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ No se encontró el driver JDBC de Oracle.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a Oracle: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
