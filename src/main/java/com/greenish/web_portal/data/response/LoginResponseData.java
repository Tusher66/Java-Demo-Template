package com.greenish.web_portal.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseData {

    @JsonProperty("id")
    private String id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String fullName;

    @JsonProperty("login_id")
    private String loginId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("user_type_id")
    private String userTypeId;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("avatar_type")
    private String avatarType;

    @JsonProperty("active_yn")
    private String activeYn;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("center_id")
    private String centerId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("emailVerifiedYn")
    private String emailVerifiedYn;

    @JsonProperty("response_date_time")
    private String responseDateTime;

    @JsonProperty("province_id")
    private Long provinceId;

    @JsonProperty("province_name")
    private String provinceName;

    private String userChatEnableYn;
}
