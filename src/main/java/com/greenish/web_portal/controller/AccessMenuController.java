package com.greenish.web_portal.controller;


import com.greenish.web_portal.auth.AuthUtils;
import com.greenish.web_portal.auth.dto.SecurityUserDetails;
import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.data.menu.MenusData;
import com.greenish.web_portal.data.menu.MenusDetailsData;
import com.greenish.web_portal.data.user.UserResponseData;
import com.greenish.web_portal.exception.ArgumentNotValidException;
import com.greenish.web_portal.exception.ResourceNotFoundException;
import com.greenish.web_portal.model.UserProfile;
import com.greenish.web_portal.service.MenuService;
import com.greenish.web_portal.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccessMenuController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthUtils authUtil;

    @GetMapping(value = WebApiUrlConstants.ACCESS_MENU, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserMenu() {
        SecurityUserDetails userDetails = authUtil.getSecurityUserDetails();
        try {
            UserProfile p = userProfileService.findByUserId(userDetails.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid userId!"));
            List<MenusDetailsData> accessMenus = menuService.getUserAccessMenus(p.getUserId(), p.getLUserType().getUserTypeId());
            String msg = accessMenus.isEmpty() ? "No data found" : "Data found";

            UserResponseData user = UserResponseData.builder()
                    .id(p.getUserId().toString())
                    .username(p.getLoginId())
                    .userTypeId(p.getLUserType().getUserTypeId().toString())
                    .activeYn(p.getActiveYn())
                    .build();

            MenusData menusData = MenusData.builder()
                    .menus(accessMenus)
                    .user(user)
                    .code(1)
                    .status(true)
                    .message(msg)
                    .build();

            return new ResponseEntity<>(menusData, HttpStatus.OK);
        } catch (ArgumentNotValidException e) {
            e.printStackTrace();
            throw new ArgumentNotValidException(e.getMessage());
        }

    }

}
