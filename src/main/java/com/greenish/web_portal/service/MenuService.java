package com.greenish.web_portal.service;

import com.greenish.web_portal.data.menu.MainMenuPermissionDetailsData;
import com.greenish.web_portal.data.menu.MenusDetailsData;
import com.greenish.web_portal.data.menu.RoleMenuReqData;
import com.greenish.web_portal.data.menu.RoleMenuRespData;
import com.greenish.web_portal.model.Menus;
import com.greenish.web_portal.model.RoleMenus;
import com.greenish.web_portal.model.UserMenuPermission;

import java.util.List;
import java.util.Set;

public interface MenuService {
    List<RoleMenus> findActiveRoleMenuByUserType(Long userTypeId);

    List<UserMenuPermission> findActiveUserMenuPermission(Long userId);

    List<MenusDetailsData> getUserAccessMenus(Long userId, Long userTypeId);

    List<MainMenuPermissionDetailsData> getMainMenuPermission(Long roleId);

    RoleMenuRespData updateMainMenuPermission(RoleMenuReqData roleMenuReqData);

    List<Menus> findMenuByRoleIds(Set<Long> roleIds);
}
