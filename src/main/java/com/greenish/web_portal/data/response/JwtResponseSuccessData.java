package com.greenish.web_portal.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseSuccessData<T> extends ResponseBaseData<T> {

    @JsonProperty("token")
    private JwtResponseData jwtResponseData;

    public JwtResponseSuccessData(String message, JwtResponseData jwtData, T data) {
        super(true, 1, message, data);
        this.jwtResponseData = jwtData;
    }

}
