package br.com.imrf.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.imrf.employee.framework.AbstractService;
import br.com.imrf.employee.framework.Repository;
import br.com.imrf.employee.framework.Validador;
import br.com.imrf.employee.model.User;
import br.com.imrf.employee.repository.UserRepository;
import br.com.imrf.employee.service.UserService;

public class UserServiceImpl extends AbstractService<User> implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(Validador validador, UserRepository userRepository) {
		super(validador);
		this.userRepository = userRepository;
	}
	
	@Override
	protected Repository<User> getRepository() {
		return this.userRepository;
	}	

	@Override
	protected void regrasNegocioCadastrar(User entidade) {
	}

	@Override
	protected void regrasNegocioAlterar(User entidade) {
	}

	@Override
	protected void regrasNegocioExcluir(User entidade) {

	}

}
