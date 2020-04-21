package br.com.imrf.employee.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import br.com.imrf.employee.framework.AbstractRepository;
import br.com.imrf.employee.model.Team;
import br.com.imrf.employee.repository.TeamRepository;

@Component
public class TeamRepositoryImpl extends AbstractRepository<Team> implements TeamRepository {

	@Override
	public EntityManager getEntityManager() {
		return super.getEntityManager();
	}

}