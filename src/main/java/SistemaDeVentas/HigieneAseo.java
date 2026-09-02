package SistemaDeVentas;

public class HigieneAseo extends Producto {

    private String Especie;
    private String Marca;


public HigieneAseo(String nombre, double precio, int stock, String ubicacion, double contenido, UnidadMedida unidadMedida, String Especie, String Marca) {
        super(nombre, precio, stock, "Higiene y Aseo", ubicacion, contenido, unidadMedida);
        this.Especie = Especie;
        this.Marca = Marca;
    }

    public String getEspecie() {
        return Especie;
    }

    public void setEspecie(String especie) {
        Especie = especie;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Especie='" + Especie + '\'' +
                ", Marca='" + Marca + '\'' +
                '}';
    }
}