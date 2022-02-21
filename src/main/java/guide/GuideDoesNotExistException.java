package guide;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GuideDoesNotExistException extends RuntimeException {
    public GuideDoesNotExistException(String message) {
        super(message);
    }
}
