package com.greenish.web_portal.serviceImpl;



import com.greenish.web_portal.Utils.DateTimeUtils;
import com.greenish.web_portal.auth.SecurityConfig;
import com.greenish.web_portal.constant.ResponseMessageConstants;
import com.greenish.web_portal.data.UserProfileWebReqData;
import com.greenish.web_portal.data.response.PaginatedResData;
import com.greenish.web_portal.data.response.RequestBaseData;
import com.greenish.web_portal.data.response.ResponseBaseStatusData;
import com.greenish.web_portal.data.user.UserProfileData;
import com.greenish.web_portal.exception.ArgumentNotValidException;
import com.greenish.web_portal.model.UserProfile;
import com.greenish.web_portal.model.lookup.LUserType;
import com.greenish.web_portal.repository.LUserTypeRepository;
import com.greenish.web_portal.repository.UserProfileRepository;
import com.greenish.web_portal.service.UserProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private LUserTypeRepository lUserTypeRepository;

    @Override
    public ResponseBaseStatusData saveAndUpdateWebUserProfile(UserProfileWebReqData reqData) {

        Long userId = 11L;
        String password = reqData.getPassword();
        String dateOfBirthStr = reqData.getDob();
        String reqDataEmail = reqData.getEmail().trim();
        LocalDate currentDate = LocalDate.now();
        String appRegistrationYn = "Y";

        LUserType luserType = lUserTypeRepository.findById(reqData.getUserTypeId())
                .orElseThrow(() -> new ArgumentNotValidException(ResponseMessageConstants.USER_TYPE_INVALID));

        LocalDate dateOfBirth = null;
        if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            try {
                dateOfBirth = DateTimeUtils.stringToLocalDt(dateOfBirthStr);
            } catch (Exception e) {
                throw new ArgumentNotValidException(ResponseMessageConstants.USER_DOB_NOT_VALID, e.getMessage());
            }
        }

        String activeYn = reqData.getActiveYn();
        if (activeYn == null || activeYn.isEmpty()) {
            activeYn = "N";
        }

        String email = reqDataEmail;
        if (email != null && !email.isEmpty()) {
            email = email.toLowerCase();
        }
        // save
        try {
            UserProfile userProfile = UserProfile.builder()
                    .firstName(reqData.getFirstName())
                    .lastName(reqData.getLastName())
                    .fullName(reqData.getFirstName() + " " + reqData.getLastName())
                    .email(email)
                    .mobileNo(reqData.getMobileNo())
                    .dob(dateOfBirth)
                    .loginId(email)
                    .appRegisterYn(appRegistrationYn)
                    .registrationDate(currentDate)
                    .LUserType(luserType)
                    .activeYn(activeYn)
                    .password(SecurityConfig.passwordEncoder().encode(password))
                    .insertDate(LocalDateTime.now())
                    .insertBy(userId)
                    .build();
            UserProfile user = userProfileRepository.save(userProfile);
            return ResponseBaseStatusData.builder()
                    .status(true)
                    .code(1)
                    .message("USER_SAVE_SUCCESSFUL")
                    .build();

        } catch (Exception e) {
            return ResponseBaseStatusData.builder()
                    .status(false)
                    .code(99)
                    .message("USER_NOT_SAVE_SUCCESSFUL")
                    .build();
        }
    }

    @Override
    public PaginatedResData getAllUserProfile(int page, int size, String sortBy, String sortType, String search, Long filterId,
                                              Long centerId) {
        // get auth user info
        /*SecurityUser securityUser = authUtil.getSecurityUserDetails();*/

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "first_name";
            sortType = "asc";
        }

        if (sortType == null || sortType.isEmpty()) {
            sortType = "desc";
        }

        Page<UserProfile> pageResult;
        Pageable pageRequest;

        /*pageRequest = PageUtils.generatePageRequest((page - 1), size, sortBy, sortType);
        pageResult = userProfileRepository.findAllUserProfileBySuperAdmin(search, filterId, pageRequest);*/


        /*List<UserProfileData> profileListDataList = pageResult.getContent().stream()
                .map(this::buildUserProfileData)
                .collect(Collectors.toList());*/

        RequestBaseData request = RequestBaseData.builder()
                .perPage(size)
                .page(page)
                .sortBy(sortBy)
                .sortType(sortType)
                .search(search)
                .build();

        /*return PageUtils.getPaginatedResData(page, size, request, pageRequest,
                pageResult.getTotalElements(), pageResult.getTotalPages(), profileListDataList);*/
        return null;

    }

    @Override
    public UserProfile findByLoginId(String loginId) {
        return userProfileRepository.findByLoginId(loginId);
    }

    private UserProfileData buildUserProfileData(UserProfile userProfile) {
        UserProfileData userProfileData = new UserProfileData();
        BeanUtils.copyProperties(userProfile, userProfileData);

        if (StringUtils.hasText(userProfile.getAvatar()) && StringUtils.hasText(userProfile.getAvatarType())) {
            String avatar = "data:" + userProfile.getAvatarType() + ";base64, " + userProfile.getAvatar();
            userProfileData.setAvatar(avatar);
            userProfile.setAvatarSize(userProfile.getAvatarSize());
            userProfile.setAvatarType(userProfile.getAvatarType());
        } else {
            userProfileData.setAvatar(null);
        }

        if (userProfileData.getEmailVerifyYn() == null) {
            userProfileData.setEmailVerifyYn("N");
        }

        // set user type
        LUserType lUserType = userProfile.getLUserType();
        userProfileData.setUserTypeId(lUserType != null ? lUserType.getUserTypeId() : null);

        return userProfileData;
    }

    @Override
    public Optional<UserProfile> findByUserId(Long userId) {
        return userProfileRepository.findById(userId);
    }



}
