public class HoraInvalidaException extends Exception {
    
    public HoraInvalidaException() {
        super("La hora de la cita debe estar entre las 08:00 y las 21:00.");
    }
}
