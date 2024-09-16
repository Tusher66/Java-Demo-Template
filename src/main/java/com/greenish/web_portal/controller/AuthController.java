package com.greenish.web_portal.controller;


import com.greenish.web_portal.Utils.DateTimeUtils;
import com.greenish.web_portal.Utils.StringUtils;
import com.greenish.web_portal.auth.JwtTokenUtil;
import com.greenish.web_portal.auth.dto.SecurityUser;
import com.greenish.web_portal.constant.DbConstant;
import com.greenish.web_portal.constant.ErrorStatusCode;
import com.greenish.web_portal.constant.ResponseMessageConstants;
import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.data.request.LoginReqData;
import com.greenish.web_portal.data.response.JwtResponseData;
import com.greenish.web_portal.data.response.JwtResponseSuccessData;
import com.greenish.web_portal.data.response.LoginResponseData;
import com.greenish.web_portal.exception.UnauthorizedUserException;
import com.greenish.web_portal.model.UserProfile;
import com.greenish.web_portal.model.lookup.LUserType;
import com.greenish.web_portal.service.UserProfileService;
import com.greenish.web_portal.serviceImpl.UserProfileDetailsService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserProfileDetailsService userDetailsService;

    @Autowired
    private UserProfileService userProfileService;

    /*@GetMapping("/getData")
    public ResponseEntity<?> changeUserPassword(@RequestParam(value = "password") String password) {

        return new ResponseEntity<>("check the console", HttpStatus.OK);
    }*/

    @RequestMapping(value = WebApiUrlConstants.AUTH_LOGIN_API, method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginReqData loginReqData, HttpServletRequest request) {

        if (!StringUtils.hasText(loginReqData.getUsername()) || !StringUtils.hasText(loginReqData.getPassword())) {
            throw new UnauthorizedUserException(ErrorStatusCode.INVALID_USER_NAME.getValue(), ErrorStatusCode.INVALID_USER_NAME.getCode());
        }

        String timeZone = loginReqData.getTimeZone();
        /*ZoneId zoneIdFromGmtOffSet = TimeZoneUtils.getZoneIdFromGmtOffSet(timeZone);
        if (zoneIdFromGmtOffSet != null) {
            TimeZoneUtils.getLocalTimeZoneDateTime(timeZone);
        }*/

        final String username = loginReqData.getUsername().toLowerCase();
        UserProfile userProfile = userProfileService.findByLoginId(username);

        if (userProfile == null ) {
            throw new UnauthorizedUserException(ErrorStatusCode.INVALID_USER.getValue(), ErrorStatusCode.INVALID_USER.getCode());
        } else if (userProfile.getLUserType().getUserTypeId().equals(DbConstant.CLIENT) ) {
            throw new UnauthorizedUserException(ErrorStatusCode.INVALID_USER_TYPE.getValue(), ErrorStatusCode.INVALID_USER_TYPE.getCode());
        }

        authenticate(username, loginReqData.getPassword());

        final SecurityUser securityUser = userDetailsService.loadUserByUsername(username);

        LUserType userType = userProfile.getLUserType();

        if (userType == null) {
            throw new UnauthorizedUserException(ErrorStatusCode.INVALID_USER_PASSWORD.getValue(), ErrorStatusCode.INVALID_USER_PASSWORD.getCode());
        } else if (!userProfile.getActiveYn().equals("Y")) {
            throw new UnauthorizedUserException(ErrorStatusCode.USER_NOT_ACTIVATED.getValue(), ErrorStatusCode.USER_NOT_ACTIVATED.getCode());
        }

        LoginResponseData userProfileData = getUserProfileData(userProfile, userType);

        final String token = jwtTokenUtil.generateToken(securityUser, timeZone);
        JwtResponseData jwtResponseData = new JwtResponseData("Bearer", token);
        /*userProfileService.storeFCMToken(loginReqData.getFcmToken(), userProfile);*/
        return ResponseEntity.ok(new JwtResponseSuccessData<>(ResponseMessageConstants.LOGIN_SUCCESSFULLY, jwtResponseData, userProfileData));
    }

    private LoginResponseData getUserProfileData(UserProfile userProfile, LUserType userType) {
        String avatarType = userProfile.getAvatarType();
        String avatar = userProfile.getAvatar();
        if (avatar != null && avatarType != null) {
            avatar = "data:" + avatarType + ";base64, " + userProfile.getAvatar();
        }

        /*long userChatEnableFound = chatFacilityMemberRepo.countByMemberIdAndActiveYn(userProfile.getUserId(), "Y");*/

        return LoginResponseData.builder()
                .id(StringUtils.convertString(userProfile.getUserId()))
//                .encLoginId(cipherUtils.encrypt(String.valueOf(userProfile.getLoginId())))
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .loginId(userProfile.getLoginId())
                .avatar(avatar)
                .avatarType(avatarType)
                .fullName(userProfile.getFullName())
                .email(userProfile.getEmail())
                .emailVerifiedYn(userProfile.getEmailVerifyYn())
                .activeYn(userProfile.getActiveYn())
                .userType(userType.getUserTypeName())
                .userTypeId(StringUtils.convertString(userType.getUserTypeId()))
                .responseDateTime(DateTimeUtils.dateToUiDateMonthStr(LocalDateTime.now()))
                .createdAt(DateTimeUtils.localDtToString(userProfile.getInsertDate()))
                .updatedAt(DateTimeUtils.localDtToString(userProfile.getUpdateDate()))
                /*.provinceId(userProfile.getLProvince() != null ? userProfile.getLProvince().getProvinceId() : null)
                .provinceName(userProfile.getLProvince() != null ? userProfile.getLProvince().getName() : null)*/
                /*.userChatEnableYn(userChatEnableFound > 0 ? "Y" : "N")*/
                .build();
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
