
package com.solace.asyncapi;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPStreamingPublishEventHandler;

public class PublishEventHandler implements JCSMPStreamingPublishEventHandler {

	@Override
	public void handleError(String messageId, JCSMPException cause, long timestamp) {
		System.out.printf("Error: %s %s %d\n", messageId, cause, timestamp);
		
	}

	@Override
	public void responseReceived(String messageId) {
		System.out.printf("Publisher response: %s\n", messageId);
	}

}
