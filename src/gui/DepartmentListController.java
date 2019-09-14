package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable {

@FXML
	private TableView<Department> tableViewDepartment;     // referencias para nossa tela grafica

@FXML
    private TableColumn<Department, Integer> tableColumnId;

@FXML
private TableColumn<Department, String> tableColumnName;

@FXML
private Button btNew;

@FXML

public void onBtNewAction() {
	System.out.println("onBtNewAction");
}


    
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		}




	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id")); //este comando serve para preencher as colunas do programa grafico
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name")); // este comando serve para preencher as colunas do programa grafico
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty()); // este comandos servem para a grade acompanhar o menu principal 
	}

}
