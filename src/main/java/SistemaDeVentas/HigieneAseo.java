package SistemaDeVentas;

public class HigieneAseo extends Producto {

    private String especie;
    private String marca;


public HigieneAseo(String nombre, double precio, int stock, String ubicacion, double contenido, UnidadMedida unidadMedida, String especie, String marca) {
        super(nombre, precio, stock, "Higiene y Aseo", ubicacion, contenido, unidadMedida);
        this.especie = especie;
        this.marca = marca;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Especie='" + especie + '\'' +
                ", Marca='" + marca + '\'' +
                '}';
    }
}