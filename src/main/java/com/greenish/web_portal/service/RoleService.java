package com.greenish.web_portal.service;

import com.greenish.web_portal.data.role.RoleData;
import com.greenish.web_portal.data.user.UserTypeRoleData;
import com.greenish.web_portal.data.user.UserTypeRoleListDetailsData;
import com.greenish.web_portal.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    RoleData saveRole(RoleData roleData);

    Optional<Role> findByRoleId(Long roleId);

    UserTypeRoleData saveUserTypeRole(UserTypeRoleData roleData);

    List<UserTypeRoleListDetailsData> getAllUserTypeRole(String search);
}
