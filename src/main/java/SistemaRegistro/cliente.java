package SistemaRegistro;

public class cliente {
    private String nombre;
    private String telefono;

    public cliente(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
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

    @Override
    public String toString() {
        return "cliente vetcare = nombre:" + nombre + " telefono:" + telefono;
    }
}