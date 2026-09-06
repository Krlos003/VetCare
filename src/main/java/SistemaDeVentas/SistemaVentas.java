package SistemaDeVentas;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class SistemaVentas {

    private static Inventario inventario = new Inventario();

    private static String recortarTexto(String texto, int longitudMaxima) {
        if (texto == null) {
            return "";
        }
        return texto.length() > longitudMaxima
                ? texto.substring(0, longitudMaxima - 3) + "..."
                : texto;
    }

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

                        System.out.print("Ingrese el cantidad del producto: (sin unidad de) ");
                        double contenido = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Ingrese la unidad de medida del producto: (ml/lt/g/lb/kg/und) ");
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

                                System.out.print("Ingrese la especie de la mascota: (perro/gato/otros) ");
                                String especie = scanner.nextLine();

                                System.out.print("Ingrese la edad de la mascota: (cachorro/adulto/anciano) "); 
                                String edad = scanner.nextLine();

                                producto = new Alimentos(nombre, precio, stock, ubicacion, contenido, unidadMedida, especie, edad);
                                break;
                            }
                            case 3:{

                                System.out.print("Ingrese la talla del producto: (XS/S/M/L/XL) ");
                                String talla = scanner.nextLine();

                                System.out.print("Ingrese el género del producto: (macho/hembra) ");
                                String genero = scanner.nextLine();

                                System.out.print("Ingrese la especie del producto: (perro/gato/otros) ");
                                String especie = scanner.nextLine();

                                producto = new RopaAccesorios(nombre, precio, stock, ubicacion, contenido, unidadMedida, talla, genero, especie);
                                break;
                            }
                            case 4:{
                                System.out.print("Ingrese la especie del producto: (perro/gato/otros) ");
                                String especie = scanner.nextLine();

                                System.out.print("Ingrese la marca del producto: ");
                                String marca = scanner.nextLine();

                                producto = new HigieneAseo(nombre, precio, stock, ubicacion, contenido, unidadMedida, especie, marca);
                                break;
                            }
                        }

                        producto.guardarEnBD();
                        System.out.println("Producto registrado con éxito: " + producto);
                        break;
                    case 4:
                    System.out.println("\n=========================================================================================");
                    System.out.println("                                CATÁLOGO DE PRODUCTOS                                    ");
                    System.out.println("=========================================================================================");

                    List<Producto> listaBD = inventario.obtenerProductosDesdeBD();

                    if (listaBD.isEmpty()) {
                        System.out.println("No hay productos registrados en la base de datos.");
                    } else {
                        // Cabecera de la tabla con anchos fijos
                        System.out.println(String.format("%-4s | %-28s | %-16s | %-10s | %-6s | %-12s", 
                                "N°", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK", "UBICACIÓN"));
                        System.out.println("-----------------------------------------------------------------------------------------");

                        // Filas formateadas
                        for (int i = 0; i < listaBD.size(); i++) {
                            Producto p = listaBD.get(i);
                            String categoria = p.getClass().getSimpleName();

                            System.out.println(String.format("%-4d | %-28s | %-16s | $%-9.2f | %-6d | %-12s",
                                    (i + 1),
                                    recortarTexto(p.getNombre(), 28),
                                    categoria,
                                    p.getPrecio(),
                                    p.getStock(),
                                    p.getUbicacion()));
                        }
        

                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Total de productos en catálogo: " + listaBD.size());
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