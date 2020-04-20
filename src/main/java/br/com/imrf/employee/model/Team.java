package br.com.imrf.employee.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.imrf.employee.core.Change;
import br.com.imrf.employee.core.Identidade;
import br.com.imrf.employee.core.Ordenacao;
import br.com.imrf.employee.core.Register;

@Entity
@Audited
@Table(name = "teams")
public class Team implements Serializable, Identidade, Ordenacao {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Date creationDate;

	public Team() {
		super();
	}
	
	public Team(String name, Date creationDate) {
		this.name = name;
		this.creationDate = creationDate;
	}	

	public Team(Integer id, String name, Date creationDate) {
		this.id = id;
		this.name = name;
		this.creationDate = creationDate;
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

	@Column(name = "name", nullable = false)
	@NotNull(message = "error_name_cannot_be_null", groups = { Register.class, Change.class })
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Team other = (Team) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + "]";
	}

	@Override
	@Transient
	@JsonIgnoreProperties(ignoreUnknown = true)
	public String getCampoOrderBy() {
		return "name";
	}
}
