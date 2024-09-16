package com.greenish.web_portal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


/**
 * The persistent class for the "ROLES" database table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles",
        uniqueConstraints = {@UniqueConstraint(name = "role_name_udx", columnNames = {"role_name"})})
@Builder
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false)
    @JsonProperty("roleId")
    private Long roleId;

    @Column(name = "active_yn", nullable = false, length = 1)
    @JsonProperty("activeYn")
    private String activeYn;

    @Column(name = "role_name", nullable = false, length = 50)
    @JsonProperty("roleName")
    private String roleName;

    @Column(name = "show_ui_yn", length = 1)
    @JsonProperty("showUiYn")
    private String showUiYn;
}