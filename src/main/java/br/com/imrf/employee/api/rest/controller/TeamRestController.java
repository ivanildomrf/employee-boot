package br.com.imrf.employee.api.rest.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.imrf.employee.api.payload.request.TeamRequest;
import br.com.imrf.employee.model.Team;
import br.com.imrf.employee.repository.TeamRepository;
import br.com.imrf.employee.security.config.RecursoCriadoEvent;

@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
@RestController
@RequestMapping("api/team")
public class TeamRestController implements Serializable, AbstractRestController<TeamRequest> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(TeamRestController.class);

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	public @ResponseBody List<Team> listAll() {
		LOGGER.info("TeamRestController.listAll");
		return this.teamRepository.consultarTodos(new Team(), "name");
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize(ROLE_ADMIN_INSERIR)
	public ResponseEntity<String> create(@Valid @RequestBody TeamRequest teamRequest, HttpServletResponse response) {
		LOGGER.info("TeamRestController.createNew");
		Team entidade = new Team(teamRequest.getName(), new Date());
		this.teamRepository.cadastrar(entidade);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, entidade.getId()));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
