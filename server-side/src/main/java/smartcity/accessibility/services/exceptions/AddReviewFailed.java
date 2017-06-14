package smartcity.accessibility.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yael
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AddReviewFailed extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
