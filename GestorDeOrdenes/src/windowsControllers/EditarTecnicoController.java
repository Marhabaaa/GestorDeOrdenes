package windowsControllers;

import application.SST;
import application.Tecnico;
import exceptions.RutInvalidoException;
import exceptions.TelefonoInvalidoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditarTecnicoController {

    @FXML private TextField numberField;
    @FXML private TextField rutField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField hoursField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private SST sistema;

    public void initVariables(Tecnico technician, SST sistema) {
        this.sistema = sistema;
        numberField.setText(String.valueOf(technician.getTechNumber()));
        rutField.setText((String.valueOf(technician.getRut())));
        nameField.setText(technician.getName());
        phoneField.setText(String.valueOf(technician.getPhoneNumber()));
        emailField.setText(technician.geteMail());
        hoursField.setText(String.valueOf(technician.getDwh()));
    }

    @FXML
    private void saveButtonAction() throws Exception {
        try {
            sistema.updateTechnician(Integer.parseInt(numberField.getText()), rutField.getText(), nameField.getText(), phoneField.getText(), emailField.getText(), Integer.parseInt(hoursField.getText()));
            System.out.println("Tecnico actualizado correctamente");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch(TelefonoInvalidoException e) {
            launchWarning("/windows/WarningTelefonoInvalido.fxml");
            System.out.println(e.getMessage());
        }
        catch (RutInvalidoException e) {
            launchWarning("/windows/WarningRutInvalido.fxml");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void cancelButtonAction(){
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