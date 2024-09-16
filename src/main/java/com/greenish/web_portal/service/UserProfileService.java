package com.greenish.web_portal.service;

import com.greenish.web_portal.data.UserProfileWebReqData;
import com.greenish.web_portal.data.response.PaginatedResData;
import com.greenish.web_portal.data.response.ResponseBaseStatusData;
import com.greenish.web_portal.model.UserProfile;

import java.util.Optional;

public interface UserProfileService {
    ResponseBaseStatusData saveAndUpdateWebUserProfile(UserProfileWebReqData reqData);

    PaginatedResData getAllUserProfile(int page, int size, String sortBy, String sortType, String search, Long filterId, Long centerId);

    UserProfile findByLoginId(String loginId);

    Optional<UserProfile> findByUserId(Long userId);
}
