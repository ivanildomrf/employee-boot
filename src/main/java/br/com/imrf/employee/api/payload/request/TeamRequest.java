package br.com.imrf.employee.api.payload.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import br.com.imrf.employee.model.Team;

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
	
	public static Team toEntity(TeamRequest teamRequest) {
		return new Team(teamRequest.getName(), new Date());
	}

}
