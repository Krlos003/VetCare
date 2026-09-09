package com.vetcare.SistemaMedico;

import java.util.Date;

public class MenuMedico extends AtencionMedica {
   private String nombreMascota;
   private String diagnostico;

public MenuMedico(int idServicio, String descripcion, double costoAtencion, String nombreMascota, String diagnostico) {   
   super(idServicio,descripcion, costoAtencion);
   this.nombreMascota = nombreMascota;
   this.diagnostico = diagnostico;
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
   return super.toString() +
      " | Mascota: " + this.nombreMascota +
      " | Diagnostico: " + this.diagnostico;
   

       }
   
   }