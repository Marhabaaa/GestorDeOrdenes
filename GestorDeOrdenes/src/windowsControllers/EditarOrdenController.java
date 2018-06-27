package windowsControllers;

import application.*;
import exceptions.SinStockException;
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

public class EditarOrdenController {

    @FXML private TableView tableA;
    @FXML private TableColumn<Pieza, Integer> codAColumn;
    @FXML private TableColumn<Pieza, String> descriptionAColumn;
    @FXML private TableColumn<Pieza, Integer> cantAColumn;

    @FXML private TableView tableB;
    @FXML private TableColumn<Pieza, Integer> codBColumn;
    @FXML private TableColumn<Pieza, String> descriptionBColumn;
    @FXML private TableColumn<Pieza, Integer> cantBColumn;

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    @FXML private TextField technicianField;
    @FXML private TextArea descriptionArea;

    private SST sistema;
    private Orden order;
    private ListaPiezas addedParts;
    private ListaPiezas removedParts;
    private int orderNumber;
    private boolean flag;

    public void initVariables(SST sistema, Orden order) {
        this.sistema = sistema;
        this.order = order;
        orderNumber = order.getOrderNumber();
        addedParts = new ListaPiezas();
        removedParts = new ListaPiezas();

        descriptionArea.setText(order.getDescription());

        codAColumn.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descriptionAColumn.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantAColumn.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));

        codBColumn.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descriptionBColumn.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantBColumn.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));

        tableA.setItems(getItems(sistema.getListaPiezas()));
        tableB.setItems(getItems(order.getPartsList()));
    }

    @FXML
    private void addButtonAction() {
        if(tableA.getSelectionModel().getSelectedItem() != null) {
            try {
                sistema.addPartToOrder(orderNumber, ((Pieza) tableA.getSelectionModel().getSelectedItem()).getCode());
                addedParts.addOne((Pieza) tableA.getSelectionModel().getSelectedItem());
            } catch (SinStockException e) {
                System.out.println(e.getMessage());
            }

            tableB.setItems(getItems(sistema.getOrder(orderNumber).getPartsList()));
            tableA.refresh();
            tableB.refresh();
        }
    }

    @FXML
    private void deleteButtonAction() {

        if(tableB.getSelectionModel().getSelectedItem() != null) {
            sistema.removePartFromOrder(orderNumber, ((Pieza) tableB.getSelectionModel().getSelectedItem()).getCode());
            removedParts.addOne((Pieza) tableB.getSelectionModel().getSelectedItem());

            tableB.setItems(getItems(sistema.getOrder(orderNumber).getPartsList()));
            tableA.refresh();
            tableB.refresh();
        }
    }

    @FXML
    private void saveButtonAction() throws Exception {
        order.setDescription(descriptionArea.getText());
        if(sistema.getOrder(orderNumber).getPartsList().isEmpty()) {
            order.setIsChecked(false);
            launchFinalizarOrdenNoRevisada();
        }
        else {
            sistema.getOrder(orderNumber).set();
            sistema.getOrder(orderNumber).setDateOut(sistema.calculateDateOut(orderNumber));
            launchFinalizarOrdenRevisada();
        }

        if(flag) {
            sistema.updateOrder(orderNumber);
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelButtonAction() {
        sistema.recoverParts(addedParts);
        sistema.returnParts(removedParts);
        order.recoverPartsList();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/FinalizarOrdenRevisada.fxml"));
        Parent root = loader.load();

        FinalizarOrdenController finalizarOrdenController = loader.getController();
        finalizarOrdenController.initVariables(sistema, sistema.getOrder(orderNumber));

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();

        flag = finalizarOrdenController.getFlag();
    }

    private void launchFinalizarOrdenNoRevisada() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/FinalizarOrdenNoRevisada.fxml"));
        Parent root = loader.load();

        FinalizarOrdenController finalizarOrdenController = loader.getController();
        finalizarOrdenController.initVariables(sistema, sistema.getOrder(orderNumber));

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();

        flag = finalizarOrdenController.getFlag();
    }
}

