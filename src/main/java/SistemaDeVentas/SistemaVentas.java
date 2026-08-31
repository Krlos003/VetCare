package SistemaDeVentas;

import java.util.ArrayList;
import java.util.Scanner;

public class SistemaVentas {

    private static ArrayList<Producto> inventario = new ArrayList<>();

    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("Bienvenido al sistema de ventas");
            System.out.println("1. Registrar venta");
            System.out.println("2. Consultar ventas");
            System.out.println("3. Registrar producto");
            System.out.println("4. Consultar productos");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("proceso registrar ventas");
                        break;

                    case 2:
                        System.out.println("proceso consultar ventas");
                        break;
                    case 3:
                        System.out.println("REGISTRO DE PRODUCTO");

                        System.out.print("Ingrese el nombre del producto: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese el precio del producto: ");
                        double precio = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Ingrese el stock del producto: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();


                        Producto producto = new Producto(nombre, precio, stock);
                        inventario.add(producto);
                        producto.guardarEnBD();
                        System.out.println("Producto registrado con éxito: " + producto);
                        break;
                    case 4:
                        System.out.println("Catalogo de productos:");

                        Inventario inventarioBD = new Inventario();
                        ArrayList<Producto> productosBD = inventarioBD.obtenerTodos();
                        if (productosBD.isEmpty()) {
                            System.out.println("No hay productos registrados.");
                        } else {
                            for (Producto p : productosBD) {
                                System.out.println(p);
                            }
                        }
                        break;
                    case 5:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione una opción");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();
            }
        } while (opcion != 5);

        scanner.close();
    }
}