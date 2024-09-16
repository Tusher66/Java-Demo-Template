package com.greenish.web_portal.serviceImpl;

import com.greenish.web_portal.auth.AuthUtils;
import com.greenish.web_portal.auth.dto.SecurityUserDetails;
import com.greenish.web_portal.constant.DbConstant;
import com.greenish.web_portal.constant.ResponseMessageConstants;
import com.greenish.web_portal.data.role.RoleData;
import com.greenish.web_portal.data.user.UserTypeRoleData;
import com.greenish.web_portal.data.user.UserTypeRoleListDetailsData;
import com.greenish.web_portal.exception.ArgumentNotValidException;
import com.greenish.web_portal.exception.CrudException;
import com.greenish.web_portal.exception.ResourceNotFoundException;
import com.greenish.web_portal.model.Role;
import com.greenish.web_portal.model.UserTypeRole;
import com.greenish.web_portal.model.lookup.LUserType;
import com.greenish.web_portal.repository.LUserTypeRepository;
import com.greenish.web_portal.repository.RoleRepository;
import com.greenish.web_portal.repository.UserTypeRoleRepository;
import com.greenish.web_portal.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private AuthUtils authUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LUserTypeRepository userTypeRepository;

    @Autowired
    private UserTypeRoleRepository userTypeRoleRepository;

    @Override
    public RoleData saveRole(RoleData roleData) {

        SecurityUserDetails authUserData = authUtil.getSecurityUserDetails();

        if (authUserData == null || authUserData.getUserId() == null) {
            throw new ArgumentNotValidException("UPDATE BY CAN NOT BE EMPTY!");
        } else if (!authUserData.getUserTypeId().equals(DbConstant.SUPER_ADMIN)) {
            throw new CrudException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        // set ACTIVE_YN value
        if (roleData.getActiveYn() == null || roleData.getActiveYn().isEmpty()) {
            roleData.setActiveYn("N");
        }
        long nameCount;
        if (roleData.getRoleId() != null) {

            nameCount = roleRepository.countByRoleNameIgnoreCaseAndRoleIdNot(roleData.getRoleName(), roleData.getRoleId());
            if (nameCount > 0) {
                throw new ArgumentNotValidException(roleData.getRoleName() + " ALREADY EXIST!");
            }

            Role role = roleRepository.findByRoleId(roleData.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("ROLE NOT FOUND!"));

            role.setRoleId(roleData.getRoleId());
            role.setRoleName(roleData.getRoleName());
            role.setActiveYn(roleData.getActiveYn());

            roleRepository.save(role);

            roleData.setRoleId(role.getRoleId());
            return roleData;


        } else {

            nameCount = roleRepository.countByRoleNameIgnoreCase(roleData.getRoleName());
            if (nameCount > 0) {
                throw new ArgumentNotValidException(roleData.getRoleName() + " ALREADY EXIST!");
            }

            Role role = Role.builder()
                    .roleId(roleData.getRoleId())
                    .roleName(roleData.getRoleName().toUpperCase())
                    .activeYn(roleData.getActiveYn() != null && roleData.getActiveYn().equals("Y") ? "Y" : "N")
                    .showUiYn("Y")
                    .build();

            role = roleRepository.save(role);

            roleData.setRoleId(role.getRoleId());
            return roleData;

        }
    }

    @Override
    public List<UserTypeRoleListDetailsData> getAllUserTypeRole(String search) {

        List<LUserType> pageResult = userTypeRepository.getAllUserTypeRole(search);
        return pageResult.stream()
                .map(userType -> {
                    UserTypeRole userTypeRole = userTypeRoleRepository.findByUserTypeIdAndActiveYn(userType.getUserTypeId(), "Y")
                            .stream().findFirst().orElse(null);
                    return UserTypeRoleListDetailsData.builder()
                            .userTypeId(userType.getUserTypeId().toString())
                            .userTypeName(userType.getUserTypeName())
                            .activeYn(userType.getActiveYn())
                            .roleId(userTypeRole != null ? userTypeRole.getRoleId() : null)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserTypeRoleData saveUserTypeRole(UserTypeRoleData roleData) {

        SecurityUserDetails authUserData = authUtil.getSecurityUserDetails();

        if (authUserData == null || authUserData.getUserId() == null) {
            throw new ArgumentNotValidException("UPDATE BY CAN NOT BE EMPTY!");
        } else if (!authUserData.getUserTypeId().equals(DbConstant.SUPER_ADMIN)) {
            throw new CrudException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }

        UserTypeRole userTypeRoleDB = userTypeRoleRepository.findByUserTypeId(roleData.getUserTypeId());
        UserTypeRole userTypeRole = UserTypeRole.builder()
                .roleId(roleData.getRoleId())
                .userTypeId(roleData.getUserTypeId())
                .activeYn("Y")
                .insertBy(authUserData.getUserId())
                .insertDate(LocalDateTime.now())
                .build();
        if (userTypeRoleDB != null) {
            userTypeRole.setUserTypeRolesId(userTypeRoleDB.getUserTypeRolesId());
            userTypeRole.setRoleId(roleData.getRoleId());
            userTypeRole.setUpdateBy(authUserData.getUserId());
            userTypeRole.setUpdateDate(LocalDateTime.now());
        }

        userTypeRole = userTypeRoleRepository.save(userTypeRole);

        roleData.setOStatusCode(1);
        roleData.setOStatusMessage(userTypeRoleDB != null ? "RECORD UPDATED SUCCESSFULLY" : "RECORD SAVED SUCCESSFULLY");
        roleData.setRoleId(userTypeRole.getRoleId());
        return roleData;
    }

    @Override
    public Optional<Role> findByRoleId(Long roleId) {
        return roleRepository.findById(roleId);
    }
}
