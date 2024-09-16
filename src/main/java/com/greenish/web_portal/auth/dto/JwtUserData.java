package com.greenish.web_portal.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserData implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long userId;
    private String loginId; // -- > username
    private Long userTypeId;
    private Long clientId;
    private String userUuid;
    private String email;
    private String timeZone;
}
