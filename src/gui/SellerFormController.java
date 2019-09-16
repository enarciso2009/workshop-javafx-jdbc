package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private Seller entity; // criado uma instancia do department em baixo existe o set

	private SellerService service; // criado uma instancia do departmentSErvice para fazer o insert e update

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
	catch (ValidationException e) {
		setErrorsMessages(e.getErrors());
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


	private Seller getFormData() {
		Seller obj = new Seller();

		ValidationException exception = new  ValidationException("validation exception");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {  //verificando com o trim se tem espações em branco no começo e fim do nome e equals se o campo esta em branco
			exception.addError("name", "Field can't be empty"); // se o if der condição adicionamos o erro na classe validationException no Map 
		}
		obj.setName(txtName.getText());

		if (exception.getErrors().size() > 0) {  // neste if estamos verificando se ouve alguma mensagem de erro no if acima se tiver teremos que trata-lá
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

	public void setSellerService(SellerService service) { // criada a instancia para o departmentService
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

	public void updateFormData() { // neste bloco iremos preencher a tela do programa grafico SellerForm para
									// incluir no banco de dados.
		if (entity == null) { // vamos criar uma condição para verificar se o department esta nulo
			throw new IllegalStateException("Entity was null"); // caso esteja nulo vai aparecer a mensagem de erro
		}
		txtId.setText(String.valueOf(entity.getId())); // a caixa de texto id eh do tipo String por isso temos que fazer
														// desta maneira
		txtName.setText(entity.getName()); // neste caso não foi preciso fazer a conversão para string pois o nome já é
											// String

	}
	
	private void setErrorsMessages(Map<String, String> errors) {	// este comando serve para apresentar os erros nos campos do formularios dentro de um label oculto que fizemos na tela grafica 
	 Set<String> fields = errors.keySet();
	 
	 if (fields.contains("name")) {
		 labelErroName.setText(errors.get("name"));
	 }
}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	