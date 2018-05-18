package windowsControllers;

import application.SST;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NuevaPiezaController {

    @FXML private TextField codeField;
    @FXML private TextField descriptionField;
    @FXML private TextField complexField;
    @FXML private TextField cantField;
    @FXML private TextField priceField;
    @FXML private Button createButton;
    @FXML private Button cancelButton;

    private SST sistema;

    public void initVariables(SST sistema) {
        this.sistema = sistema;
    }


    //public void addPart(int code, String description, int cant, int price, int complex

    @FXML
    private void createButtonAction() throws SQLException {
        sistema.addPart(Integer.parseInt(codeField.getText()),descriptionField.getText(),Integer.parseInt(cantField.getText()),Integer.parseInt(priceField.getText()), Integer.parseInt(complexField.getText()));
        System.out.println("Se ha ingresado la pieza correctamente al sistema");

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }



}