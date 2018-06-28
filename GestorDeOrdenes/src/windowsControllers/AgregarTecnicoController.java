package windowsControllers;

import application.SST;
import application.Tecnico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarTecnicoController {

    @FXML private TextField rutField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField hoursField;
    @FXML private Button createButton;
    @FXML private Button cancelButton;

    private SST sistema;

    public void initVariables(SST sistema) {
        this.sistema = sistema;
    }

    @FXML
    private void createButtonAction() {
        if(sistema.addTechnician(Integer.parseInt(rutField.getText()), nameField.getText(),Integer.parseInt(phoneField.getText()), emailField.getText(), (sistema.getListaTecnicos().size())+1,Integer.parseInt(hoursField.getText()))){
            System.out.println("Se ha ingresado el nuevo tecnico correctamente");
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void cancelButtonAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
