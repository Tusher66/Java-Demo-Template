package com.greenish.web_portal.data.menu;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleMenuRespData {
    private String roleMenuId;
    private String mainMenuId;
    private String menuId;
    private String roleId;
    private String insertBy;
    private String updateBy;
    private String activeYn;
    private String o_status_code;
    private String o_status_message;
}
