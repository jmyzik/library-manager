package jmyzik.librarymanager.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(nullable = false)
	private String street;
	@Column(nullable = false)
	private int houseNumber;
	@Column(nullable = true)
	private Integer apartmentNumber;
	@Column(nullable = false)
	private String zipCode;
	@Column(nullable = false)
	private String city;
	
	public Address() {}

	public Address(String street, int houseNumber, Integer apartmentNumber, String zipCode, String city) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.apartmentNumber = apartmentNumber;
		this.zipCode = zipCode;
		this.city = city;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Integer getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(Integer apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return (apartmentNumber == null) ?
				String.format("%s %d, %s %s", street, houseNumber, zipCode, city) : 
				String.format("%s %d/%d, %s %s", street, houseNumber, apartmentNumber, zipCode, city);
	}
}