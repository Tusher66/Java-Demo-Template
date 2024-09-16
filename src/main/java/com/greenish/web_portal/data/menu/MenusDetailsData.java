package com.greenish.web_portal.data.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenusDetailsData {

//    @JsonProperty("main_menu_id")
    private String  mainMenuId;

//    @JsonProperty("main_menu_name")
    private String  mainMenuName;

//    @JsonProperty("main_menu_status")
    private String  mainMenuStatus;

//    @JsonProperty("main_menu_url")
    private String  mainMenuUrl;

//    @JsonProperty("main_menu_component_name")
    private String  mainMenuComponentName;

//    @JsonProperty("main_menu_visibility")
//    private String  mainMenuVisibility;

//    @JsonProperty("main_menu_sl_no")
    private String  mainMenuSlNo;

//    @JsonProperty("main_menu_icon")
    private String  mainMenuIcon;

//    @JsonProperty("menu_id")
    private String  menuId;

//    @JsonProperty("menu_name")
    private String  menuName;

//    @JsonProperty("menu_status")
    private String  menuStatus;
//
//    @JsonProperty("menu_visibility")
//    private String  menuVisibility;

//    @JsonProperty("part_menu_yn")
    private String  partMenuYn;

//    @JsonProperty("menu_url")
    private String  menuUrl;

//    @JsonProperty("menu_component_name")
    private String  menuComponentName;

//    @JsonProperty("menu_sl_no")
    private String  menuSlNo;

//    @JsonProperty("menu_icon")
//    private String  menuIcon;

//    @JsonProperty("menu_type")
    private String  menuType;

//    @JsonProperty("role_id")
    private String  roleId;

//    @JsonProperty("role_menu_status")
    private String  roleMenuStatus;

//    @JsonProperty("user_menu_access_status")
    private String userMenuAccessStatus;


    @JsonProperty("permission")
    private Boolean  permission;

//    @JsonProperty("parent_menu_show_ui_yn")
    private String  parentMenuShowUiYn;

    @JsonProperty("to")
    private String to;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("label")
    private String label;

    @JsonProperty("menu")
    private List<MenusDetailsData> childMenus;

}