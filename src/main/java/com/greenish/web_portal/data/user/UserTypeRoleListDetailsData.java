package com.greenish.web_portal.data.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTypeRoleListDetailsData {

    private String userTypeId;

    private String userTypeName;

    private String activeYn;

//    @JsonProperty("roles")
//    private List<UserTypeRolesData> roles;

    private Long roleId;
}