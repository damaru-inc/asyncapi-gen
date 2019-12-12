
package com.solace.asyncapi;


public  class Address {

	private String streetAddress;
	private String city;
	private String province;
	private String postalCode;

	public String getStreetAddress() {
		return streetAddress;
	}

	public Address setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
		return this;
	}

	public String getCity() {
		return city;
	}

	public Address setCity(String city) {
		this.city = city;
		return this;
	}

	public String getProvince() {
		return province;
	}

	public Address setProvince(String province) {
		this.province = province;
		return this;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public Address setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}
}

