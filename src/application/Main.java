package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
	try {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));  // agora temos uma instanciação Loader no FXML  e tambem colocamos o caminho 
	ScrollPane scrollPane = loader.load();  // chamamos o load e carregamos a view 
	
	scrollPane.setFitToHeight(true); // altura 
	scrollPane.setFitToWidth(true);  // largura 
	
	
	mainScene = new Scene(scrollPane);  
	primaryStage.setScene(mainScene);
	primaryStage.setTitle("Sample JavaFX application");  // titulo do paldo
	primaryStage.show(); // apresentar o titulo na tela 
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


}
