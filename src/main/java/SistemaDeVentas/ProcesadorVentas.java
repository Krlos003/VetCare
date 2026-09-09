package SistemaDeVentas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcesadorVentas {

    public static void registrarVenta(Connection conn, Scanner scanner) {
        System.out.println("\n--- REGISTRO DE VENTA Y FACTURACIÓN ---");
        System.out.print("Ingrese Cédula/Documento del Cliente: ");
        String documento = scanner.nextLine().trim();
String sqlMascotas = "SELECT id, nombre, especie FROM mascotas WHERE cliente_documento = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlMascotas)) {
            pstmt.setString(1, documento);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron mascotas registradas para este documento.");
                return;
            }
            System.out.println("\nSeleccione la mascota:");
            while (rs.next()) {
                System.out.println(rs.getLong("id") + ". " + rs.getString("nombre") + " (" + rs.getString("especie") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar cliente/mascota: " + e.getMessage());
            return;
        }

        System.out.print("Ingrese el ID de la mascota: ");
        long idMascota = scanner.nextLong();
        scanner.nextLine();

        String nombreCliente = "Cliente Registrado", nombreMascota = "", especie = "", edad = "", genero = "";
        String sqlInfo = "SELECT m.nombre AS mascota_nombre, m.especie, m.edad, m.genero, " +
                         "c.nombre AS cliente_nombre " +
                         "FROM mascotas m " +
                         "LEFT JOIN clientes c ON m.cliente_documento = c.documento " +
                         "WHERE m.id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInfo)) {
            pstmt.setLong(1, idMascota);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nombreMascota = rs.getString("mascota_nombre");
                especie = rs.getString("especie");
                edad = rs.getString("edad");
                if (rs.getString("cliente_nombre") != null) {
                    nombreCliente = rs.getString("cliente_nombre");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener detalles de la mascota: " + e.getMessage());
            return;
        }

        List<DetalleFactura> detalles = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n¿Qué desea añadir a la factura?");
            System.out.println("1. Producto de Inventario");
            System.out.println("2. Servicio de Atención Médica");
            System.out.println("3. Servicio de Peluquería / Estética");
            System.out.print("Seleccione una opción: ");
            int opcionTipo = scanner.nextInt();
            scanner.nextLine();

            switch (opcionTipo) {
                case 1:
                    agregarProducto(conn, scanner, detalles);
                    break;
                case 2:
                    agregarServicioMedico(conn, scanner, idMascota, detalles);
                    break;
                case 3:
                    agregarServicioEstetica(conn, scanner, idMascota, detalles);
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

            System.out.print("\n¿Desea agregar otro ítem a la factura? (true/false): ");
            while (!scanner.hasNextBoolean()) {
                System.out.print("Ingrese true o false: ");
                scanner.next();
            }
            continuar = scanner.nextBoolean();
            scanner.nextLine();
        }

        if (detalles.isEmpty()) {
            System.out.println("No se agregaron ítems. Venta cancelada.");
            return;
        }

        double total = 0;
        for (DetalleFactura d : detalles) {
            total += d.getSubtotal();
        }

        System.out.println("\nTOTAL A PAGAR: $" + String.format("%.2f", total));
        System.out.println("\n--- MÉTODO DE PAGO ---");
        System.out.println("1. Efectivo");
        System.out.println("2. Transferencia");
        System.out.println("3. Tarjeta (Débito / Crédito)");
        System.out.print("Seleccione opción: ");
        int met = scanner.nextInt();
        scanner.nextLine();

        String metodoPago = "";
        double recibido = total, cambio = 0.0;

        if (met == 1) {
            metodoPago = "Efectivo";
            System.out.print("Ingrese el monto entregado por el cliente: $");
            recibido = scanner.nextDouble();
            scanner.nextLine();
            while (recibido < total) {
                System.out.print("Monto insuficiente. Ingrese un valor mayor o igual a $" + String.format("%.2f", total) + ": $");
                recibido = scanner.nextDouble();
                scanner.nextLine();
            }
            cambio = recibido - total;
            System.out.println("Cambio a entregar: $" + String.format("%.2f", cambio));

        } else if (met == 2) {
            metodoPago = "Transferencia";
            System.out.print("¿Transferencia confirmada? (true/false): ");
            boolean confirmada = scanner.nextBoolean();
            scanner.nextLine();
            if (!confirmada) {
                System.out.println("Venta cancelada: Transferencia no confirmada.");
                return;
            }
        } else if (met == 3) {
            System.out.print("Tipo de tarjeta (1. Débito / 2. Crédito): ");
            int tTar = scanner.nextInt();
            scanner.nextLine();
            metodoPago = (tTar == 1) ? "Tarjeta Débito" : "Tarjeta Crédito";

            System.out.print("¿Pago con tarjeta aprobado? (true/false): ");
            boolean aprobada = scanner.nextBoolean();
            scanner.nextLine();
            if (!aprobada) {
                System.out.println("Venta cancelada: Transacción rechazada.");
                return;
            }
        }

    Factura factura = new Factura(documento, nombreCliente, idMascota, nombreMascota, especie, edad, genero, metodoPago, recibido, cambio, total, detalles);
        factura.guardarEnBD(conn);
    }

    private static void agregarProducto(Connection conn, Scanner scanner, List<DetalleFactura> lista) {
        String sql = "SELECT id, nombre, precio, stock FROM inventario WHERE stock > 0";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- PRODUCTOS EN INVENTARIO ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getLong("id") + " | " + rs.getString("nombre") + " - $" + rs.getDouble("precio") + " (Stock: " + rs.getLong("stock") + ")");
            }
            System.out.print("Ingrese el ID del producto: ");
            long idP = scanner.nextLong();
            System.out.print("Ingrese la cantidad: ");
            int cant = scanner.nextInt();
            scanner.nextLine();

            String sqlP = "SELECT nombre, precio, stock FROM inventario WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlP)) {
                pstmt.setLong(1, idP);
                ResultSet rsP = pstmt.executeQuery();
                if (rsP.next()) {
                    if (cant > rsP.getLong("stock")) {
                        System.out.println("Stock insuficiente en el inventario.");
                        return;
                    }
                    lista.add(new DetalleFactura("Producto", (int) idP, rsP.getString("nombre"), cant, rsP.getDouble("precio")));
                    System.out.println("¡Producto agregado a la factura!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }
    }

    private static void agregarServicioMedico(Connection conn, Scanner scanner, long idMascota, List<DetalleFactura> lista) {
        String sql = "SELECT id, descripcion, costo_atencion FROM atencion_medica WHERE mascota_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, idMascota);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\n--- ATENCIONES MÉDICAS DE LA MASCOTA ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | " + rs.getString("descripcion") + " - $" + rs.getDouble("costo_atencion"));
            }
            System.out.print("Ingrese el ID de la atención médica: ");
            int idAt = scanner.nextInt();
            scanner.nextLine();

            String sqlAt = "SELECT descripcion, costo_atencion FROM atencion_medica WHERE id = ?";
            try (PreparedStatement pstmtAt = conn.prepareStatement(sqlAt)) {
                pstmtAt.setInt(1, idAt);
                ResultSet rsA = pstmtAt.executeQuery();
                if (rsA.next()) {
                    lista.add(new DetalleFactura("Servicio Medico", idAt, "Consulta: " + rsA.getString("descripcion"), 1, rsA.getDouble("costo_atencion")));
                    System.out.println("¡Servicio médico agregado a la factura!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar atenciones médicas: " + e.getMessage());
        }
    }

    private static void agregarServicioEstetica(Connection conn, Scanner scanner, long idMascota, List<DetalleFactura> lista) {
        String sql = "SELECT id, tipo_servicio, costo FROM servicios_esteticos WHERE mascota_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, idMascota);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\n--- SERVICIOS DE ESTÉTICA DE LA MASCOTA ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getLong("id") + " | " + rs.getString("tipo_servicio") + " - $" + rs.getDouble("costo"));
            }
            System.out.print("Ingrese el ID del servicio de estética: ");
            long idEs = scanner.nextLong();
            scanner.nextLine();

            String sqlEs = "SELECT tipo_servicio, costo FROM servicios_esteticos WHERE id = ?";
            try (PreparedStatement pstmtEs = conn.prepareStatement(sqlEs)) {
                pstmtEs.setLong(1, idEs);
                ResultSet rsE = pstmtEs.executeQuery();
                if (rsE.next()) {
                    lista.add(new DetalleFactura("Servicio Estetica", (int) idEs, "Peluquería: " + rsE.getString("tipo_servicio"), 1, rsE.getDouble("costo")));
                    System.out.println("¡Servicio de estética agregado a la factura!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar servicios estéticos: " + e.getMessage());
        }
    }

    public static void consultarVentas(Connection conn, Scanner scanner) {
        System.out.println("\n--- CONSULTA DE VENTAS REGISTRADAS ---");
        System.out.print("Ingrese Cédula del Cliente: ");
        String doc = scanner.nextLine().trim();

        String sql = "SELECT v.id, v.fecha, v.metodo_pago, v.total, m.nombre AS mascota " +
                     "FROM ventas v INNER JOIN mascotas m ON v.mascota_id = m.id " +
                     "WHERE v.cliente_documento = ? ORDER BY v.fecha DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doc);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron facturas asociadas a esta cédula.");
                return;
            }

            while (rs.next()) {
                int idVenta = rs.getInt("id");
                System.out.println("\n--------------------------------------------------------");
                System.out.println(" FACTURA N°: " + idVenta + " | Fecha: " + rs.getString("fecha"));
                System.out.println(" Mascota    : " + rs.getString("mascota"));
                System.out.println(" Método Pago: " + rs.getString("metodo_pago"));
                System.out.println(" Total      : $" + String.format("%.2f", rs.getDouble("total")));
                System.out.println("--- DETALLES DE COMPRA ---");

                String sqlDet = "SELECT tipo_item, descripcion, cantidad, precio_unitario, subtotal FROM detalle_ventas WHERE venta_id = ?";
                try (PreparedStatement pD = conn.prepareStatement(sqlDet)) {
                    pD.setInt(1, idVenta);
                    ResultSet rsD = pD.executeQuery();
                    while (rsD.next()) {
                        System.out.println(" -> [" + rsD.getString("tipo_item") + "] " + rsD.getString("descripcion") + 
                                           " x" + rsD.getInt("cantidad") + " ($" + rsD.getDouble("precio_unitario") + ") = $" + rsD.getDouble("subtotal"));
                    }
                }
                System.out.println("--------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el historial de ventas: " + e.getMessage());
        }
    }
}