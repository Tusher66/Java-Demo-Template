package com.greenish.web_portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    private Integer code;

    public UnauthorizedUserException(String message) {
        super(message);
        this.code = 999;
    }

    public UnauthorizedUserException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
