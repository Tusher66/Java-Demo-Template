package com.greenish.web_portal.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorDetails {
    // private Date timestamp;

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("code")
    // private String code;
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("details")
    private String details;

    // @JsonProperty("data")
    // private T data;

    public ErrorDetails(String message) {
        super();
        this.status = false;
        this.code = 999;
        this.message = message;
    }
    public ErrorDetails(String message, String details) {
        super();
        this.status = false;
        this.code = 999;
        this.message = message;
        this.details = details;
    }

    public ErrorDetails(String message, Integer code) {
        super();
        this.status = false;
        this.code = code;
        this.message = message;
    }

    public ErrorDetails(String message, Integer code, String details) {
        super();
        this.status = false;
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public ErrorDetails(String message, Integer code, boolean status) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
