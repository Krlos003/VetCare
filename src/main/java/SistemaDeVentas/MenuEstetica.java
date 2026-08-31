package SistemaDeVentas;

import java.util.Scanner;

public class MenuEstetica {

    public static void ejecutarMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("   MODULO DE PELUQUERIA Y ESTETICA ");
        System.out.println("===========================================");
        
        System.out.print("Ingrese el nombre de la mascota: ");
        String nombreMascota = scanner.nextLine();

        System.out.print("Ingrese la especie o raza: ");
        String especie = scanner.nextLine();

        System.out.println("Seleccione el tamano de la mascota:");
        System.out.println("1. Pequeno (Sin recargo)");
        System.out.println("2. Mediano (+ $10,000)");
        System.out.println("3. Grande (+ $20,000)");
        System.out.print("Opcion: ");
        int opcionTamano = scanner.nextInt();
        
        String tamano = "Pequeno";
        if (opcionTamano == 2) {
            tamano = "Mediano";
        } else if (opcionTamano == 3) {
            tamano = "Grande";
        }

        System.out.print("¿Incluir servicio de Bano? ($15,000) [1=Si / 2=No]: ");
        boolean bano = scanner.nextInt() == 1;

        System.out.print("¿Incluir servicio de Corte? ($20,000) [1=Si / 2=No]: ");
        boolean corte = scanner.nextInt() == 1;

        double tarifaBase = 10000;

        ServicioEstetica servicio = new ServicioEstetica(tarifaBase, bano, corte, tamano);

        System.out.println("-------------------------------------------");
        System.out.println("         RESUMEN DEL SERVICIO");
        System.out.println("-------------------------------------------");
        System.out.println("Mascota: " + nombreMascota + " (" + especie + ")");
        servicio.mostrarDetalle();
        System.out.println("-------------------------------------------");
    }

    public static void main(String[] args) {
        ejecutarMenu();
    }
}