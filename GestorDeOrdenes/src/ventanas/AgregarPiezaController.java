package ventanas;

import application.ListaPiezas;
import application.Pieza;
import application.SST;
import application.SinStockException;
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

    @FXML private TableView tableB;
    @FXML private TableColumn<Pieza, Integer> codB;
    @FXML private TableColumn<Pieza, String> descriptionB;
    @FXML private TableColumn<Pieza, Integer> cantB;

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button nextButton;

    private SST sistema;
    private int orderNumber;


    @FXML
    private void addButtonAction() throws Exception {
        sistema.transferPart(orderNumber, ((Pieza) tableA.getSelectionModel().getSelectedItem()).getCode());
        //tableA.refresh();
    }

    @FXML
    private void deleteButtonAction() throws Exception{

    }

    @FXML
    private void cancelButtonAction() {
        //flag = false;
        //Stage stage = (Stage) cancelButton.getScene().getWindow();
        //stage.close();
        try {
            sistema.transferPart(orderNumber, ((Pieza) tableA.getSelectionModel().getSelectedItem()).getCode());
        }
        catch(SinStockException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void nextButtonAction() {

    }

    public void initVariables(SST sistema, int orderNumber) {
        this.sistema = sistema;
        this.orderNumber = orderNumber;

        codA.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descriptionA.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantA.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));

        tableA.setItems(getItems());
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