package ServicioDeEstetica;

import SistemaDeVentas.Servicio;

/**
 * Módulo del Estudiante 4: Peluquería y Estética
 * Hereda de la clase base Servicio.
 */
public class ServicioEstetica extends Servicio {
    private boolean incluyeBano;
    private boolean incluyeCorte;
    private String tamanoMascota;

    public ServicioEstetica(double precioBase, boolean incluyeBano, boolean incluyeCorte, String tamanoMascota) {
        super("Peluquería y Estética", precioBase);
        this.incluyeBano = incluyeBano;
        this.incluyeCorte = incluyeCorte;
        this.tamanoMascota = tamanoMascota;
    }

    public boolean isIncluyeBano() {
        return incluyeBano;
    }

    public void setIncluyeBano(boolean incluyeBano) {
        this.incluyeBano = incluyeBano;
    }

    public boolean isIncluyeCorte() {
        return incluyeCorte;
    }

    public void setIncluyeCorte(boolean incluyeCorte) {
        this.incluyeCorte = incluyeCorte;
    }

    public String getTamanoMascota() {
        return tamanoMascota;
    }

    public void setTamanoMascota(String tamanoMascota) {
        this.tamanoMascota = tamanoMascota;
    }

    @Override
    public double calcularPrecioTotal() {
        double total = getPrecioBase();

        if (incluyeBano) {
            total += 15000;
        }
        if (incluyeCorte) {
            total += 20000;
        }

        if (tamanoMascota.equalsIgnoreCase("Mediano")) {
            total += 10000;
        } else if (tamanoMascota.equalsIgnoreCase("Grande")) {
            total += 20000;
        }

        return total;
    }

    @Override
    public void mostrarDetalle() {
        super.mostrarDetalle();
        System.out.println("Incluye Baño: " + (incluyeBano ? "Sí (+$15,000)" : "No"));
        System.out.println("Incluye Corte: " + (incluyeCorte ? "Sí (+$20,000)" : "No"));
        System.out.println("Tamaño Mascota: " + tamanoMascota);
        System.out.println("Precio Total Estética: $" + calcularPrecioTotal());
    }
}