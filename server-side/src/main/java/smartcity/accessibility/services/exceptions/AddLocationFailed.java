package smartcity.accessibility.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AddLocationFailed extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String message;

	public AddLocationFailed(String message) {
		this.message = message;
	}
	
	public AddLocationFailed() {
		this.message = "adding this location has failed";
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

