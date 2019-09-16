package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {  //exceção para validar um formulario

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors = new HashMap<>(); // este map é para guardar as mensagem de erro do formulario nome do campo e mensagem de erro 

	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErrors() { // este get Errors vai retornar as mensagem de erro quando ouver 
		return errors;
	}
	
	public void addError(String fieldName, String errorsMessage) { //este comando sera para adicionar as mensagem de erro
		errors.put(fieldName, errorsMessage);
	}
	
}
