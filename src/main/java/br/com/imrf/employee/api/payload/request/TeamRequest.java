package br.com.imrf.employee.api.payload.request;

import org.hibernate.validator.constraints.NotBlank;

public class TeamRequest {

	@NotBlank
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
