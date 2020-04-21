package br.com.imrf.employee.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import br.com.imrf.employee.framework.AbstractRepository;
import br.com.imrf.employee.model.Person;
import br.com.imrf.employee.repository.EmployeeRepository;

@Component
public class EmployeeRepositoryImpl extends AbstractRepository<Person> implements EmployeeRepository {

	@Override
	public EntityManager getEntityManager() {
		return super.getEntityManager();
	}

}