package com.greenish.web_portal.serviceImpl;


import com.greenish.web_portal.auth.dto.SecurityRole;
import com.greenish.web_portal.auth.dto.SecurityUser;
import com.greenish.web_portal.auth.dto.SecurityUserDetails;
import com.greenish.web_portal.model.Menus;
import com.greenish.web_portal.model.RoleMenus;
import com.greenish.web_portal.model.UserMenuPermission;
import com.greenish.web_portal.model.UserProfile;
import com.greenish.web_portal.service.MenuService;
import com.greenish.web_portal.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserProfileDetailsService implements UserDetailsService {
    @Autowired
    private UserProfileService userProfileService;

    /*@Autowired
    private MenuService menuService;*/
    @Autowired
    private MenuService menuService;

    /*@Autowired
    private VehicleCenterService vehicleCenterService;

    @Autowired
    private ClientInfoService clientInfoService;*/

    @Override
    public SecurityUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileService.findByLoginId(username);

        if (userProfile == null) {
            throw new UsernameNotFoundException("INVALID USER ID / PASSWORD");
        }

//        if (userProfileService.isVerifiedLinkedUser(userProfile)) {
//            userProfile.setPassword(bcryptEncoder.encode(DbConstant.SOCIAL_PLATFORM_USER_PASSWORD));
//        }

        Long userTypeId = userProfile.getLUserType().getUserTypeId();

        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserId(userProfile.getUserId());
        securityUser.setPassword(userProfile.getPassword());
        securityUser.setLoginId(userProfile.getLoginId());
        securityUser.setFirstName(userProfile.getFirstName());
        securityUser.setLastName(userProfile.getLastName());
        securityUser.setEmail(userProfile.getEmail());
        securityUser.setUserTypeId(userTypeId);
        securityUser.setUserUuid(userProfile.getUserUuid());
        securityUser.setActiveYn(userProfile.getActiveYn());
//        securityUser.setCenterId(userProfile.getVehicleCenter() != null ? userProfile.getVehicleCenter().getCenterId() : null);

        /*if (DbConstant.FACILITY_MANAGER.equals(userTypeId)) {
            Set<Long> centerIds = vehicleCenterService.getVehicleCenterIdsByManagerId(userProfile.getUserId());
            if (centerIds != null && centerIds.size() > 0) {
                securityUser.setCenterIds(centerIds);
            }
        }*/


        List<RoleMenus> roleMenus = menuService.findActiveRoleMenuByUserType(userTypeId);
        Set<Long> allowedRoles = roleMenus.stream()
                .map(RoleMenus::getMenuId)
                .collect(Collectors.toSet());

        List<UserMenuPermission> userMenuPermissions = menuService.findActiveUserMenuPermission(userProfile.getUserId());
        for (UserMenuPermission permission : userMenuPermissions) {

            if (permission.getActiveYn() != null && permission.getActiveYn().equals("Y")) {
                allowedRoles.add(permission.getMenuId());
            } else {
                if (permission.getMenuId() != null) {
                    allowedRoles.remove(permission.getMenuId());
                }
            }
        }

        List<Menus> menusList = menuService.findMenuByRoleIds(allowedRoles);
//        System.out.println("############## \nUserName: " + securityUser.getFirstName() + " userId: " + securityUser.getUserId()
//                + " userType:" + securityUser.getUserTypeId());
//        System.out.print("Total user menus:" + allowedRoles.size() + "\n");
//        menusList.forEach(p -> System.out.print("[" + p.getMenuId() + ":" + p.getMenuName() + "] "));
//        System.out.println("\n##############");

        Set<SecurityRole> securityRoles = allowedRoles.stream()
                .map(p -> new SecurityRole(p.toString()))
                .collect(Collectors.toSet());

        securityUser.setRoles(securityRoles);

        /*ClientInfo clientInfo = clientInfoService.getClientInfoByUserId(userProfile.getUserId());

        if (clientInfo != null) {
            securityUser.setClientId(clientInfo.getClientId());
        }*/
        return new SecurityUserDetails(securityUser);
    }

}