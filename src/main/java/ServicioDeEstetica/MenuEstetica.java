package ServicioDeEstetica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuEstetica {
    private static final String ARCHIVO_HISTORIAL = "historial_estetica.dat";
    private static List<CitaEstetica> historial = new ArrayList<>();

    public static void main(String[] args) {
        cargarHistorial();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MÓDULO DE PELUQUERÍA Y ESTÉTICA ---");
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
                    registrarCita(scanner);
                    break;
                case 2:
                    mostrarHistorial();
                    break;
                case 3:
                    guardarHistorial();
                    System.out.println("Saliendo del módulo... ¡Datos guardados!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    private static void registrarCita(Scanner scanner) {
        System.out.print("Nombre de la mascota: ");
        String mascota = scanner.nextLine();

        System.out.print("Tamaño (Pequeño / Mediano / Grande): ");
        String tamano = scanner.nextLine();

        System.out.print("¿Incluye baño? (si/no): ");
        boolean baño = scanner.nextLine().equalsIgnoreCase("si");

        System.out.print("¿Incluye corte? (si/no): ");
        boolean corte = scanner.nextLine().equalsIgnoreCase("si");

        System.out.print("Fecha y Hora de la cita (Ej: 2026-09-01 10:00 AM): ");
        String fechaHora = scanner.nextLine();

        ServicioEstetica servicio = new ServicioEstetica(mascota, tamano, baño, corte);
        CitaEstetica cita = new CitaEstetica(fechaHora, servicio);

        historial.add(cita);
        guardarHistorial();
        System.out.println("\n✓ Servicio registrado exitosamente:");
        System.out.println(cita);
    }

    private static void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL DE SERVICIOS REGISTRADOS ===");
        if (historial.isEmpty()) {
            System.out.println("No hay servicios registrados aún.");
        } else {
            for (int i = 0; i < historial.size(); i++) {
                System.out.println((i + 1) + ". " + historial.get(i));
            }
        }
    }

    private static void guardarHistorial() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_HISTORIAL))) {
            oos.writeObject(historial);
        } catch (IOException e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void cargarHistorial() {
        File file = new File(ARCHIVO_HISTORIAL);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            historial = (List<CitaEstetica>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar historial previo: " + e.getMessage());
        }
    }
}