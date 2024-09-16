package com.greenish.web_portal.controller.common;

public class WebApiUrlConstants {
    public final static String API_URI_PREFIX = "/api/v1";
    public final static String PATH_VAR_ID = "/{id}";

    public final static String AUTH_API = API_URI_PREFIX + "/auth";
    public final static String AUTH_LOGIN_API = AUTH_API + "/login";
    public final static String AUTH_PASSWORD_GENERATE_API = AUTH_LOGIN_API + "/generate-password";

    public final static String SAVE_USER = API_URI_PREFIX + "/saveUser";
    public final static String USER_PROFILE_API = API_URI_PREFIX + "/user";
    public final static String ACCESS_MENU = API_URI_PREFIX + "/access/menus/";

    public final static String ROLE_API = API_URI_PREFIX + "/role";
    public final static String USER_TYPE_ROLE_API = API_URI_PREFIX + "/user-type-role-mapping";


    public final static String MAIN_MENU_API = API_URI_PREFIX + "/main-menu";
    public final static String MAIN_MENU_PERMISSION = MAIN_MENU_API + "/permission" + PATH_VAR_ID;
    public final static String MAIN_MENU_PERMISSION_CREATE = MAIN_MENU_API + "/create-permission";
    public final static String MAIN_MENU_PERMISSION_UPDATE = MAIN_MENU_API + "/update-permission";

}
