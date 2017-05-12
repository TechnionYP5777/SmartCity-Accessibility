package smartcity.accessibility.services.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Signup failed. (1) user already exist; or (2) our DB has some problem try again later.")
public class SignUpFailed extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
