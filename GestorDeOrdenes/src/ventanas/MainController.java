package ventanas;

import application.RutInvalidoException;
import application.SST;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	
	@FXML private Button nextButton;
	@FXML private TextField rutField;
	@FXML private TextField descField;
	@FXML Label label;

	private SST sistema;

	@FXML
	private void nextButtonAction() throws Exception {
		String rut = rutField.getText();

		try {
			if (sistema.getClient(rut) == null) {
				if (launchWarningClienteNoExiste()) {
					if (!launchCrearClienteWindow())
						return;
				} else return;
			}
		}
		catch(RutInvalidoException e){
			launchWarning("WarningRutInvalido.fxml");
			return;
		}

		if (descField.getText().length() == 0) {
			launchWarning("WarningCampoProblemaVacio.fxml");
			return;
		}

		launchAgregarPieza();
	}
	
	/*
	private void showLabel(int rut) throws Exception {
		Cliente aux = sistema.getClient(rut);
		if(aux != null) {
			String name = aux.getName();
			putLabel(name);
		}
		else {
			if(launchWarningClienteNoExiste())
				launchCrearClienteWindow();
		}
		
	}
	
	private void putLabel(String s) {
		label.setText(s);
	}
	*/
	
	void initVariables(SST sistema) {
		this.sistema = sistema;
	}
	
	private void launchWarning(String fxml) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.show();
	}
	
	private boolean launchWarningClienteNoExiste() throws Exception  {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WarningClienteNoExiste.fxml"));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

        return ((WarningClienteNoExisteController) loader.getController()).getFlag();
	}
	
	private boolean launchCrearClienteWindow() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearCliente.fxml"));
		Parent root = loader.load();

		CrearClienteController crearClienteController = loader.getController();
		crearClienteController.initVariables(rutField.getText(), sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

        return ((CrearClienteController) loader.getController()).getFlag();
	}

	private void launchAgregarPieza() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarPiezas.fxml"));
        Parent root = loader.load();

        AgregarPiezaController agregarPiezaController = loader.getController();
        agregarPiezaController.initVariables(sistema);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(nextButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.show();
    }
}
