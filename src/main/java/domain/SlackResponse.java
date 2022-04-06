package domain;

import java.io.Serializable;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SlackResponse implements Serializable {

	private static final long serialVersionUID = 3039905153117320196L;

	private int code;
	private String message;

	public SlackResponse() {
		super();
	}

	public SlackResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
