package windowsControllers;

import application.Cliente;
import exceptions.RutInvalidoException;
import application.SST;
import exceptions.TelefonoInvalidoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditarClienteController {

    @FXML private TextField rutField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private RadioButton isBusinessButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private SST sistema;

    public void initVariables(Cliente cliente, SST sistema) throws RutInvalidoException {
        this.sistema = sistema;
        rutField.setText(String.valueOf(cliente.getRut()));
        nameField.setText(cliente.getName());
        phoneField.setText(String.valueOf(cliente.getPhoneNumber()));
        emailField.setText(cliente.geteMail());
        isBusinessButton.setSelected(cliente.isBusiness());
    }

    @FXML
    private void saveButtonAction() throws Exception {
        if(nameField.getText().length() == 0) {
            launchWarning("/windows/WarningCampoNombreVacio.fxml");
            return;
        }

        try {
            sistema.updateClient(rutField.getText(), nameField.getText(), phoneField.getText(), emailField.getText(), isBusinessButton.isSelected());
            System.out.println("Cliente actualizado correctamente");
            launchWarning("/windows/WarningClienteIngresadoConExito.fxml");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        catch(TelefonoInvalidoException e) {
            launchWarning("/windows/WarningTelefonoInvalido.fxml");
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            launchWarning("/windows/WarningClienteNoIngresado.fxml");
        }
        catch (RutInvalidoException e) {
            System.out.println(e.getMessage());
            launchWarning("/windows/WarningRutInvalido.fxml");
        }
    }

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void launchWarning(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(saveButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();
    }

}

