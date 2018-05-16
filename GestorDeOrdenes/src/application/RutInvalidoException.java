package application;

public class RutInvalidoException extends Exception {

    public RutInvalidoException() {
        super("El formato de rut ingresado es invalido.");
    }
}
