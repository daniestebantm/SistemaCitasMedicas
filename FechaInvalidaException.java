import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FechaInvalidaException extends Exception{
    public FechaInvalidaException(LocalDate fechaActual, LocalDate fechaLimite, DateTimeFormatter formatoFecha) {
        super("La fecha de la cita debe estar entre " + fechaActual.plusDays(1).format(formatoFecha) + " y " + fechaLimite.format(formatoFecha) + ".");
    }
    
}
