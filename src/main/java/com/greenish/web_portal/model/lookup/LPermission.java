package com.greenish.web_portal.model.lookup;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "l_permission")
@NamedQuery(name = "LPermission.findAll", query = "SELECT l FROM LPermission l")
public class LPermission implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    @Column(name = "active_yn", nullable = false, length = 1)
    private String activeYn;

    @Column(name = "permission_key", length = 200)
    private String permissionKey;

    @Column(name = "permission_name", nullable = false, length = 100)
    private String permissionName;

}