package ServicioDeEstetica;

import java.io.Serializable;

public class CitaEstetica implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fechaHora;
    private ServicioEstetica servicio;

    public CitaEstetica(String fechaHora, ServicioEstetica servicio) {
        this.fechaHora = fechaHora;
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return "Fecha/Hora: " + fechaHora + " | " + servicio.toString();
    }
}