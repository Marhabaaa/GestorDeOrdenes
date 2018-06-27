package exceptions;

public class SinTecnicosException extends Exception {

    public SinTecnicosException() {
        super("No hay tecnicos en el sistema.");
    }
}

