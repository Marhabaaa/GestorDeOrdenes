package ventanas;

import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReporteStockController {

    @FXML private TableView table;
    @FXML private TableColumn<Pieza, Integer> cod;
    @FXML private TableColumn<Pieza, String> descripcion;
    @FXML private TableColumn<Pieza, Integer> cantidad;
    @FXML private TableColumn<Pieza, Integer> complejidad;
    @FXML private TableColumn<Pieza, Integer> precio;

    @FXML private Button newPartButton;
    @FXML private Button editButton;
    @FXML private Button deteleButton;

    private SST sistema;

    public void initVariables(SST sistema) {

        this.sistema = sistema;

        cod.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descripcion.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantidad.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));
        complejidad.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("complex"));
        precio.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("price"));

        table.setItems(getItems());
    }

    private ObservableList<Pieza> getItems() {
        ListaPiezas list = sistema.getListaPiezas();
        ObservableList<Pieza> piezas = FXCollections.observableArrayList();
        int i = 0;
        while(i < list.size()) {
            piezas.add(list.get(list.size() - i - 1));
            i++;
        }

        return piezas;
    }



}
