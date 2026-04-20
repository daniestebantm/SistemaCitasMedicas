public class EdadInvalidaException extends Exception{
    public EdadInvalidaException(){
        super("La edad del paciente debe ser un número entero del 0 al 109.");
    }
}
