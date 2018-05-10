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

public class MainController {
	
	private SST sistema;
	
	@FXML private Button nextButton;
	@FXML private TextField rutField;
	@FXML private TextField descField;
	@FXML Label label;

	@FXML
	private void nextButtonAction() throws Exception {
		String rut = rutField.getText().toString();
		
		if(!rutVerifier(rut)) {
			launchWarning("WarningRutInvalido.fxml");
			return;
		}
		
		if(sistema.getClient(Integer.parseInt(rut)) == null) {
			if(launchWarningClienteNoExiste()) {
				if(!launchCrearClienteWindow())
					return;
			}
			else return;
		}
		
		if(descField.getText().toString().length() == 0) {
			launchWarning("WarningCampoProblemaVacio.fxml");
			return;
		}
		
		System.out.println("WIIIII! AGREGAR PIEZAS!");
	}
	
	private boolean rutVerifier(String rut) {
		if(rut.length() > 8 || rut.length() < 7) return false;
		
		if(!StringUtils.isStrictlyNumeric(rut)) return false;
		
		return true;
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
	
	public void initVariables(SST sistema) {
		this.sistema = sistema;
	}
	
	private void launchWarning(String fxml) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		Parent root = (Parent) loader.load();
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
		Parent root = (Parent) loader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

		boolean flag = ((WarningClienteNoExisteController) loader.getController()).getFlag();
		return flag;
	}
	
	private boolean launchCrearClienteWindow() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearCliente.fxml"));
		Parent root = (Parent) loader.load();

		CrearClienteController crearClienteController = (CrearClienteController) loader.getController();
		crearClienteController.initVariables(rutField.getText().toString(), sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

		boolean flag = ((CrearClienteController) loader.getController()).getFlag();
		return flag;
	}
}
