package smartcity.accessibility.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yael
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NavigationFailed extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

	public NavigationFailed(String message) {
		this.message = message;
	}

	public NavigationFailed() {
		this.message = "We could not find the cause of the problem :(";
	}
	
	public void setMessage(String message){
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
