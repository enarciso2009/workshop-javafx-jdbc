package gui.util;



import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;


public class Utils {
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static Integer tryParseToInt(String str) {  // este comando irá transformar uma string em numero inteiro
		try {  // caso a string que esta sendo convertida tenha alguma letra ou seja diferente de numero vai dar erro por isso do try 
		return Integer.parseInt(str); // vai converter para numero 
	}
     catch (NumberFormatException e) {  // se não consegui converte ao inves de mostrar um erro vamos deixar o campo como nulo
     }
    	 return null;
     }
}

