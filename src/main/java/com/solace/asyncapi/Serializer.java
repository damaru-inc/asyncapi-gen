
package com.solace.asyncapi;
public interface Serializer<T> {
	public String serialize(T object) throws Exception;
	public T deserialize(String string) throws Exception;
}
