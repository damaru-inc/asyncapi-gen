
package com.solace.asyncapi;
import java.util.HashMap;

public class OrderMessage { 


    // Topic: This field allows the client to see the topic
    // of a received messages. It is not necessary to set this 
    // when publishing.

    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    // Headers with their getters and setters.
    private HashMap<String, Object> headers = new HashMap<>();

    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId( String messageId ) {
        this.messageId = messageId;
        headers.put("messageId", messageId);
    }


    // Payload


    private Order order;

    public Order getPayload() {
        return order;
    }

    public void setPayload(Order order) {
        this.order = order;
    }
}
