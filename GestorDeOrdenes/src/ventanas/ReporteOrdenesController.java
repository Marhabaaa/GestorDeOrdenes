package ventanas;

import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ReporteOrdenesController {

    @FXML private TableView table;
    @FXML private TableColumn<Orden, Integer> cod;
    @FXML private TableColumn<Orden, String> problema;
    @FXML private TableColumn<Orden, Integer> cliente;
    @FXML private TableColumn<Orden, Integer> tecnico;
    @FXML private TableColumn<Orden, Integer> ganancia;
    @FXML private TableColumn<Orden, String> entregada;
    @FXML private Button editButton;
    @FXML private Button deteleButton;

    private SST sistema;

    public void initVariables(SST sistema) {

        this.sistema = sistema;

        cod.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("orderNumber"));
        problema.setCellValueFactory(new PropertyValueFactory<Orden, String>("description"));
        cliente.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("clientRut"));
        tecnico.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("techNumber"));
        ganancia.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("price"));
        entregada.setCellValueFactory(new PropertyValueFactory<Orden, String>("entregada"));

        table.setItems(getItems());
    }


   private ObservableList<Orden> getItems() {
        ListaOrdenes list = sistema.getListaOrdenes();
        ObservableList<Orden> ordenes = FXCollections.observableArrayList();
        int i = 0;
        String checked;
        while(i < list.size()) {
            ordenes.add(list.get(list.size() - i - 1));
            i++;
        }

        return ordenes;
    }

}
