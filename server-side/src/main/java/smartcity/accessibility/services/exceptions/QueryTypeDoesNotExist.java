package smartcity.accessibility.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "this query type does not exists")
public class QueryTypeDoesNotExist extends RuntimeException {
	private static final long serialVersionUID = 11L;
}

