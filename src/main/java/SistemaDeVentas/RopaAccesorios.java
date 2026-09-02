package SistemaDeVentas;

public class RopaAccesorios extends Producto {
    private String talla;
    private String genero;
    private String especie;

    public RopaAccesorios(String nombre, double precio, int stock, String ubicacion, double contenido, UnidadMedida unidadMedida, String talla, String genero, String especie) {
        super(nombre, precio, stock, "Ropa y Accesorios", ubicacion, contenido, unidadMedida);
        this.talla = talla;
        this.genero = genero;
        this.especie = especie;
    }
    public String getTalla() {
        return talla;
    }
    public String getGenero() {
        return genero;
    }
    public String getEspecie() {
        return especie;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", talla='" + talla + '\'' +
                ", genero='" + genero + '\'' +
                ", especie='" + especie + '\'' +
                '}';
    }
}    