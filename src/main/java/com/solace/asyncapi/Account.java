
package com.solace.asyncapi;


public  class Account {

	private int accountId;
	private String firstName;
	private String lastName;

	public int getAccountId() {
		return accountId;
	}

	public Account setAccountId(int accountId) {
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

