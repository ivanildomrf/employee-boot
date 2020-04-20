package br.com.imrf.employee.api.rest.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.imrf.employee.api.payload.request.TeamRequest;
import br.com.imrf.employee.api.payload.request.UserRequest;
import br.com.imrf.employee.framework.json.JsonUtil;
import br.com.imrf.employee.model.Team;
import br.com.imrf.employee.security.config.RecursoCriadoEvent;
import br.com.imrf.employee.service.TeamService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
@RestController
@RequestMapping("api/team")
public class TeamRestController implements Serializable, AbstractRestController<TeamRequest> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(TeamRestController.class);

	private TeamService teamService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	public ResponseEntity<String> listAll() {
		LOGGER.info("TeamRestController.listAll()");
		List<Team> listTeams = this.teamService.consultarTodos(new Team(), "name");
		String jsonObject = JsonUtil.convertListOfObjectsToJsonString(listTeams);
		return new ResponseEntity<>(jsonObject, HttpStatus.OK);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize(ROLE_ADMIN_INSERIR)
	public ResponseEntity<String> createNew(@Valid TeamRequest teamRequest, HttpServletResponse response) {
		LOGGER.info("TeamRestController.createNew()");
		Team entidade = TeamRequest.toEntity(teamRequest);
		this.teamService.cadastrar(entidade);
		String jsonObject = JsonUtil.convertObjectToJsonString(entidade);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, entidade.getId()));
		return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
	}

	@Override
	public Object getUserById(UserRequest userRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getUserById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
