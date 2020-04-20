package br.com.imrf.employee.api.payload.request;

import org.hibernate.validator.constraints.NotBlank;

public class UserRequest {

	@NotBlank
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
