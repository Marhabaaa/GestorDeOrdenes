package ventanas;

import com.mysql.jdbc.StringUtils;
import application.SST;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CrearClienteController {

    @FXML private Label rutLabel;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private RadioButton isBusinessButton;
    @FXML private Button createButton;
    @FXML private Button cancelButton;

    private SST sistema;
    private boolean flag;

    @FXML
    private void createButtonAction() throws Exception {
        if(nameField.getText().length() == 0) {
            launchWarning("WarningCampoNombreVacio.fxml");
            return;
        }
        if(!phoneVerifier()) {
            launchWarning("WarningTelefonoInvalido.fxml");
            return;
        }

        flag = sistema.addClient(Integer.parseInt(rutLabel.getText()), nameField.getText(), Integer.parseInt(phoneField.getText()), emailField.getText(), isBusinessButton.isSelected());

        if(!flag){
            launchWarning("WarningClienteNoIngresado.fxml");
            return;
        }
        else {
            launchWarning("WarningClienteIngresadoConExito.fxml");
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelButtonAction(){
        flag = false;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean getFlag(){
        return flag;
    }

    public void initVariables(String rut, SST sistema){
        rutLabel.setText(rut);
        this.sistema = sistema;
    }


    private boolean phoneVerifier() { //hacerlo una excepcion y pasarlo a persona
        String phone = phoneField.getText().toString();

        if(phone.length() != 9) return false;

        if(!StringUtils.isStrictlyNumeric(phone)) return false;

        return true;
    }

    private void launchWarning(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(createButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();
    }
}
