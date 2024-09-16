package com.greenish.web_portal.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomUnauthorizedException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    private final Integer code;

    public CustomUnauthorizedException(String message) {
        super(message);
        this.code=999;
    }
    public CustomUnauthorizedException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
