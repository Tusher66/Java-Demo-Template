package com.greenish.web_portal.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorData {

    private static final Long serialVersionUID = 1L;

    @JsonProperty("message")
    private String message;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("status")
    private final boolean status = false;

    public CustomErrorData(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}
