package br.com.imrf.employee.api.payload.request;

import java.io.IOException;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import br.com.imrf.employee.model.Address;
import br.com.imrf.employee.model.Person;
import br.com.imrf.employee.model.Team;

public class PersonRequest {

	@NotBlank
	private String name;

	@NotBlank
	private Date birthDate;

	@NotBlank
	private String street;

	@NotBlank
	private Integer homeNumber;

	@NotBlank
	private String complement;

	@NotBlank
	private String neighborhood;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotBlank
	private Date hiringDate;

	@NotBlank
	private Object photo;

	@NotBlank
	private String teamName;

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

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the homeNumber
	 */
	public Integer getHomeNumber() {
		return homeNumber;
	}

	/**
	 * @param homeNumber the homeNumber to set
	 */
	public void setHomeNumber(Integer homeNumber) {
		this.homeNumber = homeNumber;
	}

	/**
	 * @return the complement
	 */
	public String getComplement() {
		return complement;
	}

	/**
	 * @param complement the complement to set
	 */
	public void setComplement(String complement) {
		this.complement = complement;
	}

	/**
	 * @return the neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * @param neighborhood the neighborhood to set
	 */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the hiringDate
	 */
	public Date getHiringDate() {
		return hiringDate;
	}

	/**
	 * @param hiringDate the hiringDate to set
	 */
	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	/**
	 * @return the photo
	 */
	public Object getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(Object photo) {
		this.photo = photo;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public static Person toEntity(PersonRequest request) throws IOException {
		
		Person person = new Person();
		person.setName(request.getName());
		person.setBirthDate(request.getBirthDate());
		
		Address address = new Address(request.getStreet(), request.getHomeNumber(),
				request.getComplement(), request.getNeighborhood(), request.getCity(),
				request.getState());
		
		person.setAddress(address);
		person.setHiringDate(request.getHiringDate());
		
		Team team = new Team(request.getTeamName(), new Date());
		
		person.setTeam(team);
		
		return person;
	}

}
