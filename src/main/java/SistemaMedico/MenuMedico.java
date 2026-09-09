package SistemaMedico;

import SistemaRegistro.GestionClientesBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class MenuMedico {
    private static Connection conn;

    private int idServicio;
    private String descripcion;
    private double costoAtencion;
    private Date fecha;
    private String nombreMascota;
    private String diagnostico;

    public static void main(String[] args) {
        conn = GestionClientesBD.obtenerConexion();
        if (conn == null) {
            System.out.println("No se pudo establecer conexión con la base de datos.");
            return;
        }

        mostrarMenuMedico();
    }

    public static void mostrarMenuMedico() {
      conn = GestionClientesBD.obtenerConexion();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) {
            System.out.println("Bienvenido al sistema de atención médica");
            System.out.println("1. Registrar atención médica");
            System.out.println("2. Consultar historial médico");
            System.out.println("3. Volver al menú principal");

            System.out.print("Seleccione una opción: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor ingrese un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    RegistrarAtencionMedica(conn, scanner);
                    break;
                case 2:
                    consultarHistorialMedico(conn, scanner);
                    break;
                case 3:
                    System.out.println("\nSaliendo del módulo... ¡Datos guardados exitosamente!");
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    public static void RegistrarAtencionMedica(Connection conn, Scanner scanner) {
        System.out.println("Ingrese Doc/Cedula del dueño");
        String documento = scanner.nextLine();

        String sqlBuscarMascotas = "SELECT id, nombre, especie, raza FROM mascotas WHERE cliente_documento = ? ORDER BY id ASC";
        boolean tieneMascotas = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlBuscarMascotas)) {
            pstmt.setString(1, documento);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    System.out.println("\n--- MASCOTAS REGISTRADAS DEL CLIENTE ---");
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
        } catch (SQLException e) {
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

        System.out.print("Ingrese la descripción del servicio: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese el costo de la atención médica: ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor ingrese un número válido para el costo: ");
            scanner.next();
        }
        double costoAtencion = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Ingrese el diagnóstico: ");
        String diagnostico = scanner.nextLine();

        System.out.print("Requiere receta? (true/false): ");
        boolean requiereReceta = scanner.nextBoolean();
        scanner.nextLine();

        String recetaMedicaBD = null;
        if (requiereReceta) {
            System.out.print("Ingrese el nombre del medicamento: ");
            String nombreMedicamento = scanner.nextLine();

            System.out.print("Ingrese la dosis del medicamento: ");
            String dosisMedicamento = scanner.nextLine();

            System.out.print("Ingrese la frecuencia del medicamento: ");
            String frecuenciaMedicamento = scanner.nextLine();

            System.out.print("Ingrese la duración del tratamiento (en días): ");
            int duracionTratamiento = scanner.nextInt();
            scanner.nextLine();

            recetaMedicaBD = nombreMedicamento + " - Dosis: " + dosisMedicamento
                    + " | Frecuencia: " + frecuenciaMedicamento
                    + " | Duración: " + duracionTratamiento + " días";
        }

        String sqlInsertarAtencion = "INSERT INTO atencion_medica (descripcion, costo_atencion, fecha, mascota_id, diagnostico, requiere_receta, receta_medica) VALUES (?, ?, NOW(), ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertarAtencion)) {
            pstmt.setString(1, descripcion);
            pstmt.setDouble(2, costoAtencion);
            pstmt.setInt(3, idMascota);
            pstmt.setString(4, diagnostico);
            pstmt.setBoolean(5, requiereReceta);
            pstmt.setString(6, recetaMedicaBD);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Atención médica registrada exitosamente para la mascota: " + mascota);
            } else {
                System.out.println("No se pudo registrar la atención médica.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar la atención médica: " + e.getMessage());
        }
    }

      public static void consultarHistorialMedico(Connection conn, Scanner scanner) {
         System.out.println("\n--- CONSULTA DE HISTORIAL MÉDICO ---");
         System.out.print("Ingrese el documento/cédula del dueño: ");
         String documento = scanner.nextLine();

         String sqlMascotas = "SELECT id, nombre, especie, raza FROM mascotas WHERE cliente_documento = ? ORDER BY id ASC";
         boolean tieneMascotas = false;

         try (PreparedStatement pstmt = conn.prepareStatement(sqlMascotas)) {
        pstmt.setString(1, documento);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.isBeforeFirst()) {
                System.out.println("\nSeleccione la mascota para ver su historial:");
                while (rs.next()) {
                    tieneMascotas = true;
                    int idMascota = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String especie = rs.getString("especie");
                    String raza = rs.getString("raza");
                    System.out.println(idMascota + ". " + nombre + " (" + especie + " - " + raza + ")");
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar las mascotas del cliente: " + e.getMessage());
        return;
    }

    if (!tieneMascotas) {
        System.out.println("No se encontraron mascotas registradas para la cédula ingresada.");
        return;
    }


    System.out.print("\nIngrese el ID de la mascota: ");
    while (!scanner.hasNextInt()) {
        System.out.print("Ingrese un ID numérico válido: ");
        scanner.next();
    }

    int idMascota = scanner.nextInt();
    scanner.nextLine();

    String sqlHistorial = "SELECT a.id, a.fecha, a.descripcion, a.diagnostico, a.costo_atencion, a.requiere_receta, a.receta_medica, m.nombre AS nombre_mascota " +
                         "FROM atencion_medica a " +
                         "INNER JOIN mascotas m ON a.mascota_id = m.id " +
                         "WHERE a.mascota_id = ? AND m.cliente_documento = ? " +
                         "ORDER BY a.fecha DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sqlHistorial)) {
        pstmt.setInt(1, idMascota);
        pstmt.setString(2, documento);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (!rs.isBeforeFirst()) {
                System.out.println("\nNo hay registros médicos o atenciones asociadas a esta mascota.");
                return;
            }

            boolean encabezadoImpreso = false;

            while (rs.next()) {
                if (!encabezadoImpreso) {
                    System.out.println("\n========================================================");
                    System.out.println("   HISTORIAL MÉDICO DE: " + rs.getString("nombre_mascota").toUpperCase());
                    System.out.println("========================================================");
                    encabezadoImpreso = true;
                }

                int idAtencion = rs.getInt("id");
                String fecha = rs.getString("fecha");
                String descripcion = rs.getString("descripcion");
                String diagnostico = rs.getString("diagnostico");
                double costo = rs.getDouble("costo_atencion");
                boolean tieneReceta = rs.getBoolean("requiere_receta");
                String receta = rs.getString("receta_medica");

                System.out.println("\n[ ID Atención: " + idAtencion + " | Fecha: " + fecha + " ]");
                System.out.println(" -> Servicio/Descripción : " + descripcion);
                System.out.println(" -> Diagnóstico           : " + diagnostico);
                System.out.println(" -> Costo de Atención    : $" + String.format("%.2f", costo));

                if (tieneReceta) {
                    System.out.println(" -> Receta Médica        : " + (receta != null ? receta : "Sin detalles registrados"));
                } else {
                    System.out.println(" -> Receta Médica        : No requirió prescripción");
                }
                System.out.println("--------------------------------------------------------");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar el historial médico: " + e.getMessage());
    }
}

    public MenuMedico(int idServicio, String descripcion, double costoAtencion, String nombreMascota, String diagnostico, boolean requiereReceta) {
        this.idServicio = idServicio;
        this.descripcion = descripcion;
        this.costoAtencion = costoAtencion;
        this.fecha = new Date();
        this.nombreMascota = nombreMascota;
        this.diagnostico = diagnostico;
    }

    public int getIdServicio() {
        return this.idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoAtencion() {
        return this.costoAtencion;
    }

    public void setCostoAtencion(double costoAtencion) {
        this.costoAtencion = costoAtencion;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public String getNombreMascota() {
        return this.nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "ID: " + this.idServicio + " | Mascota: " + this.nombreMascota + " | Servicio: " + this.descripcion + " | Diagnostico: " + this.diagnostico + " | Costo: $" + this.costoAtencion;
    }
}

