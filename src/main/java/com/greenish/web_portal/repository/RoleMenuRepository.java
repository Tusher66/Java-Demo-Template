package com.greenish.web_portal.repository;


import com.greenish.web_portal.model.RoleMenus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenus, Long> {

    List<RoleMenus> findByMenuIdAndActiveYn(Long menuId, String active);

    @Query("select r from RoleMenus r, UserTypeRole u where r.roleId = u.roleId and r.activeYn='Y'"
            + " and u.activeYn='Y' and u.userTypeId= :userTypeId")
    List<RoleMenus> findActiveRoleMenuByUserType(@Param("userTypeId") Long userTypeId);

    Long countDistinctByRoleIdAndMenuId(Long roleId, Long menuId);

    List<RoleMenus> findByRoleIdAndMenuIdAndMainMenuId(Long roleId, Long menuId, Long mainMenuId);
}
