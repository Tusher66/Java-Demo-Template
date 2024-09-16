package com.greenish.web_portal.model;

import com.greenish.web_portal.constant.SequenceConstant;
import com.greenish.web_portal.model.common.BaseAuditEntity;
import com.greenish.web_portal.model.id_generator.UserMenuPermissionIdGenerator;
import com.greenish.web_portal.model.lookup.LPermission;
import com.greenish.web_portal.model.lookup.LUserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;


/**
 * The persistent class for the USER_MENU_PERMISSIONS database table.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "user_menu_permissions", uniqueConstraints = {@UniqueConstraint(name = "USER_MENU_PERMISSIONS_U_IDX",
        columnNames = {"menu_permission_id", "permission_id", "menu_id", "main_menu_id", "user_id", "user_type_id"})})
@NamedQuery(name = "UserMenuPermission.findAll", query = "SELECT u FROM UserMenuPermission u")
public class UserMenuPermission extends BaseAuditEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = SequenceConstant.USER_MENU_PERMISSIONS_SEQ)
    @GenericGenerator(name = SequenceConstant.USER_MENU_PERMISSIONS_SEQ,  type = UserMenuPermissionIdGenerator.class)
    @Column(name = "menu_permission_id", nullable = false)
    private Long menuPermissionId;

    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "main_menu_id", nullable = false)
    private Long mainMenuId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    //bi-directional many-to-one association to LPermission
    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false, insertable = false, updatable = false)
    private LPermission lPermission;

    //bi-directional many-to-one association to Menus
    @ManyToOne
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    private Menus menus;

    //bi-directional many-to-one association to UserProfile
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private UserProfile userProfile;

    @JsonProperty("active_yn")
    @Column(name = "active_yn", length = 1, nullable = false)
    private String activeYn;

}