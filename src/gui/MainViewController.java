package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> { // criamos uma funcão de inicialização como lambda
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();  // esta linha eh responsavel por dar um update na lista no programa grafico
		});
	}
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}

	
	@Override	
	public void initialize(URL uri, ResourceBundle rb) {
	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) { //foi criado este modulo para teste de popular a tabela no programa grafico
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); // criamos uma chamada para o main principal para inserir o about no menu principal 
		VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();  // pegamos a cena principal pegando o primeiro elemento da view principal scrollpane 
		
		Node mainMenu = mainVBox.getChildren().get(0); //guardando uma referencia para o mainmenu pegando o primeiro filho da mainmenu
		mainVBox.getChildren().clear(); // limpando todos os filhos da mainmenu
		mainVBox.getChildren().add(mainMenu); // agora adicionando no mainmenu os filhos do newVBox 
		mainVBox.getChildren().addAll(newVBox.getChildren());
		
		T Controller = loader.getController();   // ativando a funcão lambda 
		initializingAction.accept(Controller);
		
		}
		catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
			
		}
	}
	
	
	
}
