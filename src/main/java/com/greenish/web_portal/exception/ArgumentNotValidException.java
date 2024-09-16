package com.greenish.web_portal.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArgumentNotValidException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    private final String errorDetails;

    public ArgumentNotValidException(String message) {
        super(message);
        this.errorDetails = null;
    }

    public ArgumentNotValidException(String message, String errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

}
