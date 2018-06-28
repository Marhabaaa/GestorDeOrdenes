package windowsControllers;

import application.*;

import javafx.fxml.FXML;

import application.ListaPiezas;
import application.Pieza;
import application.SST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import exceptions.RutInvalidoException;
import application.SST;
import exceptions.TelefonoInvalidoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class VerOrdenController {

    @FXML private Label clientLabel;
    @FXML private Label totalLabel;
    @FXML private Label dateLabel;
    @FXML private Label techLabel;
    @FXML private Label problemLabel;
    @FXML private Button backButton;

    @FXML private TableView tableA;
    @FXML private TableColumn<Pieza, Integer> codAColumn;
    @FXML private TableColumn<Pieza, String> descriptionAColumn;
    @FXML private TableColumn<Pieza, Integer> cantAColumn;


    @FXML
    private void backButtonAction(){
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void initVariables(SST sistema, Orden orden) {

        clientLabel.setText(String.valueOf(sistema.getClient(orden.getClientRut()).getName()));
        totalLabel.setText(String.valueOf(orden.calculatePrice()));
        dateLabel.setText(String.valueOf(orden.getDateOut()));
        techLabel.setText(String.valueOf(sistema.getTechnician(orden.getTechNumber()).getName()));
        problemLabel.setText(String.valueOf(orden.getDescription()));


        codAColumn.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descriptionAColumn.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantAColumn.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));



        tableA.setItems(getItems(orden.getPartsList()));

    }

    private ObservableList<Pieza> getItems(ListaPiezas list) {
        ObservableList<Pieza> piezas = FXCollections.observableArrayList();
        int i = 0;
        while(i < list.size()) {
            piezas.add(list.get(list.size() - i - 1));
            i++;
        }

        return piezas;
    }

}