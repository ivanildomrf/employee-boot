package br.com.imrf.employee.api.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.imrf.employee.api.payload.request.LoginRequest;
import br.com.imrf.employee.api.payload.response.JwtResponse;
import br.com.imrf.employee.security.config.UserDetailsImpl;
import br.com.imrf.employee.security.config.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
@RestController
@RequestMapping("api/authenticate")
public class AuthRestController {

	private static final Logger logger = LoggerFactory.getLogger(AuthRestController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = this.jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles);

		logger.info("User logged with sucess: " + userDetails.getUsername());

		return jwtResponse;
	}

}
