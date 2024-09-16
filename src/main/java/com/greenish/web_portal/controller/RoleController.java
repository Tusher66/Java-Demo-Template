package com.greenish.web_portal.controller;


import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.data.response.ResponseSuccessData;
import com.greenish.web_portal.data.role.RoleData;
import com.greenish.web_portal.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = WebApiUrlConstants.ROLE_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveRole(@RequestBody @Valid RoleData roleData) {
        RoleData resData = roleService.saveRole(roleData);
        return new ResponseEntity<>(new ResponseSuccessData<>(resData),
                HttpStatus.OK
        );
    }
}
