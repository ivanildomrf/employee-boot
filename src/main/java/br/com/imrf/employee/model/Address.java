package br.com.imrf.employee.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private String street;
	private Integer homeNumber;
	private String complement;
	private String neighborhood;
	private String city;
	private String state;

	public Address() {
		super();
	}

	public Address(String street, Integer homeNumber, String complement, String neighborhood, String city, String state) {
		this.street = street;
		this.homeNumber = homeNumber;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
	}

	/**
	 * @return the propertied street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the propertied street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the propertied homeNumber
	 */
	public Integer getHomeNumber() {
		return homeNumber;
	}

	/**
	 * @param homeNumber the propertied homeNumber to set
	 */
	public void setHomeNumber(Integer homeNumber) {
		this.homeNumber = homeNumber;
	}

	/**
	 * @return the propertied complement
	 */
	public String getComplement() {
		return complement;
	}

	/**
	 * @param complement the propertied complement to set
	 */
	public void setComplement(String complement) {
		this.complement = complement;
	}

	/**
	 * @return the propertied neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * @param neighborhood the propertied neighborhood to set
	 */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	/**
	 * @return the propertied city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the propertied city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the propertied city
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param city the propertied city to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((homeNumber == null) ? 0 : homeNumber.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
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
		Address other = (Address) obj;
		if (homeNumber == null) {
			if (other.homeNumber != null)
				return false;
		} else if (!homeNumber.equals(other.homeNumber))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", homeNumber=" + homeNumber + "]";
	}

}
