package ServicioDeEstetica;

public class ServicioEstetica extends Servicio {
    private boolean incluyeBaño;
    private boolean incluyeCorte;

    public ServicioEstetica(String nombreMascota, String tamanoMascota, boolean incluyeBaño, boolean incluyeCorte) {
        super("Peluquería y Estética", 20000, nombreMascota, tamanoMascota);
        this.incluyeBaño = incluyeBaño;
        this.incluyeCorte = incluyeCorte;
    }

    @Override
    public double calcularPrecioTotal() {
        double total = super.calcularPrecioTotal();
        if (incluyeBaño) total += 10000;
        if (incluyeCorte) total += 15000;
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + " (Baño: " + (incluyeBaño ? "Sí" : "No") + 
               ", Corte: " + (incluyeCorte ? "Sí" : "No") + ")";
    }
}