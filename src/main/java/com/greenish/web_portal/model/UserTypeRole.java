package com.greenish.web_portal.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenish.web_portal.constant.SequenceConstant;
import com.greenish.web_portal.model.common.BaseAuditEntity;
import com.greenish.web_portal.model.id_generator.UserTypeRoleIdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;


/**
 * The persistent class for the USER_TYPE_ROLES database table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "user_type_roles", uniqueConstraints = {@UniqueConstraint(name = "USER_TYPE_ROLES_U_IDX",
        columnNames = {"user_type_roles_id", "user_type_id", "role_id"})})
@NamedQuery(name = "UserTypeRole.findAll", query = "SELECT u FROM UserTypeRole u")
public class UserTypeRole extends BaseAuditEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = SequenceConstant.COMMON_SEQUENCE_TABLE)
    @GenericGenerator(name = SequenceConstant.COMMON_SEQUENCE_TABLE, type = UserTypeRoleIdGenerator.class)
    @Column(name = "user_type_roles_id", nullable = false)
    @JsonProperty("user_type_roles_id")
    private Long userTypeRolesId;

    @Column(name = "user_type_id", nullable = false)
    @JsonProperty("user_type_id")
    private Long userTypeId;

    @Column(name = "role_id", nullable = false)
    @JsonProperty("role_id")
    private Long roleId;

    //bi-directional many-to-one association to LUserType
    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false, insertable = false, updatable = false)

    private com.greenish.web_portal.model.lookup.LUserType LUserType;

    //bi-directional many-to-one association to Role
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    @JsonProperty("role")
    private Role role;

    @JsonProperty("active_yn")
    @Column(name = "active_yn", length = 1, nullable = false)
    private String activeYn;

}