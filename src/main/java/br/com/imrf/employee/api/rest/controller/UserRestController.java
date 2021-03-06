package br.com.imrf.employee.api.rest.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.imrf.employee.api.payload.request.UserRequest;
import br.com.imrf.employee.api.payload.response.UserResponse;
import br.com.imrf.employee.model.User;
import br.com.imrf.employee.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
@RestController
@RequestMapping("api/user")
public class UserRestController implements Serializable, AbstractRestController<UserRequest> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	public UserResponse getUserById(@PathVariable Integer id) {
		LOGGER.info("UserRestController.getUserById");
		User user = this.userRepository.consultarPorId(id);
		return new UserResponse(user.getId(), user.getLogin(), user.getPerson().getName(), null);
	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(ROLE_ADMIN_CONSULTAR)
	public @ResponseBody List<User> listAll() {
		LOGGER.info("UserRestController.listAll");
		return this.userRepository.consultarTodos(new User(), "login");
	}

	@Override
	@PostMapping
	@PreAuthorize(ROLE_ADMIN_INSERIR)	
	public ResponseEntity<String> create(UserRequest Object, HttpServletResponse response) throws Exception {
		LOGGER.info("UserRestController.listAll");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}