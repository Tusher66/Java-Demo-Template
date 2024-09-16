package com.greenish.web_portal.data.menu;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleMenuReqData {
//    @JsonProperty("role_id")
    @NotNull(message = "PLEASE SELECT ROLE!")
    private Long roleId;

//    @JsonProperty("main_menu_id")
    @NotNull(message = "PLEASE SELECT PARENT MENU!")
    private Long mainMenuId;

//    @JsonProperty("menu_id")
    @NotNull(message = "PLEASE SELECT MENU!")
    private Long menuId;

//    @JsonProperty("active_yn")
    private String activeYn;
}
