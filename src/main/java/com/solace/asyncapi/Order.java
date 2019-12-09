
package com.solace.asyncapi;


public class Order { 

private int orderId;
private String orderDescription;


public int getOrderId() {
	return orderId;
}

public Order setOrderId( int orderId ) {
	this.orderId = orderId;
	return this;
}


public String getOrderDescription() {
	return orderDescription;
}

public Order setOrderDescription( String orderDescription ) {
	this.orderDescription = orderDescription;
	return this;
}


}
