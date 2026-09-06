

import java.sql.Connection;
import java.util.Scanner;
import SistemaDeVentas.SistemaVentas;
import SistemaRegistro.GestionClientesBD;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

    Connection conn = GestionClientesBD.obtenerConexion();

        if (conn == null) {
            System.err.println(" Error: No se pudo conectar a la base de datos Supabase.");
            System.err.println("Por favor revisa tu conexión a internet o credenciales.");
            return;
        }
    
        do {
            System.out.println("Bienvenido al sistema de gestión de mascotas");
            System.out.println("1. Gestion de Clientes y Mascotas");
            System.out.println("2. Ventas de Productos y Alimentos");
            System.out.println("3. Atención Médica");
            System.out.println("4. Servicios de Peluquería y Estética");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                System.out.println();

                switch (opcion) {
                    case 1 : GestionClientesBD.mostrarMenu(conn, scanner); break;
                    case 2 : SistemaVentas.mostrarMenu(); break;
                    case 3 : System.out.println("ACA LLAMAMOS LA CLASE DE ATENCION MEDICA"); break;
                    case 4 : System.out.println("ACA LLAMAMOS LA CLASE DE PELUQUERIA"); break;
                    case 5 : System.out.println("Saliendo del sistema..."); break;
                    default : System.out.println("Opción inválida. Por favor, seleccione una opción válida."); break;
                
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();
            }
        } while (opcion != 5);

        scanner.close();
        } 

    }