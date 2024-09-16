package com.greenish.web_portal.repository;


import com.greenish.web_portal.data.valuemap.LabelCodeDataStr;
import com.greenish.web_portal.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /*@Query("select new com.cnsbd.carwash.data.valuemap.LabelCodeDataStr(r.roleName, concat(r.roleId, '')) " +
            "from Role r " +
            "where r.showUiYn = 'Y' " +
            "order by r.roleId asc")
    List<LabelCodeDataStr> findAllRole();*/

    @Query("select new com.greenish.web_portal.data.valuemap.LabelCodeDataStr(r.roleName, concat(r.roleId, '')) " +
            "from Role r " +
            "where r.showUiYn = 'Y' " +
            "order by r.roleId asc")
    List<LabelCodeDataStr> findAllRole();

    @Query(value = "select v from Role v " +
            "where v.showUiYn = 'Y' and (:search is null or lower(v.roleName) like concat('%', lower(:search), '%'))")
    Page<Role> findAllPagedRole(@Param("search") String search, Pageable pageable);


    long countByRoleNameIgnoreCase(String roleName);

    long countByRoleNameIgnoreCaseAndRoleIdNot(String roleName, Long roleId);

    Optional<Role> findByRoleIdAndActiveYn(Long roleId, String activeYn);

    Optional<Role> findByRoleId(Long roleId);
}
