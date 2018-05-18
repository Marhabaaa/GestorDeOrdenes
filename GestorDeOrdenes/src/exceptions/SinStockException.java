package exceptions;

public class SinStockException extends Exception {

    public SinStockException() {
        super("No hay stock de la pieza seleccionada.");
    }
}
