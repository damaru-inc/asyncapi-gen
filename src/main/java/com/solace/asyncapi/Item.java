
package com.solace.asyncapi;


public  class Item {

	private Integer catalogId;
	private String description;
	private Double price;

	public Integer getCatalogId() {
		return catalogId;
	}

	public Item setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Item setDescription(String description) {
		this.description = description;
		return this;
	}

	public Double getPrice() {
		return price;
	}

	public Item setPrice(Double price) {
		this.price = price;
		return this;
	}
}

