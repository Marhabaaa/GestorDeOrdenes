package exceptions;

public class TieneOrdenesException extends Exception {
    public TieneOrdenesException(){
        super("Tiene ordenes pendientes.");
    }
}
