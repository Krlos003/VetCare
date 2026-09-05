package com.vetcare.SistemaMedico;

import java.util.Date;

public class MenuMedico {
   private int idServicio;
   private String descripcion;
   private double costoAtencion;
   private Date fecha;
   private String nombreMascota;
   private String diagnostico;

   public MenuMedico(int idServicio, String descripcion, double costoAtencion, String nombreMascota, String diagnostico) {
      this.idServicio = idServicio;
      this.descripcion = descripcion;
      this.costoAtencion = costoAtencion;
      this.fecha = new Date();
      this.nombreMascota = nombreMascota;
      this.diagnostico = diagnostico;
   }

   public int getIdServicio() {
      return this.idServicio;
   }

   public void setIdServicio(int idServicio) {
      this.idServicio = idServicio;
   }

   public String getDescripcion() {
      return this.descripcion;
   }

   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   public double getCostoAtencion() {
      return this.costoAtencion;
   }

   public void setCostoAtencion(double costoAtencion) {
      this.costoAtencion = costoAtencion;
   }

   public Date getFecha() {
      return this.fecha;
   }

   public String getNombreMascota() {
      return this.nombreMascota;
   }

   public void setNombreMascota(String nombreMascota) {
      this.nombreMascota = nombreMascota;
   }

   public String getDiagnostico() {
      return this.diagnostico;
   }

   public void setDiagnostico(String diagnostico) {
      this.diagnostico = diagnostico;
   }

   public String toString() {
      return "ID: " + this.idServicio + " | Mascota: " + this.nombreMascota + " | Servicio: " + this.descripcion + " | Diagnostico: " + this.diagnostico + " | Costo: $" + this.costoAtencion;
   }
}

