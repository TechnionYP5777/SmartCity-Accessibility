package smartcity.accessibility.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoAdressFound extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String message;

	public NoAdressFound(String message) {
		this.message = message;
	}
	
	public NoAdressFound() {
		this.message = "no adress was found fr this location";
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

