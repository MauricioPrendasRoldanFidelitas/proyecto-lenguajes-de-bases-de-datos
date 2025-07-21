package com.mycompany.proyectolenguajesbasesdatos;

import panaderia.conexion.ConexionOracle;
import panaderia.dao.*;
import panaderia.modelo.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProyectoLenguajesBasesDatos {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Connection conn = ConexionOracle.obtenerConexion();
        if (conn == null) {
            System.out.println("❌ No se pudo establecer la conexión con la base de datos.");
            return;
        }

        int opcionPrincipal;
        do {
            System.out.println("\n==== MENÚ PRINCIPAL ====");
            System.out.println("1. Cliente");
            System.out.println("2. Pedido");
            System.out.println("3. Detalle Pedido");
            System.out.println("4. Venta");
            System.out.println("5. Detalle Venta");
            System.out.println("6. Producto");
            System.out.println("7. Producción");
            System.out.println("8. Salir");
            System.out.print("Seleccione una entidad (1-8): ");

            try {
                opcionPrincipal = Integer.parseInt(scanner.nextLine());
                switch (opcionPrincipal) {
                    case 1 -> menuCliente();
                    case 2 -> menuPedido();
                    case 3 -> menuDetallePedido();
                    case 4 -> menuVenta();
                    case 5 -> menuDetalleVenta();
                    case 6 -> menuProducto();
                    case 7 -> menuProduccion();
                    case 8 -> System.out.println("👋 Saliendo del programa...");
                    default -> System.out.println("❗ Opción inválida, intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❗ Entrada inválida. Por favor, ingrese un número.");
                opcionPrincipal = -1;
            }
        } while (opcionPrincipal != 8);

        scanner.close();
    }

    // Ejemplo submenú para Producto
    private static void menuProducto() {
        ProductoDAO productoDAO = new ProductoDAO();
        int opcion;
        do {
            System.out.println("\n==== MENÚ DE PRODUCTOS ====");
            System.out.println("1. Listar productos");
            System.out.println("2. Agregar producto");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción (1-5): ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        List<Producto> productos = productoDAO.obtenerProductos();
                        if (productos.isEmpty()) {
                            System.out.println("⚠️ No hay productos registrados.");
                        } else {
                            System.out.println("📦 Lista de productos:");
                            productos.forEach(System.out::println);
                        }
                    }
                    case 2 -> {
                        System.out.print("Nombre del producto: ");
                        String nombre = scanner.nextLine();
                        double precio = solicitarDouble("Precio del producto: ");
                        int stock = solicitarEntero("Stock del producto: ");

                        Producto nuevoProducto = new Producto(nombre, precio, stock);
                        productoDAO.insertarProducto(nuevoProducto);
                        System.out.println("✅ Producto agregado correctamente.");
                    }
                    case 3 -> {
                        int idActualizar = solicitarEntero("ID del producto a actualizar: ");
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        double nuevoPrecio = solicitarDouble("Nuevo precio: ");
                        int nuevoStock = solicitarEntero("Nuevo stock: ");

                        Producto productoActualizado = new Producto(idActualizar, nuevoNombre, nuevoPrecio, nuevoStock);
                        productoDAO.actualizarProducto(productoActualizado);
                        System.out.println("✅ Producto actualizado correctamente.");
                    }
                    case 4 -> {
                        int idEliminar = solicitarEntero("ID del producto a eliminar: ");
                        productoDAO.eliminarProducto(idEliminar);
                        System.out.println("🗑️ Producto eliminado (si existía).");
                    }
                    case 5 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("❗ Opción inválida. Por favor, ingrese un número del 1 al 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❗ Entrada inválida. Por favor, ingrese un número.");
                opcion = -1;
            }
        } while (opcion != 5);
    }

    // Ejemplo submenú para Producción
    private static void menuProduccion() {
        ProduccionDAO produccionDAO = new ProduccionDAO();
        int opcion;
        do {
            System.out.println("\n==== MENÚ DE PRODUCCIÓN ====");
            System.out.println("1. Listar producciones");
            System.out.println("2. Agregar producción");
            System.out.println("3. Actualizar producción");
            System.out.println("4. Eliminar producción");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción (1-5): ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        List<Produccion> lista = produccionDAO.obtenerProducciones();
                        if (lista.isEmpty()) {
                            System.out.println("⚠️ No hay registros de producción.");
                        } else {
                            System.out.println("📦 Lista de producciones:");
                            lista.forEach(System.out::println);
                        }
                    }
                    case 2 -> {
                        int idProducto = solicitarEntero("ID del producto: ");
                        java.sql.Date fecha = solicitarFecha("Fecha (yyyy-MM-dd): ");

                        Produccion produccion = new Produccion();
                        produccion.setIdProducto(idProducto);
                        produccion.setFecha(fecha);

                        produccionDAO.insertarProduccion(produccion);
                        System.out.println("✅ Producción agregada correctamente.");
                    }
                    case 3 -> {
                        int idProduccion = solicitarEntero("ID de producción a actualizar: ");
                        int idProducto = solicitarEntero("Nuevo ID del producto: ");
                        java.sql.Date fecha = solicitarFecha("Nueva fecha (yyyy-MM-dd): ");

                        Produccion produccion = new Produccion(idProduccion, idProducto);
                        produccionDAO.actualizarProduccion(produccion);
                        System.out.println("✅ Producción actualizada correctamente.");
                    }
                    case 4 -> {
                        int idEliminar = solicitarEntero("ID de producción a eliminar: ");
                        produccionDAO.eliminarProduccion(idEliminar);
                        System.out.println("🗑️ Producción eliminada (si existía).");
                    }
                    case 5 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("❗ Opción inválida. Por favor, ingrese un número del 1 al 5.");
                }
            } catch (Exception e) {
                System.out.println("❗ Entrada inválida. Intente de nuevo.");
                opcion = -1;
            }
        } while (opcion != 5);
    }

    // Métodos reutilizables para pedir enteros y doubles (sin cambios)
    private static int solicitarEntero(String mensaje) {
        int valor;
        while (true) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("❗ Por favor, ingrese un número entero válido.");
            }
        }
    }

    private static double solicitarDouble(String mensaje) {
        double valor;
        while (true) {
            try {
                System.out.print(mensaje);
                valor = Double.parseDouble(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("❗ Por favor, ingrese un número decimal válido.");
            }
        }
    }

    // Método para leer fecha yyyy-MM-dd y convertir a java.sql.Date
    private static java.sql.Date solicitarFecha(String mensaje) {
        java.sql.Date fecha = null;
        while (fecha == null) {
            try {
                System.out.print(mensaje);
                String fechaStr = scanner.nextLine();
                fecha = java.sql.Date.valueOf(fechaStr);  // formato yyyy-MM-dd
            } catch (IllegalArgumentException e) {
                System.out.println("❗ Formato de fecha inválido. Use yyyy-MM-dd.");
            }
        }
        return fecha;
    }

        private static void menuCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        int opcion;
        do {
            System.out.println("\n==== MENÚ DE CLIENTES ====");
            System.out.println("1. Listar clientes");
            System.out.println("2. Agregar cliente");
            System.out.println("3. Actualizar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción (1-5): ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> {
                        List<Cliente> clientes = clienteDAO.obtenerClientes();
                        if (clientes.isEmpty()) {
                            System.out.println("⚠️ No hay clientes registrados.");
                        } else {
                            System.out.println("📋 Lista de clientes:");
                            clientes.forEach(System.out::println);
                        }
                    }
                    case 2 -> {
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();

                        Cliente nuevoCliente = new Cliente(nombre, apellido);
                        clienteDAO.insertarCliente(nuevoCliente);
                        System.out.println("✅ Cliente agregado correctamente.");
                    }
                    case 3 -> {
                        int idActualizar = solicitarEntero("ID del cliente a actualizar: ");
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nuevo apellido: ");
                        String nuevoApellido = scanner.nextLine();

                        Cliente clienteActualizado = new Cliente(idActualizar, nuevoNombre, nuevoApellido);
                        clienteDAO.actualizarCliente(clienteActualizado);
                        System.out.println("✅ Cliente actualizado correctamente.");
                    }
                    case 4 -> {
                        int idEliminar = solicitarEntero("ID del cliente a eliminar: ");
                        clienteDAO.eliminarCliente(idEliminar);
                        System.out.println("🗑️ Cliente eliminado (si existía).");
                    }
                    case 5 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("❗ Opción inválida. Por favor, ingrese un número del 1 al 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❗ Entrada inválida. Por favor, ingrese un número.");
                opcion = -1;
            }
        } while (opcion != 5);
    }

        public static void menuPedido() throws SQLException {
            Scanner sc = new Scanner(System.in);
            PedidoDAO pedidoDAO = new PedidoDAO();
            int opcion;

            do {
                System.out.println("\n--- SUBMENÚ DE PEDIDO ---");
                System.out.println("1. Insertar pedido");
                System.out.println("2. Listar pedidos");
                System.out.println("3. Actualizar pedido");
                System.out.println("4. Eliminar pedido");
                System.out.println("5. Volver al menú principal");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.print("ID del cliente: ");
                        int idCliente = sc.nextInt();
                        sc.nextLine(); // limpiar buffer
                        System.out.print("Fecha (YYYY-MM-DD): ");
                        String fecha = sc.nextLine();
                        pedidoDAO.insertarPedido(new Pedido(0, idCliente, fecha));
                        break;

                    case 2:
                        List<Pedido> pedidos = pedidoDAO.obtenerPedidos();
                        for (Pedido p : pedidos) {
                            System.out.println(p);
                        }
                        break;

                    case 3:
                        System.out.print("ID del pedido a actualizar: ");
                        int idActualizar = sc.nextInt();
                        System.out.print("Nuevo ID de cliente: ");
                        int nuevoCliente = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nueva fecha (YYYY-MM-DD): ");
                        String nuevaFecha = sc.nextLine();
                        pedidoDAO.actualizarPedido(new Pedido(idActualizar, nuevoCliente, nuevaFecha));
                        break;

                    case 4:
                        System.out.print("ID del pedido a eliminar: ");
                        int idEliminar = sc.nextInt();
                        pedidoDAO.eliminarPedido(idEliminar);
                        break;

                    case 5:
                        System.out.println("Volviendo al menú principal...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } while (opcion != 5);
        }


        public static void menuDetallePedido() {
        DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();
        Scanner scanner = new Scanner(System.in);
            int opcion;

        do {
            System.out.println("\n--- SUBMENÚ DETALLE PEDIDO ---");
            System.out.println("1. Insertar detalle de pedido");
            System.out.println("2. Mostrar todos los detalles de pedido");
            System.out.println("3. Actualizar un detalle de pedido");
            System.out.println("4. Eliminar un detalle de pedido");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el ID del pedido: ");
                    int idPedido = scanner.nextInt();

                    System.out.print("Ingrese el ID del producto: ");
                    int idProducto = scanner.nextInt();

                    System.out.print("Ingrese la cantidad: ");
                    int cantidad = scanner.nextInt();

                    System.out.print("Ingrese el precio unitario: ");
                    double precioUnitario = scanner.nextDouble();

                    detallePedidoDAO.insertarDetallePedido(idPedido, idProducto, cantidad, precioUnitario);
                    break;

                case 2:
                    detallePedidoDAO.obtenerDetallesPedido();
                    break;

                case 3:
                    System.out.print("Ingrese ID del detalle de pedido a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    System.out.print("Ingrese nuevo ID del pedido: ");
                    int nuevoIdPedido = scanner.nextInt();
                    System.out.print("Ingrese nuevo ID del producto: ");
                    int nuevoIdProducto = scanner.nextInt();
                    System.out.print("Ingrese nueva cantidad: ");
                    int nuevaCantidad = scanner.nextInt();
                    detallePedidoDAO.actualizarDetallePedido(idActualizar, nuevoIdPedido, nuevoIdProducto, nuevaCantidad);
                    break;

                case 4:
                    System.out.print("Ingrese ID del detalle de pedido a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    detallePedidoDAO.eliminarDetallePedido(idEliminar);
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }


    private static void menuVenta() {
        VentaDAO ventaDAO = new VentaDAO();
        int opcion;
        do {
            System.out.println("\n--- SUBMENÚ: GESTIÓN DE VENTAS ---");
            System.out.println("1. Insertar venta");
            System.out.println("2. Mostrar ventas");
            System.out.println("3.");
            System.out.println("4. Eliminar venta");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ventaDAO.insertarVenta();
                    break;
                case 2:
                    ventaDAO.obtenerVentas();
                    break;
                case 3:
                    System.out.print("Esta opción está demás, por favor introduzca otro número");
                    break;
                case 4:
                    System.out.print("Ingrese el ID de la venta a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    ventaDAO.eliminarVenta(idEliminar);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 0);
    }

    private static void menuDetalleVenta() {
        DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
        int opcion;

        do {
            System.out.println("\n--- SUBMENÚ DETALLE VENTA ---");
            System.out.println("1. Insertar detalle de venta");
            System.out.println("2. Mostrar todos los detalles de venta");
            System.out.println("3. Eliminar un detalle de venta");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ID de la venta: ");
                    int idVenta = scanner.nextInt();
                    System.out.print("Ingrese ID del producto: ");
                    int idProducto = scanner.nextInt();
                    System.out.print("Ingrese cantidad: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer
                    detalleVentaDAO.insertarDetalleVenta(idVenta, idProducto, cantidad);
                    break;

                case 2:
                    List<DetalleVenta> lista = detalleVentaDAO.obtenerDetallesVenta();
                    if (lista.isEmpty()) {
                        System.out.println("No hay detalles de venta registrados.");
                    } else {
                        System.out.println("Lista de detalles de venta:");
                        for (DetalleVenta dv : lista) {
                            System.out.println(dv);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Ingrese ID del detalle de venta a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer
                    detalleVentaDAO.eliminarDetalleVenta(idEliminar);
                    break;

                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
}
