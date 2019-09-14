package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;  //sempre colocar menuItem que tem um import no javafx para ficar padronizado 
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {   // nome padrao sempre iniciar com on o nome da nossa variavel e por fim o que ele vai ser neste caso Action de Ação 
		System.out.println("onMenuItemSellerAction");
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml");
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}

	
	@Override	
	public void initialize(URL uri, ResourceBundle rb) {
	}

	private synchronized void loadView(String absoluteName) { // o synchronized serve para a aplicação não parar caso ocorra algum erro vai garantir que todo o bloco seja rodado.
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); // criamos uma chamada para o main principal para inserir o about no menu principal 
		VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();  // pegamos a cena principal pegando o primeiro elemento da view principal scrollpane 
		
		Node mainMenu = mainVBox.getChildren().get(0); //guardando uma referencia para o mainmenu pegando o primeiro filho da mainmenu
		mainVBox.getChildren().clear(); // limpando todos os filhos da mainmenu
		mainVBox.getChildren().add(mainMenu); // agora adicionando no mainmenu os filhos do newVBox 
		mainVBox.getChildren().addAll(newVBox.getChildren());
		
		DepartmentListController controller = loader.getController();
		controller.setDeprtmentService(new DepartmentService());
		controller.updateTableView();
		}
		catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
			
		}
	}
	
	private synchronized void loadView2(String absoluteName) { // o synchronized serve para a aplicação não parar caso ocorra algum erro vai garantir que todo o bloco seja rodado.
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); // criamos uma chamada para o main principal para inserir o about no menu principal 
		VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();  // pegamos a cena principal pegando o primeiro elemento da view principal scrollpane 
		
		Node mainMenu = mainVBox.getChildren().get(0); //guardando uma referencia para o mainmenu pegando o primeiro filho da mainmenu
		mainVBox.getChildren().clear(); // limpando todos os filhos da mainmenu
		mainVBox.getChildren().add(mainMenu); // agora adicionando no mainmenu os filhos do newVBox 
		mainVBox.getChildren().addAll(newVBox.getChildren());
		
		
		
		
		}
		catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
			
		}
	}
	
}
