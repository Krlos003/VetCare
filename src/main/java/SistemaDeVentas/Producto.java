package SistemaDeVentas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public abstract class Producto {

    private static final String URL_BD = "jdbc:postgresql://db.tatezklzxsvgqnzgbzzq.supabase.co:5432/postgres?user=postgres&password=Proyecto12345";

    public enum UnidadMedida {
        ML("ml"),
        L("l"),
        G("g"),
        KG("kg"),
        UNIDAD("und");

        private final String simbolo;

        UnidadMedida(String simbolo) {
            this.simbolo = simbolo;
        }

        public String getSimbolo() {
            return simbolo;
        }
    }

    private int id;
    private String nombre;
    private double precio;
    private int stock;
    protected String tipo;
    private String ubicacion;
    private double contenido;
    private UnidadMedida unidadMedida;

    public Producto(int id, String nombre, double precio, int stock, String tipo, String ubicacion, double contenido, UnidadMedida unidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.contenido = contenido;
        this.unidadMedida = unidadMedida;
}

    public Producto( String nombre, double precio, int stock, String tipo, String ubicacion, double contenido, UnidadMedida unidadMedida) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tipo = "prueba";
        this.ubicacion = ubicacion;
        this.contenido = contenido;
        this.unidadMedida = unidadMedida;
    }

    public boolean guardarEnBD() {
        String sql = "INSERT INTO inventario (nombre, precio, stock, tipo, ubicacion, contenido, unidad_medida) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL_BD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.nombre);
            stmt.setDouble(2, this.precio);
            stmt.setInt(3, this.stock);
            stmt.setString(4, this.tipo);
            stmt.setString(5, this.ubicacion);
            stmt.setDouble(6, this.contenido);
            stmt.setString(7, this.unidadMedida.getSimbolo());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡" + this.nombre + " guardado en la base de datos con éxito!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar en Supabase: " + e.getMessage());
            return false;
        }
        return false;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", ubicacion='" + ubicacion + '\'' +
                ", contenido=" + contenido +
                ", unidadMedida=" + unidadMedida +
                '}';
    }
}
