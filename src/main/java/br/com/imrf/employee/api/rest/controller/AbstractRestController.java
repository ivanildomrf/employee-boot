package br.com.imrf.employee.api.rest.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.imrf.employee.api.payload.request.UserRequest;

public interface AbstractRestController<E> {

	String ROLE_ADMIN_INSERIR = "hasAuthority('ROLE_ADMIN_INSERIR')";

	String ROLE_ADMIN_ALTERAR = "hasAuthority('ROLE_ADMIN_ALTERAR')";

	String ROLE_ADMIN_EXCLUIR = "hasAuthority('ROLE_ADMIN_EXCLUIR')";

	String ROLE_ADMIN_CONSULTAR = "hasAuthority('ROLE_ADMIN_CONSULTAR')";

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	Object getUserById(UserRequest userRequest);
	
	@GetMapping("/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	Object getUserById(@PathVariable Integer id);	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	public ResponseEntity<String> listAll();

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize(ROLE_ADMIN_INSERIR)
	public ResponseEntity<String> createNew(@Valid @RequestBody E Object, HttpServletResponse response)
			throws Exception;

}
