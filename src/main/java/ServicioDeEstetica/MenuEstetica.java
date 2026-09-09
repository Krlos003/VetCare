package ServicioDeEstetica;

import SistemaRegistro.GestionClientesBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuEstetica {


    public static void main(String[] args) {
        Connection conn = GestionClientesBD.obtenerConexion();
        if (conn == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

    }

        public static void mostrarMenu(Connection conn, Scanner scanner) {
        int opcion;

        do {
            System.out.println("\n=============================================");
            System.out.println("     MÓDULO DE PELUQUERÍA Y ESTÉTICA");
            System.out.println("=============================================");
            System.out.println("1. Registrar nueva cita de estética");
            System.out.println("2. Ver historial de servicios (Fidelización)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor ingrese un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    registrarCita(conn, scanner);
                    break;
                case 2:
                    mostrarHistorialBD(conn);
                    break;
                case 3:
                    System.out.println("\nSaliendo del módulo... ¡Datos guardados exitosamente!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 3);

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }

    }

    private static void registrarCita(Connection conn, Scanner scanner) {
        System.out.println("\n---------------------------------------------");
        System.out.println("          REGISTRO DE NUEVA CITA");
        System.out.println("---------------------------------------------");
        System.out.print("Doc/Cedula del dueño: ");
        String documento = scanner.nextLine();

        String sqlBuscarMascotas = "SELECT id, nombre, especie, raza FROM mascotas WHERE cliente_documento = ? Order by id asc";
        boolean tieneMascotas = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlBuscarMascotas)) {
            pstmt.setString(1, documento);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    System.out.println("\n--- MASCOTAS REGISTRADAS DEL CLIENTE ---");;
                    while (rs.next()) {
                        tieneMascotas = true;
                        int idMascota = rs.getInt("id");
                        String nombreMascota = rs.getString("nombre");
                        String especieMascota = rs.getString("especie");
                        String razaMascota = rs.getString("raza");
                        System.out.println(idMascota + ". " + nombreMascota + " (" + especieMascota + ", " + razaMascota + ")");
                    }
                }
            }
                
        }catch (SQLException e) {
                    System.out.println("Error al buscar mascotas: " + e.getMessage());
                    return;
        }            
         
        if (!tieneMascotas) {
            System.out.println("No se encontraron mascotas registradas para este dueño.");
            return;
        }
        
        System.out.print("\nIngrese el ID de la mascota para la cita: ");
        int idMascota = scanner.nextInt();
        scanner.nextLine();

        String sqlValidarMascota = "SELECT nombre, especie, raza FROM mascotas WHERE id = ? AND cliente_documento = ?";
        String mascota = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlValidarMascota)) {
            pstmt.setInt(1, idMascota);
            pstmt.setString(2, documento);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    mascota = rs.getString("nombre");
                    System.out.println("Mascota seleccionada: " + mascota);
                } else {
                    System.out.println("No se encontró una mascota con ese ID para el dueño proporcionado.");
                    return;
                }   
            }
        } catch (SQLException e) {
            System.out.println("Error al validar la mascota: " + e.getMessage());
            return;
        }

        System.out.println("\nSeleccione el tamaño de la mascota:");
        System.out.println("1. Pequeño Peludo (Recargo: $10,000)");
        System.out.println("2. Mediano        (Recargo: $20,000)");
        System.out.println("3. Grande         (Recargo: $50,000)");
        System.out.println("4. Sin recargo    (Recargo: $0)");
        System.out.print("Opción (1-4): ");
        int opcTamano = scanner.nextInt();
        scanner.nextLine();

        String tamano;
        double recargoTamano;

        if (opcTamano == 1) {
            tamano = "Pequeño Peludo";
            recargoTamano = 10000;
        } else if (opcTamano == 2) {
            tamano = "Mediano";
            recargoTamano = 20000;
        } else if (opcTamano == 3) {
            tamano = "Grande";
            recargoTamano = 50000;
        } else {
            tamano = "Sin recargo";
            recargoTamano = 0;
        }

        System.out.println("\nSeleccione el servicio a realizar:");
        System.out.println("1. Servicio Básico       (+$20,000)");
        System.out.println("2. Servicio Avanzado     (+$30,000)");
        System.out.println("3. Servicio Premium      (+$40,000)");
        System.out.print("Opción (1-3): ");
        int opcServicio = scanner.nextInt();
        scanner.nextLine();

        boolean servicioBasico = false;
        boolean servicioAvanzado = false;
        boolean servicioPremium = false;
        double costoServicio = 0;

        switch (opcServicio) {
            case 1:
                servicioBasico = true;
                costoServicio = 20000;
                break;
            case 2:
                servicioAvanzado = true;
                costoServicio = 30000;
                break;
            case 3:
                servicioPremium = true;
                costoServicio = 40000;
                break;
        }
        

        String tipoServicio = obtenerTipoServicio(servicioBasico, servicioAvanzado, servicioPremium);
        double precioBase = costoServicio;
        double total = precioBase + recargoTamano;
        

       String sqlGuardarBD = "INSERT INTO servicios_esteticos (mascota_id, tipo_servicio, costo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmtBD = conn.prepareStatement(sqlGuardarBD)) {
            pstmtBD.setInt(1, idMascota);
            pstmtBD.setString(2, tipoServicio);
            pstmtBD.setDouble(3, total);
            pstmtBD.executeUpdate();
            System.out.println("\n Servicio registrado exitosamente en Supabase.");
        } catch (SQLException e) {
            System.err.println(" Error al registrar el servicio en la base de datos: " + e.getMessage());
            return;
        }

    
       
        System.out.println("\n=============================================");
        System.out.println("           DESGLOSE Y RESUMEN TOTAL          ");
        System.out.println("=============================================");
        System.out.println("• Mascota:            " + mascota);
        System.out.println("• Tarifa Base:        $" + costoServicio);
        System.out.println("• Ajuste Tamaño (" + tamano + "): $" + recargoTamano);
        System.out.println("---------------------------------------------");
        System.out.println("  VALOR TOTAL A PAGAR: $" + total);
        System.out.println("=============================================\n");
    }

    private static String obtenerTipoServicio(boolean esBasico, boolean esAvanzado, boolean esPremium) {
        if (esBasico) {
            return "Básico";
        } else if (esAvanzado) {
            return "Avanzado";
        } else {
            return "Premium";
        }
    }

   private static void mostrarHistorialBD(Connection conn) {
        System.out.println("\n=========================================================================");
        System.out.println("           HISTORIAL DE SERVICIOS ESTÉTICOS (SUPABASE)                   ");
        System.out.println("=========================================================================");

        String sqlHistorial = "SELECT s.id, m.nombre AS mascota, c.nombre AS dueno, c.documento, " +
                             "s.tipo_servicio, s.costo, s.fecha_servicio " +
                             "FROM servicios_esteticos s " +
                             "INNER JOIN mascotas m ON s.mascota_id = m.id " +
                             "INNER JOIN clientes c ON m.cliente_documento = c.documento " +
                             "ORDER BY s.id DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlHistorial);
             ResultSet rs = pstmt.executeQuery()) {

            boolean hayRegistros = false;
            while (rs.next()) {
                hayRegistros = true;
                System.out.println("ID Cita: " + rs.getLong("id") +
                        " | Mascota: " + rs.getString("mascota") +
                        " | Dueño: " + rs.getString("dueno") + " (Doc: " + rs.getString("documento") + ")" +
                        " | Servicio: " + rs.getString("tipo_servicio") +
                        " | Costo: $" + rs.getDouble("costo") +
                        " | Fecha: " + rs.getTimestamp("fecha_servicio"));
            }

            if (!hayRegistros) {
                System.out.println("No hay ningún servicio estético registrado en la base de datos.");
            }

        } catch (SQLException e) {
            System.err.println(" Error al consultar el historial desde Supabase: " + e.getMessage());
        }
    }
}