package com.vetcare.SistemaMedico;

import java.util.Date;

public class AtencionMedica {
    
    private int idServicio;
    private String descripcion;
    private double costoAtencion;
    private Date fecha;
    private String nombreMascota;
    private String diagnostico;


    public AtencionMedica(int idServicio, String descripcion, double costoAtencion, String nombreMascota, String diagnostico) {
        this.idServicio = idServicio;
        this.descripcion = descripcion;
        this.costoAtencion = costoAtencion;
        this.fecha = new Date();
        this.nombreMascota = nombreMascota;
        this.diagnostico = diagnostico;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoAtencion() {
        return costoAtencion;
    }

    public void setCostoAtencion(double costoAtencion) {
        this.costoAtencion = costoAtencion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "ID: " + idServicio + 
               " | Mascota: " + nombreMascota + 
               " | Servicio: " + descripcion + 
               " | Diagnostico: " + diagnostico + 
               " | Costo: $" + costoAtencion;
    }
}
