package br.com.imrf.employee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.imrf.employee.core.Identidade;
import br.com.imrf.employee.core.Ordenacao;
import br.com.imrf.employee.domain.YesNo;

@Entity
@Audited
@Table(name = "user")
public class User implements Serializable, Identidade, Ordenacao {

	private static final long serialVersionUID = 1L;

	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String CONSULTAR = "_CONSULTAR";
	private static final String INSERIR = "_INSERIR";
	private static final String EXCLUIR = "_EXCLUIR";
	private static final String ALTERAR = "_ALTERAR";

	private Integer id;
	private Person person;
	private String login;
	private String password;
	private Date lastAccess;
	private YesNo active;
	private String accessToken;

	private Set<UserProfile> profiles = new HashSet<UserProfile>();

	private List<String> authorities = new ArrayList<String>();

	public User() {
		super();
	}

	public User(Integer id, Person person, String login, String password, Date lastAccess, YesNo active,
			String accessToken) {
		this.id = id;
		this.person = person;
		this.login = login;
		this.password = password;
		this.lastAccess = lastAccess;
		this.active = active;
		this.accessToken = accessToken;
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

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name = "login", length = 45)
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	@NotAudited
	@Column(name = "password", length = 300)
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_access", nullable = false)
	public Date getLastAccess() {
		return lastAccess;
	}

	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "active", nullable = false)
	public YesNo getActive() {
		return active;
	}

	/**
	 * @param active the isEnable to set
	 */
	public void setActive(YesNo active) {
		this.active = active;
	}

	@Column(name = "access_token", length = 500)
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public Set<UserProfile> getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(Set<UserProfile> profiles) {
		this.profiles = profiles;
	}

	@Transient
	public List<String> getAuthorities() {

		if (authorities.isEmpty()) {
			authorities.add(ROLE_ADMIN + INSERIR);
			authorities.add(ROLE_ADMIN + ALTERAR);
			authorities.add(ROLE_ADMIN + EXCLUIR);
			authorities.add(ROLE_ADMIN + CONSULTAR);
		}

		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	@Override
	@Transient
	public String getCampoOrderBy() {
		return "login";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((profiles == null) ? 0 : profiles.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (profiles == null) {
			if (other.profiles != null)
				return false;
		} else if (!profiles.equals(other.profiles))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [person=" + person + ", login=" + login + ", active=" + active + "]";
	}

}
