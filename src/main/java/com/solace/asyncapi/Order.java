
package com.solace.asyncapi;


public  class Order {

	private int orderId;
	private String orderDescription;
	private double price;
	private Account customer;
	private Shipping shipping;

	public int getOrderId() {
		return orderId;
	}

	public Order setOrderId(int orderId) {
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

	public double getPrice() {
		return price;
	}

	public Order setPrice(double price) {
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
		private double cost;
		private Address shipTo;

		public Method getMethod() {
			return method;
		}

		public Shipping setMethod(Method method) {
			this.method = method;
			return this;
		}

		public static enum Method { post,courier }


		public double getCost() {
			return cost;
		}

		public Shipping setCost(double cost) {
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

