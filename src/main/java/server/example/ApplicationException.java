package server.example;

import io.micronaut.http.HttpStatus;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public ApplicationException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
