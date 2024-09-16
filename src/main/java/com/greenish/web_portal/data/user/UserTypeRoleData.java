package com.greenish.web_portal.data.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTypeRoleData {

    @NotNull(message = "PLEASE SELECT ROLE!")
    private Long roleId;

    @NotNull(message = "PLEASE SELECT USER TYPE!")
    private Long userTypeId;

    private Long updateBy;

    private Integer oStatusCode;

    private String oStatusMessage;

}