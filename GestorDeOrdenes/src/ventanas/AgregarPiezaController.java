package ventanas;

import application.ListaPiezas;
import application.Pieza;
import application.SST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgregarPiezaController {

    @FXML private TableView tableA;
    @FXML private TableColumn<Pieza, Integer> codA;
    @FXML private TableColumn<Pieza, String> descriptionA;
    @FXML private TableColumn<Pieza, Integer> cantA;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button nextButton;

    private SST sistema;
    private ListaPiezas partsList;

    @FXML
    private void addButtonAction() throws Exception {


    }

    @FXML
    private void deleteButtonAction() throws Exception{

    }

    @FXML
    private void cancelButtonAction() {
        //flag = false;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void nextButtonAction() {

    }

    public void initVariables(SST sistema) {
        this.sistema = sistema;
        partsList = new ListaPiezas();

        codA.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descriptionA.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantA.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));

        tableA.setItems(getItems());
        tableA.getColumns().addAll(codA, descriptionA, cantA);
    }

    private ObservableList<Pieza> getItems(){
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
