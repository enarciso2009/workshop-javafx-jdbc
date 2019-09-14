package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {
	
	private Department entity; // criado uma instancia do department em baixo existe o set
	
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
public void onBtSaveAction() {
	System.out.println("onBtSaveAction");
}

@FXML
public void onBtCancelAction() {
	System.out.println("onBtCancelAction");
}

public void setDepartment(Department entity) {  // criada uma instancia do department
	this.entity = entity;
}

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
			initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); // o campo id so vai aceitar numeros inteiros 
		Constraints.setTextFieldMaxLength(txtName, 30);// o campo name aceita no maximo 30 caracteres   
		              
	}
	
	public void updateFormData( ) { // neste bloco iremos preencher a tela do programa grafico DepartmentForm para incluir no banco de dados.
	if (entity == null) {  //vamos criar uma condição para verificar se o department esta nulo
		throw new IllegalStateException("Entity was null"); //caso esteja nulo vai aparecer a mensagem de erro 
	}
	txtId.setText(String.valueOf(entity.getId()));	// a caixa de texto id eh do tipo String por isso temos que fazer desta maneira
	txtName.setText(entity.getName());  // neste caso não foi preciso fazer a conversão para string pois o nome já é String
		
	}
}
