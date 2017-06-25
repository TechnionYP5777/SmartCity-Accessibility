package smartcity.accessibility.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yael
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Format of image is not supported")
public class UploadImageFailed extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
