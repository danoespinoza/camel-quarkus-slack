package domain;

import java.io.Serializable;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SendMessage implements Serializable {

	private static final long serialVersionUID = -5041108593371542949L;

	private String message;

	public SendMessage() {
		super();
	}

	public SendMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SendMessage [message=" + message + "]";
	}
	
}
