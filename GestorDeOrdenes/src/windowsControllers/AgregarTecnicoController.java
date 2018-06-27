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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AgregarTecnicoController {

    @FXML private TextField rutField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField hoursField;
    @FXML private Button createButton;
    @FXML private Button cancelButton;

    private SST sistema;
    private boolean flag;

    public void initVariables(SST sistema) {
        this.sistema = sistema;
    }

    @FXML
    private void createButtonAction() throws Exception{
        try {
            sistema.addTechnician(rutField.getText(), nameField.getText(), phoneField.getText(), emailField.getText(), Integer.parseInt(hoursField.getText()));
            flag = true;
            System.out.println("Tecnico agregado correctamente");
            Stage stage = (Stage) cancelButton.getScene().getWindow();
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
        flag = false;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
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
