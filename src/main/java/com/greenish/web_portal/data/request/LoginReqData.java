package com.greenish.web_portal.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginReqData implements Serializable {
    private static final Long serialVersionUID = 5926468583005150707L;

    @Schema(example = "admin123@gmail.com")
    @JsonProperty("username")
    private String username;

    @Schema(example = "admin123")
    @JsonProperty("password")
    private String password;

    @Schema(defaultValue = "null")
    private String fcmToken; // user for chat notification

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("currentUtcDateTime")
    private String currentUtcDateTime;
}
