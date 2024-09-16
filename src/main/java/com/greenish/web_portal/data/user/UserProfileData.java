package com.greenish.web_portal.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileData {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("dob")
    private LocalDate dob;

    @JsonProperty("login_id")
    private String loginId;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("avatar_size")
    private Long avatarSize;

    @JsonProperty("avatar_type")
    private String avatarType;

    /*@JsonProperty("password")
    private String password;*/

    @JsonProperty("registrationType")
    private String registrationType;

    @JsonProperty("registrationBy")
    private String registrationBy;

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;

    @JsonProperty("activeYn")
    private String activeYn;

    @JsonProperty("linkedUserYn")
    private String linkedUserYn;

    @JsonProperty("userTypeId")
    private Long userTypeId;

    @JsonProperty("lLinkedAccountTypeId")
    private Long lLinkedAccountTypeId;

    private String emailVerifyYn;


}