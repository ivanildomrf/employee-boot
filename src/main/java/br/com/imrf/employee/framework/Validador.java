package br.com.imrf.employee.framework;

public interface Validador {
	
	void validar(Object entidade, Class<?>... contextos);

	boolean isValido(Object entidade, Class<?>... grupo);	

}
