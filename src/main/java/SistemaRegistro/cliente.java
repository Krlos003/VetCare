package SistemaRegistro;

public class cliente {
    private String nombre;
    private String telefono;
    private String Documento;

    public cliente(String nombre, String telefono, String Documento) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.Documento = Documento;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    public String gettelefono() {
        return telefono;
    }

    public void settelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("el telefono no puede estar vacio");
        }
        this.telefono = telefono;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String Documento) {
        if (Documento == null || Documento.trim().isEmpty()) {
            throw new IllegalArgumentException("el documento no puede estar vacio");
        }
        this.Documento = Documento;
    }

    @Override
    public String toString() {
        return "cliente vetcare = nombre:" + nombre + " telefono:" + telefono + " documento:" + Documento;
    }
}