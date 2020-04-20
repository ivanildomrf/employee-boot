package br.com.imrf.employee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import br.com.imrf.employee.core.Change;
import br.com.imrf.employee.core.Identidade;
import br.com.imrf.employee.core.Ordenacao;
import br.com.imrf.employee.core.Register;

@Entity
@Audited
@Table(name = "profile")
public class Profile implements Serializable, Identidade, Ordenacao {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;

	public Profile() {
		super();
	}

	public Profile(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "description", nullable = false)
	@NotNull(message = "error_description_cannot_be_null", groups = { Register.class, Change.class })
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public String getCampoOrderBy() {
		return "description";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profile other = (Profile) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", description=" + description + "]";
	}

}
