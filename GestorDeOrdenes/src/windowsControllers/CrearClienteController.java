package windowsControllers;

import exceptions.TelefonoInvalidoException;
import application.SST;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CrearClienteController {

    @FXML private TextField rutField;
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
            launchWarning("/windows/WarningCampoNombreVacio.fxml");
            return;
        }

        try {
            sistema.addClient(Integer.parseInt(rutField.getText()), nameField.getText(), phoneField.getText(), emailField.getText(), isBusinessButton.isSelected());
            flag = true;
            launchWarning("/windows/WarningClienteIngresadoConExito.fxml");
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        }
        catch(TelefonoInvalidoException e) {
            launchWarning("/windows/WarningTelefonoInvalido.fxml");
            return;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            launchWarning("/windows/WarningClienteNoIngresado.fxml");
            return;
        }
    }

    @FXML
    private void cancelButtonAction(){
        flag = false;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void initVariables(String rut, SST sistema) {
        rutField.setText(rut);
        this.sistema = sistema;
    }

    public boolean getFlag(){
        return flag;
    }

    private void launchWarning(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(createButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();
    }
}
