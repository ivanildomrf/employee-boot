package br.com.imrf.employee.repository.command;

public class ValidadorSingleResult<E> {

	public E getSingleResult(CommandSingleResult<E> command) {
		try {
			return command.execute();
		} catch (Exception e) {
			return null;
		}
	}

}
