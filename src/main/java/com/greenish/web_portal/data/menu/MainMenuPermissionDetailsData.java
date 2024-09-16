package com.greenish.web_portal.data.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainMenuPermissionDetailsData {

    @JsonProperty("menu_type")
    private String menuType;

    @JsonProperty("menu_visibility")
    private String menuVisibility;

    @JsonProperty("main_menu_id")
    private String mainMenuId;

    @JsonProperty("main_menu_name")
    private String mainMenuName;

    @JsonProperty("menu_id")
    private String menuId;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("menu_url")
    private String menuUrl;

    @JsonProperty("permission_wise_yn")
    private String permissionWiseYn;

    @JsonProperty("part_menu_yn")
    private String partMenuYn;

    @JsonProperty("main_menu_sl")
    private String mainMenuSl;

    @JsonProperty("menu_sl")
    private String menuSl;
}