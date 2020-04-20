package br.com.imrf.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.imrf.employee.framework.AbstractService;
import br.com.imrf.employee.framework.Repository;
import br.com.imrf.employee.framework.Validador;
import br.com.imrf.employee.model.Person;
import br.com.imrf.employee.repository.EmployeeRepository;
import br.com.imrf.employee.service.EmployeeService;

public class EmployeeServiceImpl extends AbstractService<Person> implements EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeServiceImpl(Validador validador, EmployeeRepository employeeRepository) {
		super(validador);
		this.employeeRepository = employeeRepository;
	}

	@Override
	protected Repository<Person> getRepository() {
		return this.employeeRepository;
	}

	@Override
	protected void regrasNegocioCadastrar(Person entidade) {
	}

	@Override
	protected void regrasNegocioAlterar(Person entidade) {
	}

	@Override
	protected void regrasNegocioExcluir(Person entidade) {

	}

}