package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity; // criado uma instancia do department em baixo existe o set

	private DepartmentService service; // criado uma instancia do departmentSErvice para fazer o insert e update

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private Label labelErroName;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

@FXML
public void onBtSaveAction(ActionEvent event) {
	if (entity == null) {
		throw new IllegalStateException("Entity was null");
		}                                                       // excessoes criadas para avisar o programador 
	if (service == null) {
		throw new IllegalStateException("Service was null");
	}
	try {    // abaixo temos comandos com relação a banco de dados e pode haver algum erro por isso estamos colocando o Try
	    entity = getFormData(); // responsavel por pegar os dados da tela do programa grafico e instanciar no department
	    service.saveOrUpdate(entity);
	    notifyDataChangeListeners(); // responsavel por atualizar a lista 
	    Utils.currentStage(event).close();   // este comando serve para fechar a tela no fim do processo de salvar o department
	}
	    	catch (DbException e) {
	    		Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
	    	}
	    }


	private void notifyDataChangeListeners() {  // emite o evento para atualizar a tela 
	for (DataChangeListener listener : dataChangeListeners) {
		listener.onDataChenged();
	}
	
}


	private Department getFormData() {
		Department obj = new Department();

		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		 Utils.currentStage(event).close();
	}

	public void setDepartment(Department entity) { // criada uma instancia do department
		this.entity = entity;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void setDepartmentService(DepartmentService service) { // criada a instancia para o departmentService
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); // o campo id so vai aceitar numeros inteiros
		Constraints.setTextFieldMaxLength(txtName, 30);// o campo name aceita no maximo 30 caracteres

	}

	public void updateFormData() { // neste bloco iremos preencher a tela do programa grafico DepartmentForm para
									// incluir no banco de dados.
		if (entity == null) { // vamos criar uma condição para verificar se o department esta nulo
			throw new IllegalStateException("Entity was null"); // caso esteja nulo vai aparecer a mensagem de erro
		}
		txtId.setText(String.valueOf(entity.getId())); // a caixa de texto id eh do tipo String por isso temos que fazer
														// desta maneira
		txtName.setText(entity.getName()); // neste caso não foi preciso fazer a conversão para string pois o nome já é
											// String

	}
}
