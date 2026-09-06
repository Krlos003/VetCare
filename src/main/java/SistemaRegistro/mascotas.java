package SistemaRegistro;


public class mascotas {
    private String nombre;
    private String especie;
    private int edad;

    public mascotas(String nombre, String especie, int edad) {
        setNombre(nombre);
        setEspecie(especie);
        setEdad(edad);
    }

public String getNombre(){
return nombre;
}

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("el nombre de la mascota no puede estar vacio");
        this.nombre = nombre;
    }

public String getEspecie(){
    return especie;
}
    public void setEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty())
            throw new IllegalArgumentException("la especie no puede estar vacia");
        this.especie = especie;
    }

    public int getEdad(){

        return edad;
    }
    public void setEdad(int edad){
        if (edad < 0)
            throw new IllegalArgumentException( "la edad no puede ser negativa");
        this.edad = edad;
        
    
    }
    public String toString() {
        return "mascota vetcare = nombre: " + nombre + " especie: " + especie;
    }

}
