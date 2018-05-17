package application;

public class TecnicoOcupadoException extends Exception {
    public TecnicoOcupadoException(){
        super("Tecnico tiene trabajo pendiente.");
    }

}
