package com.greenish.web_portal.serviceImpl;

import com.greenish.web_portal.Utils.StringUtils;
import com.greenish.web_portal.auth.AuthUtils;
import com.greenish.web_portal.auth.dto.SecurityUserDetails;
import com.greenish.web_portal.constant.DbConstant;
import com.greenish.web_portal.constant.ResponseMessageConstants;
import com.greenish.web_portal.data.menu.*;
import com.greenish.web_portal.data.nativeobj.iMainMenuPermissionDetailsInfo;
import com.greenish.web_portal.data.nativeobj.iMenuPermissionDetailsInfo;
import com.greenish.web_portal.exception.ArgumentNotValidException;
import com.greenish.web_portal.exception.CrudException;
import com.greenish.web_portal.model.*;
import com.greenish.web_portal.repository.*;
import com.greenish.web_portal.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private UserMenuPermissionRepository userMenuPermissionRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MainMenusRepository mainMenusRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthUtils authUtil;

    @Override
    public List<RoleMenus> findActiveRoleMenuByUserType(Long userTypeId) {
        return roleMenuRepository.findActiveRoleMenuByUserType(userTypeId);
    }

    @Override
    public List<UserMenuPermission> findActiveUserMenuPermission(Long userId) {
        return userMenuPermissionRepository.findByUserIdAndActiveYn(userId, "Y");
    }

    @Override
    public List<MenusDetailsData> getUserAccessMenus(Long userId, Long userTypeId) {
        List<iMenuPermissionDetailsInfo> userPermissions = mainMenusRepository.getUserPermissionUsingNativeQuery(userId, userTypeId);
        List<MenusDetailsData> menuPermissionDataList = userPermissions.stream()
                .map(this::getUserMenuAccessDetailsInfoData)
                .collect(Collectors.toList());

        List<MenusDetailsData> finalPermissionDataList = precessAndSetUserMenuPermission(menuPermissionDataList);

        List<MenusDetailsData> permissionParentChildDataList = new ArrayList<>();
        MenusDetailsData parentData;
        for (MenusDetailsData vData : finalPermissionDataList) {
            if ((vData.getPermission() == null || !vData.getPermission()) && !userTypeId.equals(DbConstant.SUPER_ADMIN)) {
                continue;
            }
            if (vData.getMenuType().equals("PARENT") && vData.getMainMenuStatus().equals("Y")) {
                parentData = vData;
                List<MenusDetailsData> child = finalPermissionDataList.stream()
                        .filter(p -> Objects.nonNull(p)
                                && vData.getMainMenuId() != null
                                && p.getMainMenuId() != null
                                && vData.getMainMenuId().equals(p.getMainMenuId())
                                && p.getMenuType().equals("CHILD")
                                && p.getPermission()
                        )
                        .collect(Collectors.toList());

                long mainSubMenuCount = finalPermissionDataList.stream()
                        .filter(p -> Objects.nonNull(p)
                                && vData.getMainMenuId() != null
                                && p.getMainMenuId() != null
                                && vData.getMainMenuId().equals(p.getMainMenuId())
                                && p.getPartMenuYn() != null
                                && p.getPartMenuYn().equals("N")
                                && p.getMenuType().equals("CHILD")
                                && p.getPermission()
                        ).count();

                parentData.setParentMenuShowUiYn((mainSubMenuCount > 0 || parentData.getMainMenuName().equalsIgnoreCase("home")) ? "Y" : "N");
                parentData.setChildMenus(child);
                permissionParentChildDataList.add(parentData);
            }
        }

        return permissionParentChildDataList;
    }

    private List<MenusDetailsData> precessAndSetUserMenuPermission(List<MenusDetailsData> permissionDataList) {
        List<MenusDetailsData> finalPermissionDataList = new ArrayList<>();
        List<String> menuIds = new ArrayList<>();
        for (MenusDetailsData v : permissionDataList) {
            if (v.getMenuId() == null || !menuIds.contains(v.getMenuId())) {
                if (v.getMenuId() != null) {
                    menuIds.add(v.getMenuId());
                }
//            foreach ($menuData as $k => $v) {
//                if ($v->menu_type == 'PARENT' && strpos($v->menu_url, '#') !== false) {
                if (v.getMenuType().equals("PARENT") && (v.getMenuUrl().contains("#") || v.getMenuUrl().contains("1"))) {
                    v.setPermission(true);
//                }elseif( $v->user_menu_insert_status == 'Y' ||  $v->user_menu_edit_status == 'Y' || $v->user_menu_delete_status == 'Y') {
                } else if ("Y".equals(v.getUserMenuAccessStatus())) {
                    v.setPermission(true);
//                }elseif(!empty($v->role_id) && ($v->user_menu_insert_status == 'N' &&  $v->user_menu_edit_status == 'N' && $v->user_menu_delete_status == 'N')){
                } else if (StringUtils.hasLength(v.getRoleId())
                        && ("N".equals(v.getUserMenuAccessStatus()))) {
                    v.setPermission(false);
                } else if (!StringUtils.hasLength(v.getRoleId())) {
                    v.setPermission(false);
                } else {
                    v.setPermission(true);
                }
                finalPermissionDataList.add(v);
            }
        }
        return finalPermissionDataList;
    }

    private MenusDetailsData getUserMenuAccessDetailsInfoData(iMenuPermissionDetailsInfo p) {
        return MenusDetailsData.builder()
                .mainMenuId(p.getMAIN_MENU_ID())
                .mainMenuName(p.getMAIN_MENU_NAME())
                .mainMenuStatus(p.getMAIN_MENU_STATUS())
                .mainMenuUrl(p.getMAIN_MENU_URL())
                .mainMenuComponentName(p.getMAIN_MENU_COMPONENT_NAME())
                .mainMenuSlNo(p.getMAIN_MENU_SL_NO())
                .mainMenuIcon(p.getMAIN_MENU_ICON())
                .menuId(p.getMENU_ID())
                .menuName(p.getMENU_NAME())
                .menuStatus(p.getMENU_STATUS())
                .partMenuYn(p.getPART_MENU_YN())
                .menuUrl(p.getMENU_URL())
                .menuComponentName(p.getMENU_COMPONENT_NAME())
                .menuSlNo(p.getMENU_SL_NO())
                .menuType(p.getMENU_TYPE())
                .roleMenuStatus(p.getROLE_MENU_STATUS())
                .roleId(p.getROLE_ID())
                .childMenus(Collections.emptyList())
                .icon("PARENT".equals(p.getMENU_TYPE())? p.getMAIN_MENU_ICON() : p.getMENU_ICON())
                .to("PARENT".equals(p.getMENU_TYPE())? p.getMAIN_MENU_URL() : p.getMENU_URL())
                .label("PARENT".equals(p.getMENU_TYPE())? p.getMAIN_MENU_NAME() : p.getMENU_NAME())
                .build();
    }

    private MenuPermissionListDetailsInfoData getMenuPermissionDetailsInfoData(iMenuPermissionDetailsInfo p) {
        return MenuPermissionListDetailsInfoData.builder()
                .mainMenuId(p.getMAIN_MENU_ID())
                .mainMenuName(p.getMAIN_MENU_NAME())
                .mainMenuStatus(p.getMAIN_MENU_STATUS())
                .mainMenuUrl(p.getMAIN_MENU_URL())
                .mainMenuIcon(p.getMAIN_MENU_ICON())
                .menuId(p.getMENU_ID())
                .menuName(p.getMENU_NAME())
                .menuStatus(p.getMENU_STATUS())
                .partMenuYn(p.getPART_MENU_YN())
                .menuUrl(p.getMENU_URL())
                .menuComponentName(p.getMENU_COMPONENT_NAME())
                .menuSlNo(p.getMENU_SL_NO())
                .menuIcon(p.getMENU_ICON())
                .menuType(p.getMENU_TYPE())
                .userMenuAccessStatus(p.getUSER_MENU_ACCESS_STATUS())
                .icon("PARENT".equals(p.getMENU_TYPE())? p.getMAIN_MENU_ICON() : p.getMENU_ICON())
                .to("PARENT".equals(p.getMENU_TYPE())? p.getMAIN_MENU_URL() : p.getMENU_URL())
                .label("PARENT".equals(p.getMENU_TYPE())? p.getMAIN_MENU_NAME() : p.getMENU_NAME())
                .build();
    }

    @Override
    public List<MainMenuPermissionDetailsData> getMainMenuPermission(Long roleId) {
        List<iMainMenuPermissionDetailsInfo> mainMenuPermissions = mainMenusRepository.getMainMenuPermissionUsingNativeQuery(roleId);
        return mainMenuPermissions.stream()
                .map(this::getMainMenuPermissionDetailsData)
                .collect(Collectors.toList());
    }

    private MainMenuPermissionDetailsData getMainMenuPermissionDetailsData(iMainMenuPermissionDetailsInfo p) {
        return MainMenuPermissionDetailsData.builder()
                .menuType(p.getMENU_TYPE())
                .menuVisibility(p.getMENU_VISIBILITY())
                .mainMenuId(p.getMAIN_MENU_ID())
                .mainMenuName(p.getMAIN_MENU_NAME())
                .menuId(p.getMENU_ID())
                .menuName(p.getMENU_NAME())
                .menuUrl(p.getMENU_URL())
                .permissionWiseYn(p.getPERMISSION_WISE_YN() != null ? p.getPERMISSION_WISE_YN() : "N")
                .partMenuYn(p.getPART_MENU_YN())
                .mainMenuSl(p.getMAIN_MENU_SL())
                .menuSl(p.getMENU_SL())
                .build();
    }
    @Override
    public RoleMenuRespData updateMainMenuPermission(RoleMenuReqData roleMenuReqData) {
        RoleMenuRespData roleMenuRespData = null;
        SecurityUserDetails securityUserDetails = authUtil.getSecurityUserDetails();
        //validating role
        Role role = roleRepository.findById(roleMenuReqData.getRoleId())
                .orElseThrow(() -> new ArgumentNotValidException("NO VALID ROLE FOUND!"));

        //validating main menu
        MainMenus mainMenus = mainMenusRepository.findById(roleMenuReqData.getMainMenuId())
                .orElseThrow(() -> new ArgumentNotValidException("NO VALID MAIN MENU FOUND!"));

        //validating menu
        Menus menus = menuRepository.findById(roleMenuReqData.getMenuId())
                .orElseThrow(() -> new ArgumentNotValidException("NO VALID MENU FOUND!"));

        if (roleMenuReqData.getActiveYn() != null && roleMenuReqData.getActiveYn().equals("Y")) {
            //checking already exists or not
            if (roleMenuRepository.countDistinctByRoleIdAndMenuId(role.getRoleId(), menus.getMenuId()) > 0) {
                throw new ArgumentNotValidException("THIS MENU ALREADY EXIST IN THIS ROLE!");
            } else {
                RoleMenus roleMenus = new RoleMenus();
                roleMenus.setRoleId(role.getRoleId());
                roleMenus.setMainMenuId(mainMenus.getMainMenuId());
                roleMenus.setMenuId(menus.getMenuId());
                roleMenus.setActiveYn(roleMenuReqData.getActiveYn());

                roleMenus.setInsertBy(securityUserDetails.getUserId());
                roleMenus.setInsertDate(LocalDateTime.now());
                roleMenus = roleMenuRepository.save(roleMenus);
                roleMenuRespData = buildRoleMenuRespData(roleMenus, "1", "ROLE MENU SAVED SUCCESSFULLY.");

            }

        } else {
            if (!securityUserDetails.getUserTypeId().equals(DbConstant.SUPER_ADMIN)) {
                /*throw new CrudException("ONLY SUPER ADMIN CAN DELETE ROLE MENU!");*/
                throw new CrudException(ResponseMessageConstants.UNAUTHORIZED_USER);
            }
            List<RoleMenus> roleMenus = roleMenuRepository.findByRoleIdAndMenuIdAndMainMenuId(roleMenuReqData.getRoleId(), roleMenuReqData.getMenuId(),
                    roleMenuReqData.getMainMenuId());
            if (!roleMenus.isEmpty()) {
                roleMenuRepository.deleteAllInBatch(roleMenus);
                roleMenuRespData = buildRoleMenuRespData(roleMenus.stream().findFirst()
                        .orElseThrow(() -> new CrudException("NO DATA FOUND FOR DELETE ROLE MENU!")), "1", "ROLE MENU REMOVED SUCCESSFULLY.");
            } else {
                throw new CrudException("NO DATA FOUND FOR DELETE ROLE MENU!");
            }
        }
        return roleMenuRespData;
    }

    private RoleMenuRespData buildRoleMenuRespData(RoleMenus roleMenus, String oStatusCode, String message) {
        RoleMenuRespData roleMenuRespData = new RoleMenuRespData();
        roleMenuRespData.setRoleMenuId(StringUtils.convertString(roleMenus.getRoleMenusId()));
        roleMenuRespData.setRoleId(StringUtils.convertString(roleMenus.getRoleId()));
        roleMenuRespData.setMainMenuId(StringUtils.convertString(roleMenus.getMainMenuId()));
        roleMenuRespData.setMenuId(StringUtils.convertString(roleMenus.getMenuId()));
        roleMenuRespData.setInsertBy(StringUtils.convertString(roleMenus.getInsertBy()));
        roleMenuRespData.setUpdateBy(StringUtils.convertString(roleMenus.getUpdateBy()));
        roleMenuRespData.setActiveYn(roleMenus.getActiveYn());
        roleMenuRespData.setO_status_code(oStatusCode);
        roleMenuRespData.setO_status_message(message);
        return roleMenuRespData;
    }

    @Override
    public List<Menus> findMenuByRoleIds(Set<Long> menuIds) {
        return menuRepository.findMenuByIds(menuIds);
    }
}
