package com.greenish.web_portal.data.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuPermissionListDetailsInfoData {
    @JsonProperty("main_menu_id")
    private String mainMenuId;

    @JsonProperty("main_menu_name")
    private String mainMenuName;

    @JsonProperty("main_menu_status")
    private String mainMenuStatus;

    @JsonProperty("main_menu_url")
    private String mainMenuUrl;

    @JsonProperty("main_menu_icon")
    private String mainMenuIcon;

    @JsonProperty("menu_id")
    private String menuId;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("menu_status")
    private String menuStatus;

    @JsonProperty("part_menu_yn")
    private String partMenuYn;

    @JsonProperty("menu_url")
    private String menuUrl;

    @JsonProperty("menu_component_name")
    private String menuComponentName;

    @JsonProperty("menu_sl_no")
    private String menuSlNo;

    @JsonProperty("menu_icon")
    private String menuIcon;

    @JsonProperty("menu_type")
    private String menuType;

    @JsonProperty("user_menu_access_status")
    private String userMenuAccessStatus;

    @JsonProperty("to")
    private String to;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("label")
    private String label;
}
