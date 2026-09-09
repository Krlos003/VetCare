package com.vetcare.SistemaMedico;

import java.util.Date;

public class AtencionMedica {

    private int idServicio;
    private String descripcion;
    private double costoAtencion;
    private Date fecha;

public AtencionMedica(int idServicio, String descripcion, double costoAtencion) {
    this.idServicio = idServicio;
    this.descripcion = descripcion;
    this.costoAtencion = costoAtencion;
    this.fecha = new Date();
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
public void setfecha(Date fecha) {
    this.fecha = fecha;

}

}