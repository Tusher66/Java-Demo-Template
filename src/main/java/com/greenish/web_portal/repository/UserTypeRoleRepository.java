package com.greenish.web_portal.repository;


import com.greenish.web_portal.model.UserTypeRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTypeRoleRepository extends JpaRepository<UserTypeRole, Long> {

    @Query(value = "select v from UserTypeRole v " +
            " where (:search is null or lower(v.role.roleName) like concat('%', lower(:search), '%'))" +
            " and v.activeYn='Y' and v.role.activeYn='Y'")
    Page<UserTypeRole> findAllPagedUserTypeRole(@Param("search") String search, Pageable pageable);

    List<UserTypeRole> findByUserTypeIdAndActiveYn(Long userTypeRoleId, String activeYn);

    UserTypeRole findByUserTypeId(Long userTypeRoleId);
}