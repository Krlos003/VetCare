package SistemaDeVentas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
public abstract class Producto {

    private static final String URL_BD = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres?user=postgres.tatezklzxsvgqnzgbzzq&password=Proyecto12345";

    public enum UnidadMedida {
        ML("ml"),
        LT("lt"),
        G("g"),
        LB("lb"),
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
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.contenido = contenido;
        this.unidadMedida = unidadMedida;
    }

    public boolean guardarEnBD() {
        String sql = "INSERT INTO inventario (" +

                "nombre, precio, stock, tipo, ubicacion, contenido, unidad_medida, " +
                "\"fechaVencimiento\", laboratorio, \"requiereReceta\", especie, marca, edad, talla, genero" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL_BD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            LocalDate fechaVencimiento = null;
            String laboratorio = null;
            Boolean requiereReceta = null;
            String especie = null;
            String marca = null;
            String edad = null;
            String talla = null;
            String genero = null;

            if (this instanceof Medicamento) {
                Medicamento med = (Medicamento) this;
                fechaVencimiento = med.getFechaVencimiento();
                laboratorio = med.getLaboratorio();
                requiereReceta = med.getRequiereReceta();
            } else if (this instanceof Alimentos) {
                Alimentos ali = (Alimentos) this;
                especie = ali.getEspecie();
                edad = ali.getEdad();
            } else if (this instanceof RopaAccesorios) {
                RopaAccesorios ropa = (RopaAccesorios) this;
                especie = ropa.getEspecie();
                talla = ropa.getTalla();
                genero = ropa.getGenero();
            } else if (this instanceof HigieneAseo) {
                HigieneAseo higiene = (HigieneAseo) this;
                marca = higiene.getMarca();
                especie = higiene.getEspecie();
            }

            stmt.setString(1, this.nombre);
            stmt.setDouble(2, this.precio);
            stmt.setInt(3, this.stock);
            stmt.setString(4, this.tipo);
            stmt.setString(5, this.ubicacion);
            stmt.setDouble(6, this.contenido);
            stmt.setString(7, this.unidadMedida.getSimbolo());
            stmt.setObject(8, fechaVencimiento);
            stmt.setString(9, laboratorio);
            stmt.setObject(10, requiereReceta);
            stmt.setString(11, especie);
            stmt.setString(12, marca);
            stmt.setString(13, edad);
            stmt.setString(14, talla);
            stmt.setString(15, genero);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡" + this.nombre + " guardado en la base de datos con éxito!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar en Supabase: " + e.getMessage());
            e.printStackTrace();
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
    public String getUbicacion() {
        return this.ubicacion;
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
