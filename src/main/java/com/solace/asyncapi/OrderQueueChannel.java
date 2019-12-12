
package com.solace.asyncapi;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.ConsumerFlowProperties;

import com.solacesystems.jcsmp.EndpointProperties;
import com.solacesystems.jcsmp.FlowReceiver;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPSession;

import com.solacesystems.jcsmp.Queue;
import com.solacesystems.jcsmp.TextMessage;import com.solacesystems.jcsmp.XMLMessageListener;


@Component
public class OrderQueueChannel {

	// Channel name: orderQueue

	@Autowired
	private SolaceSession solaceSession;
	private JCSMPSession jcsmpSession;
	private Serializer<Order> serializer;
	private FlowReceiver flowReceiver;

	@PostConstruct
	public void init() throws Exception {
		jcsmpSession = solaceSession.getSession();
		serializer = SerializerFactory.getSerializer("application/json", Order.class);
		provisionQueue();
	}



	public void subscribe(SubscribeListener listener) throws Exception {
		MessageListener messageListener = new MessageListener(listener);
		final Queue queue = JCSMPFactory.onlyInstance().createQueue("orderQueue");
		ConsumerFlowProperties flowProps = new ConsumerFlowProperties();
		flowProps.setEndpoint(queue);
		flowReceiver = jcsmpSession.createFlow(messageListener, flowProps);
		flowReceiver.start();
	}



	public void close() {

		if (flowReceiver != null) {
			flowReceiver.close();		
		}

		solaceSession.close();
	}


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

	public void provisionQueue() throws Exception {
		final Queue queue = JCSMPFactory.onlyInstance().createQueue("orderQueue");
		final EndpointProperties endpointProps = new EndpointProperties();
		endpointProps.setPermission(EndpointProperties.PERMISSION_DELETE);
		endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_NONEXCLUSIVE);
		jcsmpSession.provision(queue, endpointProps, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);
		
	}

}

