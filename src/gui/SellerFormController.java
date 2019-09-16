package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private Seller entity; // criado uma instancia do department em baixo existe o set

	private SellerService service; // criado uma instancia do departmentSErvice para fazer o insert e update

	private DepartmentService departmentService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private DatePicker dpBirthDate;

	@FXML
	private TextField txtBaseSalary;

	@FXML
	private ComboBox<Department> comboBoxDepartment;

	@FXML
	private Label labelErroName;

	@FXML
	private Label labelErroEmail;

	@FXML
	private Label labelErroBirthDate;

	@FXML
	private Label labelErroBaseSalary;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Department> obsList;

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		} // excessoes criadas para avisar o programador
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try { // abaixo temos comandos com relação a banco de dados e pode haver algum erro
				// por isso estamos colocando o Try
			entity = getFormData(); // responsavel por pegar os dados da tela do programa grafico e instanciar no
									// department
			service.saveOrUpdate(entity);
			notifyDataChangeListeners(); // responsavel por atualizar a lista
			Utils.currentStage(event).close(); // este comando serve para fechar a tela no fim do processo de salvar o
												// department
		} catch (ValidationException e) {
			setErrorsMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() { // emite o evento para atualizar a tela
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChenged();
		}

	}

	private Seller getFormData() {
		Seller obj = new Seller();

		ValidationException exception = new ValidationException("validation exception");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) { // verificando com o trim se tem
																				// espações em branco no começo e fim do
																				// nome e equals se o campo esta em
																				// branco
			exception.addError("name", "Field can't be empty"); // se o if der condição adicionamos o erro na classe
																// validationException no Map
		}
		obj.setName(txtName.getText());

		if (exception.getErrors().size() > 0) { // neste if estamos verificando se ouve alguma mensagem de erro no if
												// acima se tiver teremos que trata-lá
			throw exception;

		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	public void setSeller(Seller entity) { // criada uma instancia do department
		this.entity = entity;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void setServices(SellerService service, DepartmentService departmentservice) { // criada a instancia para o
																							// departmentService e o
																							// SellerService
		this.service = service;
		this.departmentService = departmentservice;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); // o campo id so vai aceitar numeros inteiros
		Constraints.setTextFieldMaxLength(txtName, 70);// o campo name aceita no maximo 30 caracteres
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
	}

	public void updateFormData() { // neste bloco iremos preencher a tela do programa grafico SellerForm para
									// incluir no banco de dados.
		if (entity == null) { // vamos criar uma condição para verificar se o department esta nulo
			throw new IllegalStateException("Entity was null"); // caso esteja nulo vai aparecer a mensagem de erro
		}
		txtId.setText(String.valueOf(entity.getId())); // a caixa de texto id eh do tipo String por isso temos que fazer
														// desta maneira
		txtName.setText(entity.getName()); // neste caso não foi preciso fazer a conversão para string pois o nome já é
											// String
		txtEmail.setText(entity.getEmail());

		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if (entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
	    if (entity.getDepartment() == null) {
	    	comboBoxDepartment.getSelectionModel().selectFirst();
	    }
	    else {
		comboBoxDepartment.setValue(entity.getDepartment());
	}
	
	}
	public void loadAssociatedObjects() {
		if (departmentService == null) {
			throw new IllegalStateException("DepartmentService was null");
		}

		List<Department> list = departmentService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxDepartment.setItems(obsList);
	}

	private void setErrorsMessages(Map<String, String> errors) { // este comando serve para apresentar os erros nos
																	// campos do formularios dentro de um label oculto
																	// que fizemos na tela grafica
		Set<String> fields = errors.keySet();

		if (fields.contains("name")) {
			labelErroName.setText(errors.get("name"));
		}
	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}

}
