package ventanas;

import application.Orden;
import application.Pieza;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReporteStockController {

    @FXML private TableView table;
    @FXML private TableColumn<Orden, Integer> cod;
    @FXML private TableColumn<Pieza, String> descripcion;
    @FXML private TableColumn<Pieza, Integer> cantidad;
    @FXML private TableColumn<Pieza, Integer> precio;


}
