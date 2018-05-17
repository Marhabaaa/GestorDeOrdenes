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

public class AgregarPiezasController {

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
    private boolean flag;


    @FXML
    private void addButtonAction() throws SinStockException {
        try {
            sistema.addPartToOrder(orderNumber, ((Pieza) tableA.getSelectionModel().getSelectedItem()).getCode());
        }
        catch(SinStockException e) {
            System.out.println(e.getMessage());
        }

        tableA.refresh();
        tableB.setItems(getItems(sistema.getOrderListaPiezas(orderNumber)));
        tableB.refresh();
    }

    @FXML
    private void deleteButtonAction() {

        if(tableB.getSelectionModel().getSelectedItem() != null)
            sistema.removePartFromOrder(orderNumber, ((Pieza) tableB.getSelectionModel().getSelectedItem()).getCode());

        tableB.setItems(getItems(sistema.getOrderListaPiezas(orderNumber)));
        tableA.refresh();
        tableB.refresh();
    }

    @FXML
    private void cancelButtonAction() {
        flag = false;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void nextButtonAction() throws Exception{
        if(sistema.getOrderListaPiezas(orderNumber).isEmpty())
            launchFinalizarOrdenNoRevisada();
        else
            launchFinalizarOrdenRevisada();

        flag = true;
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();

    }

    public void initVariables(SST sistema, int orderNumber) {
        this.sistema = sistema;
        this.orderNumber = orderNumber;

        codA.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descriptionA.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantA.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));

        codB.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descriptionB.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantB.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));

        tableA.setItems(getItems(sistema.getListaPiezas()));
    }

    public boolean getFlag() {
        return flag;
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

    private void launchFinalizarOrdenRevisada() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalizarOrdenRevisada.fxml"));
        Parent root = loader.load();

        //FinalizarOrdenController finalizarOrdenController = loader.getController();
        //finalizarOrdenController.initVariables(sistema, orderNumber);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(nextButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();

        //return finalizarOrdenController.getFlag();
    }

    private void launchFinalizarOrdenNoRevisada() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalizarOrdenNoRevisada.fxml"));
        Parent root = loader.load();

        //FinalizarOrdenController finalizarOrdenController = loader.getController();
        //finalizarOrdenController.initVariables(sistema, orderNumber);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(nextButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();

        //return finalizarOrdenController.getFlag();
    }
}