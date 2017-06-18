package smartcity.accessibility.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Query Name is taken or adress is missing")
public class BadQuery extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String message;

	public BadQuery(String message) {
		this.message = message;
	}
	
	public BadQuery() {
		this.message = "BadQuery";
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
