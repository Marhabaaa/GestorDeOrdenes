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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgregarPiezaController {

    @FXML private ScrollPane scrollPane;
    @FXML private TableView stockTable;
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

        //stockTable.setItems(getItems());
    }

    private ObservableList<Pieza> getItems(){
        ListaPiezas list = sistema.getListaPiezas();
        ObservableList<Pieza> piezas = FXCollections.observableArrayList();
        int i = 0;
        while(i < list.size()) {
            piezas.add(list.get(i));
            i++;
        }

        return piezas;
    }
}
