package com.greenish.web_portal.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    private final String errorDetails;

    public ResourceNotFoundException(String message) {
        super(message);
        this.errorDetails = null;
    }

    public ResourceNotFoundException(String message, String errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

}
