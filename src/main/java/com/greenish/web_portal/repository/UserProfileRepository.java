package com.greenish.web_portal.repository;


import com.greenish.web_portal.model.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {


    @Query(value = "select up from UserProfile up " +
            "where up.LUserType.activeYn = 'Y' " +
            "AND (:user_type_id is null or up.LUserType.userTypeId = :user_type_id) " +
            "AND (:search is null or lower(up.firstName) like concat('%', lower(:search), '%') " +
            "OR lower(up.email) like concat('%', lower(:search), '%') ) ")
    Page<UserProfile> findAllUserProfileBySuperAdmin(@Param("search") String search, @Param("user_type_id") Long filterId,
                                                     Pageable pageable);

    UserProfile findByLoginId(String loginId);

}
