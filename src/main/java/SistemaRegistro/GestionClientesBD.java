package SistemaRegistro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionClientesBD {
    private static final String URL = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres";
    private static final String USUARIO = "postgres.tatezklzxsvgqnzgbzzq";
    private static final String PASSWORD = "Proyecto12345";

    public static Connection obtenerConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println(" Conexión exitosa a Supabase.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println(" No se encontró el driver JDBC de PostgreSQL.");
        } catch (SQLException e) {
            System.err.println(" Error al conectar con Supabase: " + e.getMessage());
        }
        return null;
    }

    public static void registrarCliente(Connection conn, Scanner scanner) {
        if (conn == null) {
            System.err.println(" Sin conexión activa a la base de datos.");
            return;
        }

        System.out.println("\n--- REGISTRO DE NUEVO CLIENTE ---");
        System.out.print("Documento de identidad (Cédula/DNI): ");
        String documento = scanner.nextLine().trim();

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();

        String sql = "INSERT INTO clientes (documento, nombre, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, documento);
            pstmt.setString(2, nombre);
            pstmt.setString(3, telefono);
            pstmt.setString(4, email);
            pstmt.setString(5, direccion);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println(" Client guardado con éxito. Doc: " + documento);
            }
        } catch (SQLException e) {
            System.err.println(" Error al guardar en la BD: " + e.getMessage());
        }
    }

    public static void registrarMascota(Connection conn, Scanner scanner) {
        if (conn == null) {
            System.err.println(" Sin conexión activa a la base de datos.");
            return;
        }

        System.out.println("\n--- REGISTRO DE NUEVA MASCOTA ---");
        
        consultarClientes(conn);
        
        System.out.print("\nIngrese el Documento del dueño (cliente): ");
        String clienteDocumento = scanner.nextLine().trim();

        System.out.print("Nombre de la mascota: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Especie (ej. Perro, Gato): ");
        String especie = scanner.nextLine().trim();

        System.out.print("Raza: ");
        String raza = scanner.nextLine().trim();

        System.out.print("Edad: ");
        String edad = scanner.nextLine().trim();
        

        String sql = "INSERT INTO mascotas (nombre, especie, raza, edad, cliente_documento) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, especie);
            pstmt.setString(3, raza);
            pstmt.setString(4, edad);
            pstmt.setString(5, clienteDocumento);

            pstmt.executeUpdate();
            System.out.println(" Mascota registrada exitosamente y vinculada al cliente Doc: " + clienteDocumento);
        } catch (SQLException e) {
            System.err.println(" Error al registrar la mascota: " + e.getMessage());
        }
    }

    public static void consultarClientes(Connection conn) {
        if (conn == null) {
            System.err.println(" Sin conexión activa a la base de datos.");
            return;
        }

        String sql = "SELECT documento, nombre, telefono, email, direccion FROM clientes ORDER BY nombre ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- LISTA DE CLIENTES ---");
            boolean hayClientes = false;

            while (rs.next()) {
                hayClientes = true;
                String documento = rs.getString("documento");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String direccion = rs.getString("direccion");

                System.out.println("Doc/Cédula: " + documento +
                        " | Nombre: " + nombre +
                        " | Teléfono: " + (telefono != null ? telefono : "N/A") +
                        " | Email: " + (email != null ? email : "N/A") +
                        " | Dirección: " + (direccion != null ? direccion : "N/A"));
            }

            if (!hayClientes) {
                System.out.println("No hay clientes registrados en la base de datos.");
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar clientes: " + e.getMessage());
        }
    }

    public static void consultarMascotas(Connection conn) {
        if (conn == null) {
            System.err.println(" Sin conexión activa a la base de datos.");
            return;
        }

        String sql = "SELECT m.id AS mascota_id, m.nombre AS mascota, m.especie, m.raza, m.edad, " +
                     "c.documento AS dueno_doc, c.nombre AS dueno_nombre " +
                     "FROM mascotas m " +
                     "LEFT JOIN clientes c ON m.cliente_documento = c.documento " +
                     "ORDER BY m.id ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- LISTA DE MASCOTAS ---");
            boolean hayMascotas = false;

            while (rs.next()) {
                hayMascotas = true;
                long mascotaId = rs.getLong("mascota_id");
                String nombreMascota = rs.getString("mascota");
                String especie = rs.getString("especie");
                String raza = rs.getString("raza");
                String edad = rs.getString("edad");
                String duenoDoc = rs.getString("dueno_doc");
                String duenoNombre = rs.getString("dueno_nombre");

                System.out.println("ID Mascota: " + mascotaId +
                        " | Mascota: " + nombreMascota +
                        " | Especie: " + especie +
                        " | Raza: " + (raza != null ? raza : "N/A") +
                        " | Edad: " + edad + " años" +
                        " | Dueño: " + (duenoNombre != null ? duenoNombre + " (Doc: " + duenoDoc + ")" : "Sin dueño asignado"));
            }

            if (!hayMascotas) {
                System.out.println("No hay mascotas registradas en la base de datos.");
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar mascotas: " + e.getMessage());
        }
    }

    public static void mostrarMenu(Connection conn, Scanner sc) {
        int opcion;
        do {
            System.out.println("\n== Menu VetCare ==");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Mascota");
            System.out.println("3. Listar Clientes");
            System.out.println("4. Listar Mascotas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    registrarCliente(conn, sc);
                    break;
                case 2:
                    registrarMascota(conn, sc);
                    break;
                case 3:
                    consultarClientes(conn);
                    break;
                case 4:
                    consultarMascotas(conn);
                    break;
                case 0:
                    System.out.println("Gracias por usar VetCare.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Connection conn = obtenerConexion();

        if (conn == null) {
            System.err.println(" No se pudo iniciar el programa debido a fallos en la conexión.");
            sc.close();
            return;
        }

        mostrarMenu(conn, sc);

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
        sc.close();
    }
}