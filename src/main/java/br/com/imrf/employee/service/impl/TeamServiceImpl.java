package br.com.imrf.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.imrf.employee.framework.AbstractService;
import br.com.imrf.employee.framework.Repository;
import br.com.imrf.employee.framework.Validador;
import br.com.imrf.employee.model.Team;
import br.com.imrf.employee.repository.TeamRepository;
import br.com.imrf.employee.service.TeamService;

public class TeamServiceImpl extends AbstractService<Team> implements TeamService {

	private TeamRepository teamRepository;

	@Autowired
	public TeamServiceImpl(Validador validador, TeamRepository teamRepository) {
		super(validador);
		this.teamRepository = teamRepository;
	}
	
	@Override
	protected Repository<Team> getRepository() {
		return this.teamRepository;
	}	

	@Override
	protected void regrasNegocioCadastrar(Team entidade) {
	}

	@Override
	protected void regrasNegocioAlterar(Team entidade) {
	}

	@Override
	protected void regrasNegocioExcluir(Team entidade) {

	}

}
