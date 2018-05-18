package exceptions;

public class ClienteTieneOrdenesException extends Exception {
    public ClienteTieneOrdenesException() {super("Cliente aun tiene ordenes pendientes.");}
}
