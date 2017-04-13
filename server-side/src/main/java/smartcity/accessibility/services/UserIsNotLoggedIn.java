package smartcity.accessibility.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "you are not logged in, please login first.")
public class UserIsNotLoggedIn extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
