package com.greenish.web_portal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * The persistent class for the MAIN_MENUS database table.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "main_menus")
@NamedQuery(name = "MainMenus.findAll", query = "SELECT m FROM MainMenus m")
public class MainMenus implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_menu_id", nullable = false)
    private Long mainMenuId;

    @Column(name = "active_yn", nullable = false, length = 1)
    private String activeYn;

    @Column(name = "component_name", length = 100)
    private String componentName;

    @Column(name = "main_menu_name", nullable = false, length = 50)
    private String mainMenuName;

    @Column(name = "menu_icon", columnDefinition = "MEDIUMTEXT")
    private String menuIcon;

    @Column(name = "menu_url", length = 250)
    private String menuUrl;

    @Column(name = "sl_no")
    private Long slNo;

    @Column(name = "visible_yn", length = 1)
    private String visibleYn;


}