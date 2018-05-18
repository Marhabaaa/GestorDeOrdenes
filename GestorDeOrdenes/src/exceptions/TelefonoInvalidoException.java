package exceptions;

public class TelefonoInvalidoException extends Exception{

    public TelefonoInvalidoException() {
        super("El formato de telefono ingresado no es valido.");
    }
}
