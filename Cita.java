public class Cita {
    private Paciente pacienteCita;
    private String hora, fecha;
    private Doctor doctorCita;

    public Cita(Paciente pacienteCita, String hora, String fecha, Doctor doctorCita) {
        this.pacienteCita = pacienteCita;
        this.hora = hora;
        this.fecha = fecha;
        this.doctorCita = doctorCita;
    }

    //Getters de hora y fecha
    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    //Setters de hora y fecha
    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
}
