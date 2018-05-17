package ventanas;

import application.Orden;
import application.Pieza;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class ReporteOrdenesController {

    @FXML private TableView table;
    @FXML private TableColumn<Orden, Integer> cod;
    @FXML private TableColumn<Pieza, String> cliente;
    @FXML private TableColumn<Pieza, Integer> tecnico;
    @FXML private TableColumn<Pieza, Integer> ganancia;

}
