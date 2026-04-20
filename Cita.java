import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cita {
    private Paciente pacienteCita;
    private LocalTime hora;
    private LocalDate fecha;
    private Doctor doctorCita;

    public Cita(Paciente pacienteCita, LocalTime hora, LocalDate fecha, Doctor doctorCita) {
        this.pacienteCita = pacienteCita;
        this.hora = hora;
        this.fecha = fecha;
        this.doctorCita = doctorCita;
    }

    //Getters
    public LocalTime getHora() {
        return hora;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Paciente getPacienteCita() {
        return pacienteCita;
    }

    public Doctor getDoctorCita() {
        return doctorCita;
    }

    //Setters de hora y fecha
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    
}
