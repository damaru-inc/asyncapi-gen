
package com.solace.asyncapi;


public  class Order {

	private Integer orderId;
	private String orderDescription;
	private Double price;
	private Account customer;
	private Shipping shipping;

	public Integer getOrderId() {
		return orderId;
	}

	public Order setOrderId(Integer orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public Order setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
		return this;
	}

	public Double getPrice() {
		return price;
	}

	public Order setPrice(Double price) {
		this.price = price;
		return this;
	}

	public Account getCustomer() {
		return customer;
	}

	public Order setCustomer(Account customer) {
		this.customer = customer;
		return this;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public Order setShipping(Shipping shipping) {
		this.shipping = shipping;
		return this;
	}


	public static  class Shipping {

		private Method method;
		private Double cost;
		private Address shipTo;

		public Method getMethod() {
			return method;
		}

		public Shipping setMethod(Method method) {
			this.method = method;
			return this;
		}

		public static enum Method { post,courier }


		public Double getCost() {
			return cost;
		}

		public Shipping setCost(Double cost) {
			this.cost = cost;
			return this;
		}

		public Address getShipTo() {
			return shipTo;
		}

		public Shipping setShipTo(Address shipTo) {
			this.shipTo = shipTo;
			return this;
		}
	}


}

