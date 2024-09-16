package com.greenish.web_portal.model;


import com.greenish.web_portal.constant.SequenceConstant;
import com.greenish.web_portal.model.id_generator.MenusIdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;


/**
 * The persistent class for the MENUS database table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(name = "MENUS_U_IDX", columnNames = {"menu_id", "main_menu_id"}),
        @UniqueConstraint(name = "MENUS_U_IDX_2", columnNames = {"main_menu_id", "sl_no"})})
@NamedQuery(name = "Menus.findAll", query = "SELECT m FROM Menus m")
public class Menus implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = SequenceConstant.COMMON_SEQUENCE_TABLE)
    @GenericGenerator(name = SequenceConstant.COMMON_SEQUENCE_TABLE, type = MenusIdGenerator.class)
    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "main_menu_id", nullable = false)
    private Long mainMenuId;

    @Column(name = "active_yn", nullable = false, length = 1)
    private String activeYn;

    @Column(name = "component_name", length = 200)
    private String componentName;

    @Column(name = "menu_icon", columnDefinition = "MEDIUMTEXT")
    private String menuIcon;

    @Column(name = "menu_name", nullable = false, length = 100)
    private String menuName;

    @Column(name = "menu_url", nullable = false, length = 500)
    private String menuUrl;

    @Column(name = "part_menu_yn", length = 1)
    private String partMenuYn;

    @Column(name = "permission_wise_yn", nullable = false, length = 1)
    private String permissionWiseYn;

    @Column(name = "sl_no", nullable = false)
    private Long slNo;

    @Column(name = "visible_yn", length = 1)
    private String visibleYn;

    //bi-directional many-to-one association to MainMenus
    @ManyToOne 
    @JoinColumn(name = "main_menu_id", nullable = false , insertable = false, updatable = false )
    private MainMenus mainMenus;

    @Column(name = "menu_api_url", nullable = false, length = 500)
    private String menuApiUrl;

}