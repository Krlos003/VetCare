package SistemaDeVentas;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Inventario {

    private static final String URL_BD = "jdbc:postgresql://db.tatezklzxsvgqnzgbzzq.supabase.co:5432/postgres?user=postgres&password=Proyecto12345";

    public ArrayList<Producto> obtenerTodos(){
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario order by id ASC";

        try (Connection conn = DriverManager.getConnection(URL_BD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(crearProducto(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos de la base de datos: " + e.getMessage());
        }

        return lista;
    }

    private Producto crearProducto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        double precio = rs.getDouble("precio");
        int stock = rs.getInt("stock");

        try {
            for (Constructor<?> constructor : Producto.class.getDeclaredConstructors()) {
                Class<?>[] params = constructor.getParameterTypes();
                if (params.length == 4
                        && params[0] == int.class
                        && params[1] == String.class
                        && params[2] == double.class
                        && params[3] == int.class) {
                    return (Producto) constructor.newInstance(id, nombre, precio, stock);
                }
            }

            Producto producto = Producto.class.getDeclaredConstructor().newInstance();
            setCampo(producto, "id", id);
            setCampo(producto, "nombre", nombre);
            setCampo(producto, "precio", precio);
            setCampo(producto, "stock", stock);
            return producto;
        } catch (ReflectiveOperationException e) {
            throw new SQLException("No se pudo crear el producto desde la base de datos", e);
        }
    }

    private void setCampo(Object objeto, String nombreCampo, Object valor) throws IllegalAccessException, NoSuchFieldException {
        Field campo = objeto.getClass().getDeclaredField(nombreCampo);
        campo.setAccessible(true);
        campo.set(objeto, valor);
    }
    
}

