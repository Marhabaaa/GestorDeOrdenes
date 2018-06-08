package exceptions;

public class MaxOrdenesSobrepasadoException extends Exception {

    public MaxOrdenesSobrepasadoException() {
        super("Cantidad maxima de ordenes permitidas alcanzada.");
    }
}
