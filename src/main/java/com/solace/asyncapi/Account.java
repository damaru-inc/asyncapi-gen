
package com.solace.asyncapi;


public  class Account {

	private Integer accountId;
	private String firstName;
	private String lastName;

	public Integer getAccountId() {
		return accountId;
	}

	public Account setAccountId(Integer accountId) {
		this.accountId = accountId;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Account setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Account setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
}

