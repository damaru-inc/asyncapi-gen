
package com.solace.asyncapi;
public interface PublishListener {
	public void onResponse(String messageId);
	public void handleException(String messageId, Exception exception, long timestamp);
}

