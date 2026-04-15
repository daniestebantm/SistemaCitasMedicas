public class Doctor {
    private String nombre, especialidad;
    private boolean disponibilidad;

    public Doctor(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponibilidad = true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    
}
