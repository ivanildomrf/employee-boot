package br.com.imrf.employee.api.payload.response;

import org.hibernate.validator.constraints.NotBlank;

public class UserResponse {

	@NotBlank
	private Integer id;

	@NotBlank
	private String username;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;
	
	public UserResponse(Integer id, String username, String firstName, String lastName) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
