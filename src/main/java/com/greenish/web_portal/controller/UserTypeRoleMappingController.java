package com.greenish.web_portal.controller;


import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.data.response.ResponseSuccessData;
import com.greenish.web_portal.data.user.UserTypeRoleData;
import com.greenish.web_portal.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserTypeRoleMappingController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = WebApiUrlConstants.USER_TYPE_ROLE_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUserTypeRole(@Nullable @RequestParam(value = "search", required = false) String search) {

        return new ResponseEntity<>(roleService.getAllUserTypeRole(search), HttpStatus.OK);

    }

    @PostMapping(value = WebApiUrlConstants.USER_TYPE_ROLE_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUpdateUserTypeRole(@RequestBody @Valid UserTypeRoleData userTypeRoleData) {
        UserTypeRoleData resData = roleService.saveUserTypeRole(userTypeRoleData);
        return new ResponseEntity<>(new ResponseSuccessData<>(resData.getOStatusMessage(), resData),
                HttpStatus.OK
        );

    }
    /*@DeleteMapping(value = WebApiUrlConstants.USER_TYPE_ROLE_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUserTypeRole(@RequestBody @Valid UserTypeRoleData userTypeRoleData) {
        UserTypeRoleData resData = roleService.softDeleteUserTypeRole(userTypeRoleData);
        return new ResponseEntity<>(new ResponseSuccessData<>(
                resData.getOStatusMessage(), resData),
                HttpStatus.OK
        );

    }*/

}
