package br.com.imrf.employee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "user_profile")
public class UserProfile implements Serializable, Identidade, Ordenacao {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private User user;
	private Profile profile;

	public UserProfile() {
	}

	public UserProfile(Integer id, User user, Profile profile) {
		this.id = id;
		this.user = user;
		this.profile = profile;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull(message = "O Usuário não pode ser nulo.", groups = { Register.class, Change.class })
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	@NotNull(message = "O Perfil não pode ser nulo.", groups = { Register.class, Change.class })
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	@Transient
	public String getCampoOrderBy() {
		return "perfil.descricao";
	}

}
