package SistemaDeVentas;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import SistemaDeVentas.Producto.UnidadMedida;

public class Inventario {

    private static final String URL_BD = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USUARIO = "postgres.tatezklzxsvgqnzgbzzq"; 
    private static final String PASSWORD = "Proyecto12345";

    public List<Producto> obtenerProductosDesdeBD() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario";

        try (Connection conn = DriverManager.getConnection(URL_BD, USUARIO, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
                
            System.out.println(" Conexión establecida con éxito a Supabase.");

            boolean hayFilas = false;
            while (rs.next()) {
                hayFilas = true;
                String nombre = null;
                
                try {
                    nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int stock = rs.getInt("stock");
                    String tipo = rs.getString("tipo");
                    String ubicacion = rs.getString("ubicacion");
                    double contenido = rs.getDouble("contenido");
                    
                    // Manejo seguro del Enum UnidadMedida
                    String unidadStr = rs.getString("unidad_medida");
                    UnidadMedida unidad = null;
                    if (unidadStr != null) {
                        try {
                            unidad = UnidadMedida.valueOf(unidadStr);
                        } catch (IllegalArgumentException e) {
                            unidad = null; 
                        }
                    }

                    Producto producto = null;
                    
                    
                    String tipoLimpio = (tipo != null) ? tipo.trim().toLowerCase() : "";
            
                    if (tipoLimpio.contains("medicamento")) {
                        Date fechaSql = rs.getDate("fechaVencimiento");
                        LocalDate fecha = (fechaSql != null) ? fechaSql.toLocalDate() : null;
                        String laboratorio = rs.getString("laboratorio");
                        boolean receta = rs.getBoolean("requiereReceta");

                        producto = new Medicamento(nombre, precio, stock, ubicacion, contenido, unidad, laboratorio, fecha, receta);

                    } else if (tipoLimpio.contains("alimento")) {
                        String especie = rs.getString("especie");
                        String edad = rs.getString("edad");

                        producto = new Alimentos(nombre, precio, stock, ubicacion, contenido, unidad, especie, edad);

                    } else if (tipoLimpio.contains("ropa") || tipoLimpio.contains("accesorio")) {
                        String especie = rs.getString("especie");
                        String talla = rs.getString("talla");
                        String genero = rs.getString("genero");

                        producto = new RopaAccesorios(nombre, precio, stock, ubicacion, contenido, unidad, especie, talla, genero);

                    } else if (tipoLimpio.contains("higiene") || tipoLimpio.contains("aseo")) {
                        String especie = rs.getString("especie");
                        String marca = rs.getString("marca");

                        // Instanciación directa y segura sin reflection
                        producto = new HigieneAseo(nombre, precio, stock, ubicacion, contenido, unidad, especie, marca);
                    }

                    if (producto != null) {
                        lista.add(producto);
                        System.out.println(" Cargado exitosamente: " + nombre);
                    } else {
                        System.out.println(" No se pudo asignar subclase para el tipo: " + tipo);
                    }

                } catch (Exception e) {
                    System.out.println(" Error construyendo el objeto '" + nombre + "': " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (!hayFilas) {
                System.out.println("La consulta se ejecutó bien, pero la tabla 'inventario' está vacía en Supabase.");
            }

        } catch (SQLException e) {
            System.out.println(" Error de conexión al consultar Supabase: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}