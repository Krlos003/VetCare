package SistemaDeVentas;

public class Alimentos extends Producto {
    private String especie;
    private String edad;


    public Alimentos(String nombre, double precio, int stock, String ubicacion, double contenido, UnidadMedida unidadMedida, String especie, String edad) {
        super(nombre, precio, stock, "Alimento", ubicacion, contenido, unidadMedida);
        this.especie = especie;
        this.edad = edad;
    }
    
 public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", especie='" + especie + '\'' +
                ", edad='" + edad + '\'' +
                '}';
    }

}


