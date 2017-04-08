package smartcity.accessibility.services.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such User,please Login")
public class UserDoesNotExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}