
package com.solace.asyncapi;
import java.util.HashMap;

public class OrderMessage { 


	// topic and messageId: These fields allow the client to see the topic
	// and messageId of a received messages. It is not necessary to set these 
	// when publishing.

	private String topic;

	public String getTopic() {
		return topic;
	}

	public OrderMessage setTopic(String topic) {
		this.topic = topic;
		return this;
	}

	private String messageId;

	public String getMessageId() {
		return messageId;
	}

	public OrderMessage setMessageId(String messageId) {
		this.messageId = messageId;
		return this;
	}

	// Headers with their getters and setters.
	private HashMap<String, Object> headers = new HashMap<>();

	private String messageCreator;

	public String getMessageCreator() {
		return messageCreator;
	}

	public OrderMessage setMessageCreator( String messageCreator ) {
		this.messageCreator = messageCreator;
		headers.put("messageCreator", messageCreator);
		return this;
	}


	// Payload


	private Order order;

	public Order getPayload() {
		return order;
	}

	public OrderMessage setPayload(Order order) {
		this.order = order;
		return this;
	}
}
