/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectolenguajesbasesdatos;

import panaderia.conexion.ConexionSQL;
import java.sql.Connection;

/**
 *
 * @author fidelitas
 */
public class ProyectoLenguajesBasesDatos {

    public static void main(String[] args) {
        Connection conn = ConexionSQL.conectar();
        if (conn != null) {
            System.out.println("🎉 ¡Todo listo para comenzar!");
        } else {
            System.out.println("⚠️ No se pudo conectar.");
        }
    }
}
