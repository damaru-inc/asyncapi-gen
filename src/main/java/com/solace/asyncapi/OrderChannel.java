
package com.solace.asyncapi;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.DeliveryMode;

import com.solacesystems.jcsmp.EndpointProperties;

import com.solacesystems.jcsmp.JCSMPErrorResponseException;
import com.solacesystems.jcsmp.JCSMPErrorResponseSubcodeEx;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishEventHandler;

import com.solacesystems.jcsmp.Queue;
import com.solacesystems.jcsmp.TextMessage;import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.XMLMessageConsumer;
import com.solacesystems.jcsmp.XMLMessageListener;
import com.solacesystems.jcsmp.XMLMessageProducer;

@Component
public class OrderChannel {

	// Channel name: solace/order/{action}/1/{trace}/{span}
	private static final String PUBLISH_TOPIC = "solace/order/%s/1/%s/%d";
	private static final String SUBSCRIBE_TOPIC = "solace/order/*/1/*/*";

	@Autowired
	private SolaceSession solaceSession;
	private JCSMPSession jcsmpSession;
	private Serializer<Order> serializer;
	private TextMessage textMessage = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
	private XMLMessageProducer producer;
	private XMLMessageConsumer consumer;

	@PostConstruct
	public void init() throws Exception {
		jcsmpSession = solaceSession.getSession();
		serializer = SerializerFactory.getSerializer("application/json", Order.class);
		provisionQueue();
	}

	public void initPublisher(PublishListener publishListener) throws Exception {
		PublishEventHandler handler = new PublishEventHandler(publishListener);
		producer = jcsmpSession.getMessageProducer(handler);
		textMessage.setDeliveryMode(DeliveryMode.PERSISTENT);
	}


	public void subscribe(SubscribeListener listener) throws Exception {
		MessageListener messageListener = new MessageListener(listener);
		consumer = jcsmpSession.getMessageConsumer(messageListener);
		Topic topic = JCSMPFactory.onlyInstance().createTopic(SUBSCRIBE_TOPIC);
		jcsmpSession.addSubscription(topic);
		consumer.start();
	}

	private Topic formatTopic(Action action, String trace, int span) {
		String topicString = String.format(PUBLISH_TOPIC, action, trace, span);
		Topic topic = JCSMPFactory.onlyInstance().createTopic(topicString);
		return topic;
	}

	public void sendOrderMessage(OrderMessage orderMessage, Action action, String trace, int span) throws Exception {
		Topic topic = formatTopic(action, trace, span);
		Order payload = orderMessage.getPayload();
		String payloadString = serializer.serialize(payload);
		textMessage.setText(payloadString);
		producer.send(textMessage, topic);
	}
   
	public void sendOrder(Order order, Action action, String trace, int span) throws Exception {
		Topic topic = formatTopic(action, trace, span);
		String payloadString = serializer.serialize(order);
		textMessage.setText(payloadString);
		producer.send(textMessage, topic);
	}

	public void close() {

		if (consumer != null) {
			consumer.close();		
		}

		solaceSession.close();
	}


	public static enum Action { buyItem,returnItem }

	public interface SubscribeListener {
		public void onReceive(OrderMessage orderMessage);
		public void handleException(Exception exception);
	}
	
	class MessageListener implements XMLMessageListener {

		SubscribeListener listener;
		
		public MessageListener(SubscribeListener listener) {
			this.listener = listener;
		}
		
		@Override
		public void onException(JCSMPException exception) {
			listener.handleException(exception);
		}

		@Override
		public void onReceive(BytesXMLMessage bytesMessage) {
			TextMessage textMessage = (TextMessage) bytesMessage;
			Order payload;
			try {
				payload = serializer.deserialize(textMessage.getText());
				OrderMessage  orderMessage = new OrderMessage();
				orderMessage.setMessageId(textMessage.getMessageId());
				orderMessage.setPayload(payload);
				orderMessage.setTopic(bytesMessage.getDestination().getName());
				listener.onReceive(orderMessage);
			} catch (Exception exception) {
				listener.handleException(exception);
			}			
		}
	}

	public interface PublishListener {
		public void onResponse(String messageId);
		public void handleException(String messageId, Exception exception, long timestamp);
	}
	
	class PublishEventHandler implements JCSMPStreamingPublishEventHandler {
		
		PublishListener listener;
		
		public PublishEventHandler(PublishListener listener) {
			this.listener = listener;
		}

		@Override
		public void handleError(String messageId, JCSMPException exception, long timestamp) {
			listener.handleException(messageId, exception, timestamp);
		}

		@Override
		public void responseReceived(String messageId) {
			listener.onResponse(messageId);
		}
	}

	public void provisionQueue() throws Exception {
		final Queue queue = JCSMPFactory.onlyInstance().createQueue("orderQueue");
		final EndpointProperties endpointProps = new EndpointProperties();
		endpointProps.setPermission(EndpointProperties.PERMISSION_DELETE);
		endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_NONEXCLUSIVE);
		jcsmpSession.provision(queue, endpointProps, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);
		try {
			Topic topic = JCSMPFactory.onlyInstance().createTopic("solace/order/*/1/*/*");
			jcsmpSession.addSubscription(queue, topic, JCSMPSession.WAIT_FOR_CONFIRM);
		} catch (JCSMPErrorResponseException ex) {
			if (ex.getSubcodeEx() != JCSMPErrorResponseSubcodeEx.SUBSCRIPTION_ALREADY_PRESENT) {
				throw ex;
			}
		}
	}

}

