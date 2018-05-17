package ventanas;

import application.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

public class MainController {

	//dafsfasdf

	@FXML
	private Button nextButton;
	@FXML
	private TextField rutField;
	@FXML
	private TextArea descArea;


	@FXML
	private Button editClientButton;
	@FXML
	private Button deleteClientButton;

	@FXML
	private Button newTechnicianButton;
	@FXML
	private Button editTechnicianButton;
	@FXML
	private Button deleteTecnhicianButton;

	@FXML
	private Button showOrdersButton;
	@FXML
	private Button generateReportOrdersButton;

	@FXML
	private Button showStockButton;
	@FXML
	private Button generateReportStockButton;

	@FXML
	private TableView clientsTable;
	@FXML
	private TableColumn<Persona, Integer> rutCliente;
	@FXML
	private TableColumn<Persona, String> nombreCliente;

	@FXML
	private TableView techsTable;
	@FXML
	private TableColumn<Tecnico, Integer> numeroTecnico;
	@FXML
	private TableColumn<Persona, String> nombreTecnico;

	@FXML
	Label label;

	private SST sistema;
	private Report reporte;
	private int orderNumber;


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
		} catch (RutInvalidoException e) {
			launchWarning("WarningRutInvalido.fxml");
			return;
		}

		if (descArea.getText().length() == 0) {
			launchWarning("WarningCampoProblemaVacio.fxml");
			return;
		}

		try {
			orderNumber = sistema.createOrder(descArea.getText(), Integer.parseInt(rutField.getText()));
			if (launchAgregarPieza()) ;
			if (sistema.getOrderListaPiezas(orderNumber).isEmpty())
				launchFinalizarOrdenNoRevisada();
			else
				launchFinalizarOrdenRevisada();
		} catch (SinTecnicosException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	private void ShowOrdersButtonAction() throws Exception {
		launchReporteOrdenes();
	}

	@FXML
	private void ShowStockButtonAction() throws Exception {
		launchReporteStock();
	}


	@FXML
	private void GenerateReportOrdersButtonAction() throws Exception {
		reporte.ganaciasTotales(sistema.getListaOrdenes());
	}


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

	private boolean launchWarningClienteNoExiste() throws Exception {
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

		return crearClienteController.getFlag();
	}

	private void launchEditarCliente() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarCliente.fxml"));
		Parent root = loader.load();

		EditarClienteController editarClienteController = loader.getController();
		editarClienteController.initVariables((Cliente)clientsTable.getSelectionModel().getSelectedItem(), sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(editClientButton.getScene().getWindow());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();
	}

	private boolean launchAgregarPieza() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarPiezas.fxml"));
		Parent root = loader.load();

		AgregarPiezasController agregarPiezasController = loader.getController();
		agregarPiezasController.initVariables(sistema, orderNumber);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

		return agregarPiezasController.getFlag();
	}

	private void launchReporteOrdenes() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ReporteOrdenes.fxml"));
		Parent root = loader.load();

		ReporteOrdenesController reporteOrdenesController = loader.getController();
		reporteOrdenesController.initVariables(sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(showOrdersButton.getScene().getWindow());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.show();
	}

	private void launchReporteStock() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ReporteStock.fxml"));
		Parent root = loader.load();

		ReporteStockController reporteStockController = loader.getController();
		reporteStockController.initVariables(sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(showOrdersButton.getScene().getWindow());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.show();
	}

	private void launchFinalizarOrdenRevisada() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalizarOrdenRevisada.fxml"));
		Parent root = loader.load();

		FinalizarOrdenController finalizarOrdenController = loader.getController();
		finalizarOrdenController.initVariables(sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

		System.out.print("Se ha creado la orden correctamente");
		//return finalizarOrdenController.getFlag();
	}

	private void launchFinalizarOrdenNoRevisada() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalizarOrdenNoRevisada.fxml"));
		Parent root = loader.load();

		FinalizarOrdenController finalizarOrdenController = loader.getController();
		finalizarOrdenController.initVariables(sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

		System.out.print("Se ha creado la orden correctamente");
		//return finalizarOrdenController.getFlag();
	}

	private void launchCrearTecnico() throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearTecnico.fxml"));
		Parent root = loader.load();

		AgregarTecnicoController agregarTecnicoController = loader.getController();
		agregarTecnicoController.initVariables(sistema);

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(nextButton.getScene().getWindow());

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.resizableProperty().setValue(false);
		stage.showAndWait();

	}


	@FXML
	private void clientsTabAction() {

		rutCliente.setCellValueFactory(new PropertyValueFactory<Persona, Integer>("rut"));
		nombreCliente.setCellValueFactory(new PropertyValueFactory<Persona, String>("name"));
		clientsTable.setItems(getClientsItems());
		clientsTable.refresh();

	}

	private ObservableList<Cliente> getClientsItems() {
		ListaClientes list = sistema.getListaClientes();
		ObservableList<Cliente> clientes = FXCollections.observableArrayList();
		int i = 0;
		while (i < list.size()) {
			clientes.add(list.get(list.size() - i - 1));
			i++;
		}
		return clientes;
	}

	@FXML
	private void editClientButtonAction() throws Exception {
		if(clientsTable.getSelectionModel().getSelectedItem() != null){
			launchEditarCliente();
		}
	}

	@FXML
	private void deleteClientButtonAction() throws Exception{
		if(clientsTable.getSelectionModel().getSelectedItem() != null && (((Cliente) clientsTable.getSelectionModel().getSelectedItem()).getOrders().isEmpty())){
			sistema.removeClient(((Cliente)clientsTable.getSelectionModel().getSelectedItem()).getRut());
		}
	}


	@FXML
	private void techsTabAction() {
		numeroTecnico.setCellValueFactory(new PropertyValueFactory<Tecnico, Integer>("techNumber"));
		nombreTecnico.setCellValueFactory(new PropertyValueFactory<Persona, String>("name"));
		techsTable.setItems(getTechsItems());
	}

	private ObservableList<Tecnico> getTechsItems() {
		ListaTecnicos list = sistema.getListaTecnicos();
		ObservableList<Tecnico> tecnicos = FXCollections.observableArrayList();
		int i = 0;
		while (i < list.size()) {
			tecnicos.add(list.get(list.size() - i - 1));
			i++;
		}
		return tecnicos;
	}

	@FXML
	private void newTechnicianButtonAction() throws Exception{
		launchCrearTecnico();
		techsTable.refresh();
	}

	@FXML
	private void deleteTechnicianButtonAction(){
		if(techsTable.getSelectionModel().getSelectedItem() != null){
			try {
				sistema.removeTechnician(((Tecnico) techsTable.getSelectionModel().getSelectedItem()).getTechNumber());
				techsTable.refresh();
				System.out.println("Se ha eliminado el tecnico con exito");
			} catch(TecnicoOcupadoException e){
				System.out.println(e.getMessage());
			}
		}
	}

}

