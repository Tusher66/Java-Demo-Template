package com.greenish.web_portal.controller;


import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.data.UserProfileWebReqData;

import com.greenish.web_portal.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping(value = WebApiUrlConstants.SAVE_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAndUpdateWebUserProfile(@RequestBody @Valid UserProfileWebReqData reqData) throws IOException {


        return new ResponseEntity<>(userProfileService.saveAndUpdateWebUserProfile(reqData), HttpStatus.OK);
    }
    @GetMapping(value = WebApiUrlConstants.USER_PROFILE_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUserProfile(
            @RequestParam(value = "per_page", required = false, defaultValue = "10") int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @Nullable @RequestParam(value = "sort_by", required = false) String sortBy,
            @Nullable @RequestParam(value = "sort_type", required = false) String sortType,
            @Nullable @RequestParam(value = "search", required = false) String search,
            @Nullable @RequestParam(value = "user_type", required = false) Long filterId,
            @Nullable @RequestParam(value = "center_id", required = false) Long center_id
    ) {
        return new ResponseEntity<>(
                userProfileService.getAllUserProfile(page, size, sortBy, sortType, search, filterId, center_id),
                HttpStatus.OK
        );
    }

    /*@Override
    public UserProfile findByLoginId(String loginId) {
        return userProfileRepository.findByLoginId(loginId);
    }*/
}
