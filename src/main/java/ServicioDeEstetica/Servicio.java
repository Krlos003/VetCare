package ServicioDeEstetica;

import java.io.Serializable;

public class Servicio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String tipoServicio;
    protected double precioBase;
    protected String nombreMascota;
    protected String tamanoMascota;

    public Servicio(String tipoServicio, double precioBase, String nombreMascota, String tamanoMascota) {
        this.tipoServicio = tipoServicio;
        this.precioBase = precioBase;
        this.nombreMascota = nombreMascota;
        this.tamanoMascota = tamanoMascota;
    }

    public double calcularPrecioTotal() {
        double recargo = 0.0;
        if (tamanoMascota.equalsIgnoreCase("Mediano")) {
            recargo = 5000;
        } else if (tamanoMascota.equalsIgnoreCase("Grande")) {
            recargo = 10000;
        }
        return precioBase + recargo;
    }

    @Override
    public String toString() {
        return "Mascota: " + nombreMascota + " | Servicio: " + tipoServicio + 
               " | Tamaño: " + tamanoMascota + " | Total: $" + calcularPrecioTotal();
    }
}