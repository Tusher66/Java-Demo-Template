package com.greenish.web_portal.repository;


import com.greenish.web_portal.model.UserMenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMenuPermissionRepository extends JpaRepository<UserMenuPermission, Long> {

    List<UserMenuPermission> findByUserIdAndActiveYn(Long userId, String active);

    UserMenuPermission findByPermissionIdAndUserIdAndMainMenuIdAndMenuId(Long permissionId, Long userId, Long mainMenuId, Long menuId);

}
