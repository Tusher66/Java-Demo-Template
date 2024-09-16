package com.greenish.web_portal.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseData {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("email_verified_at")
    private String emailVerifiedAt;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("user_type_id")
    private String userTypeId;

    @JsonProperty("brta_employee_id")
    private String brtaEmployeeId;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("active_yn")
    private String activeYn;

}