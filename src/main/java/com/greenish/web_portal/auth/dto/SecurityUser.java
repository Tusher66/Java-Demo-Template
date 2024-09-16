package com.greenish.web_portal.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class SecurityUser implements Serializable {
    private Long userId;
    private String loginId; // -- > username
    private String firstName;
    private String lastName;
    private String email;
    private String userUuid;
    private String password;
    private Long userTypeId;
    private String activeYn;
    private Long clientId;
    private Set<Long> centerIds;
    private Set<SecurityRole> roles;
    private String timeZone;
    public SecurityUser() {
    }

    public SecurityUser(SecurityUser users) {
        this.userId = users.getUserId();
        this.userUuid = users.getUserUuid();
        this.email = users.getEmail();
        this.firstName = users.getFirstName();
        this.lastName = users.getLastName();
        this.loginId = users.getLoginId();
        this.password = users.getPassword();
        this.userTypeId = users.getUserTypeId();
        this.activeYn = users.getActiveYn();
        this.roles = users.getRoles();
        this.clientId = users.getClientId();
        this.centerIds = users.getCenterIds();
        this.timeZone = users.getTimeZone();
    }
}
