package SistemaDeVentas;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

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

                        System.out.println("Seleccione la categoría del producto:");
                        System.out.println("1. Medicamento");
                        System.out.println("2. Alimento");
                        System.out.println("3. Ropa y Accesorios");
                        System.out.println("4. Higiene y Aseo");
                        System.out.print("Opción: ");
                        int tipoOpcion = scanner.nextInt();
                        scanner.nextLine();

                        if (tipoOpcion < 1 || tipoOpcion > 4) {
                            System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                            break;
                        }

                        System.out.print("Ingrese el nombre del producto: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese el precio del producto: ");
                        double precio = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Ingrese el stock del producto: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Ingrese la ubicación del producto: ");
                        String ubicacion = scanner.nextLine();

                        System.out.print("Ingrese el contenido del producto: ");
                        double contenido = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Ingrese la unidad de medida del producto: ");
                        String simboloUnidad = scanner.nextLine();
                        Producto.UnidadMedida unidadMedida = Producto.UnidadMedida.valueOf(simboloUnidad.toUpperCase());

                        Producto producto = null;
                        switch (tipoOpcion) {
                            case 1:{

                                System.out.print("Ingrese el laboratorio del medicamento: ");
                                String laboratorio = scanner.nextLine();

                                System.out.print("Ingrese la fecha de vencimiento del medicamento (YYYY-MM-DD): ");
                                String fechaVencimientoStr = scanner.nextLine();
                                LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);

                                System.out.print("¿Requiere receta? (true/false): ");
                                boolean requiereReceta = scanner.nextBoolean();

                                producto = new Medicamento(nombre, precio, stock, ubicacion, contenido, unidadMedida, laboratorio, fechaVencimiento, requiereReceta);
                                break;
                            }
                            case 2:{

                                System.out.print("Ingrese la especie de la mascota: ");
                                String especie = scanner.nextLine();

                                System.out.print("Ingrese la edad de la mascota: "); 
                                String edad = scanner.nextLine();

                                producto = new Alimentos(nombre, precio, stock, ubicacion, contenido, unidadMedida, especie, edad);
                                break;
                            }
                            case 3:{

                                System.out.print("Ingrese la talla del producto: ");
                                String talla = scanner.nextLine();

                                System.out.print("Ingrese el género del producto: ");
                                String genero = scanner.nextLine();

                                System.out.print("Ingrese la especie del producto: ");
                                String especie = scanner.nextLine();

                                producto = new RopaAccesorios(nombre, precio, stock, ubicacion, contenido, unidadMedida, talla, genero, especie);
                                break;
                            }
                            case 4:{
                                System.out.print("Ingrese la especie del producto: ");
                                String especie = scanner.nextLine();

                                System.out.print("Ingrese la marca del producto: ");
                                String marca = scanner.nextLine();

                                producto = new HigieneAseo(nombre, precio, stock, ubicacion, contenido, unidadMedida, especie, marca);
                                break;
                            }
                        }

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