package com.greenish.web_portal.controller;


import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.data.menu.MainMenuPermissionDetailsData;
import com.greenish.web_portal.data.menu.MainMenuPermissionRespData;
import com.greenish.web_portal.data.menu.RoleMenuReqData;
import com.greenish.web_portal.data.menu.RoleMenuRespData;
import com.greenish.web_portal.data.response.ResponseSuccessData;
import com.greenish.web_portal.exception.ArgumentNotValidException;
import com.greenish.web_portal.exception.ResourceNotFoundException;
import com.greenish.web_portal.service.MenuService;
import com.greenish.web_portal.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainMenuController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;


    @GetMapping(value = WebApiUrlConstants.MAIN_MENU_PERMISSION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserMenu(@PathVariable("id") Long roleId) {
        try {
            if (roleId == null) {
                throw new ResourceNotFoundException("Invalid roleId!");
            }
            roleService.findByRoleId(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid roleId!"));
            List<MainMenuPermissionDetailsData> mainMenuPermission = menuService.getMainMenuPermission(roleId);
            String msg = mainMenuPermission.isEmpty() ? "No data found" : "Data found";
            MainMenuPermissionRespData mainMenuPermissionRespData = MainMenuPermissionRespData.builder()
                    .menus(mainMenuPermission)
                    .code(1)
                    .status(true)
                    .message(msg)
                    .build();
            return new ResponseEntity<>(mainMenuPermissionRespData, HttpStatus.OK);
        } catch (ArgumentNotValidException e) {
            e.printStackTrace();
            throw new ArgumentNotValidException(e.getMessage());
        }
    }


    /*@PostMapping(value = WebApiUrlConstants.MAIN_MENU_PERMISSION_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveMainMenuPermission(@RequestBody @Valid MainMenuPermissionReqData mainMenuPermissionReqData) {
        try {
            MenuPermissionResponse data = menuService.saveMainMenuPermission(mainMenuPermissionReqData);
            return new ResponseEntity<>(
                    new ResponseSuccessData<>(data.getO_status_message(), data), HttpStatus.OK
            );
        } catch (ArgumentNotValidException e) {
            e.printStackTrace();
            throw new ArgumentNotValidException(e.getMessage());
        }
    }*/

    @PostMapping(value = WebApiUrlConstants.MAIN_MENU_PERMISSION_UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateMainMenuPermission(@RequestBody @Valid RoleMenuReqData roleMenuReqData) {
        try {
            RoleMenuRespData data = menuService.updateMainMenuPermission(roleMenuReqData);
            return new ResponseEntity<>(
                    new ResponseSuccessData<>(
                            data.getO_status_message(), data
                    ), HttpStatus.OK
            );
        } catch (ArgumentNotValidException e) {
            e.printStackTrace();
            throw new ArgumentNotValidException(e.getMessage());
        }
    }

}
