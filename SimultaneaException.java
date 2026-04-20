public class SimultaneaException extends Exception {
    public SimultaneaException() {
        super("El doctor seleccionado ya tiene una cita agendada para esa fecha y hora.");
    }
    
}
