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

import br.com.imrf.employee.api.payload.request.PersonRequest;
import br.com.imrf.employee.model.Person;
import br.com.imrf.employee.repository.EmployeeRepository;
import br.com.imrf.employee.security.config.RecursoCriadoEvent;

@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
@RestController
@RequestMapping("api/employee")
public class EmployeeRestController implements AbstractRestController<PersonRequest>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	public @ResponseBody List<Person> listAll() {
		LOGGER.info("EmployeeRestController.listAll");
		return this.employeeRepository.consultarTodos(new Person(), "name");
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize(ROLE_ADMIN_INSERIR)
	public ResponseEntity<String> create(@Valid @RequestBody PersonRequest personRequest, HttpServletResponse response)
			throws Exception {
		LOGGER.info("EmployeeRestController.createNew");
		Person entidade = PersonRequest.toEntity(personRequest);
		this.employeeRepository.cadastrar(entidade);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, entidade.getId()));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
