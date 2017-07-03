package smartcity.accessibility.services.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author yael
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Signup failed.user already exist")
public class SignUpFailed extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
