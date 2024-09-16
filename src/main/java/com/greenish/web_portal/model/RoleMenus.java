package com.greenish.web_portal.model;

import com.greenish.web_portal.constant.SequenceConstant;
import com.greenish.web_portal.model.common.BaseAuditEntity;
import com.greenish.web_portal.model.id_generator.RoleMenusIdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;


/**
 * The persistent class for the ROLE_MENUS database table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "role_menus", uniqueConstraints = {@UniqueConstraint(name = "ROLE_MENUS_U_IDX",
        columnNames = {"role_menus_id", "role_id", "menu_id", "main_menu_id"})})
@NamedQuery(name = "RoleMenus.findAll", query = "SELECT r FROM RoleMenus r")
public class RoleMenus extends BaseAuditEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = SequenceConstant.COMMON_SEQUENCE_TABLE)
    @GenericGenerator(name = SequenceConstant.COMMON_SEQUENCE_TABLE, type = RoleMenusIdGenerator.class)
    @Column(name = "role_menus_id", nullable = false)
    private Long roleMenusId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "main_menu_id", nullable = false)
    private Long mainMenuId;

    //bi-directional many-to-one association to Menus
    @ManyToOne
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    private Menus menus;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @Column(name = "active_yn", length = 1, nullable = false)
    private String activeYn;

}