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
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarCita(scanner);
                    break;
                case 2:
                    mostrarHistorial();
                    break;
                case 3:
                    guardarHistorial();
                    System.out.println("\nSaliendo del módulo... ¡Datos guardados exitosamente!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    private static void registrarCita(Scanner scanner) {
        System.out.println("\n---------------------------------------------");
        System.out.println("          REGISTRO DE NUEVA CITA");
        System.out.println("---------------------------------------------");
        System.out.print("Nombre de la mascota: ");
        String mascota = scanner.nextLine();

        // Elección de tamaño con tarifas desglosadas
        System.out.println("\nSeleccione el tamaño de la mascota:");
        System.out.println("1. Pequeño  (Recargo: $0)");
        System.out.println("2. Mediano  (Recargo: $5,000)");
        System.out.println("3. Grande   (Recargo: $10,000)");
        System.out.print("Opción (1-3): ");
        int opcTamano = scanner.nextInt();
        scanner.nextLine();

        String tamano = "Pequeño";
        double recargoTamano = 0;

        if (opcTamano == 2) {
            tamano = "Mediano";
            recargoTamano = 5000;
        } else if (opcTamano == 3) {
            tamano = "Grande";
            recargoTamano = 10000;
        }

        // Elección del tipo de servicio con precios visibles
        System.out.println("\nSeleccione el servicio a realizar:");
        System.out.println("1. Solo Baño             (+$10,000)");
        System.out.println("2. Solo Corte            (+$15,000)");
        System.out.println("3. Baño y Corte Completo (+$25,000)");
        System.out.print("Opción (1-3): ");
        int opcServicio = scanner.nextInt();
        scanner.nextLine();

        boolean baño = false;
        boolean corte = false;
        double costoServicio = 0;

        switch (opcServicio) {
            case 1:
                baño = true;
                costoServicio = 10000;
                break;
            case 2:
                corte = true;
                costoServicio = 15000;
                break;
            case 3:
                baño = true;
                corte = true;
                costoServicio = 25000;
                break;
            default:
                System.out.println("Opción no válida. Se registrará solo baño por defecto.");
                baño = true;
                costoServicio = 10000;
                break;
        }

        System.out.print("\nFecha y Hora de la cita (Ej: 2026-09-01 10:00 AM): ");
        String fechaHora = scanner.nextLine();

        ServicioEstetica servicio = new ServicioEstetica(mascota, tamano, baño, corte);
        CitaEstetica cita = new CitaEstetica(fechaHora, servicio);

        historial.add(cita);
        guardarHistorial();

        // Resumen detallado de precios antes de guardar
        double precioBase = 20000;
        double total = servicio.calcularPrecioTotal();

        System.out.println("\n=============================================");
        System.out.println("           DESGLOSE Y RESUMEN TOTAL          ");
        System.out.println("=============================================");
        System.out.println("• Mascota:            " + mascota);
        System.out.println("• Tarifa Base:        $" + precioBase);
        System.out.println("• Ajuste Tamaño (" + tamano + "): +$" + recargoTamano);
        System.out.println("• Adicional Servicio: +$" + costoServicio);
        System.out.println("---------------------------------------------");
        System.out.println("  VALOR TOTAL A PAGAR: $" + total);
        System.out.println("=============================================\n");
    }

    private static void mostrarHistorial() {
        System.out.println("\n=============================================");
        System.out.println("     HISTORIAL DE SERVICIOS REGISTRADOS");
        System.out.println("=============================================");
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