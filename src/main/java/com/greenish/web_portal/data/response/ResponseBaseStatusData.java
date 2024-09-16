package com.greenish.web_portal.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResponseBaseStatusData {
    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;
}
