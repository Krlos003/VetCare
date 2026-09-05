package SistemaDeVentas;

public abstract class Servicio {
    private String nombreServicio;
    private double precioBase;

    public Servicio(String nombreServicio, double precioBase) {
        this.nombreServicio = nombreServicio;
        this.precioBase = precioBase;
    }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }

    public abstract double calcularPrecioTotal();

    public void mostrarDetalle() {
        System.out.println("Servicio: " + nombreServicio);
        System.out.println("Precio Base: $" + precioBase);
    }
}