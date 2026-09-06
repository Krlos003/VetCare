package SistemaDeVentas;

import java.time.LocalDate;

public class Medicamento extends Producto {
    
    private String laboratorio;
    private LocalDate fechaVencimiento;
    private boolean requiereReceta; 

    public Medicamento(String nombre, double precio, int stock, String ubicacion, double contenido, UnidadMedida unidadMedida, String laboratorio, LocalDate fechaVencimiento, boolean requiereReceta) {
        super(nombre, precio, stock, "Medicamento", ubicacion, contenido, unidadMedida);
        this.laboratorio = laboratorio;
        this.fechaVencimiento = fechaVencimiento;
        this.requiereReceta = requiereReceta;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean getRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }


    @Override
    public String toString() {
        return super.toString() +
                ", laboratorio='" + laboratorio + '\'' +
                ", fechaVencimiento=" + fechaVencimiento +
                ", requiereReceta=" + requiereReceta +
                '}';
    }
    
}
