package com.greenish.web_portal.model.lookup;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "l_user_types")
@NamedQuery(name = "LUserType.findAll", query = "SELECT l FROM LUserType l")
public class LUserType implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    @Column(name = "user_type_name", nullable = false, length = 30)
    private String userTypeName;

    @Column(name = "active_yn", nullable = false, length = 1)
    private String activeYn;

    @Column(name = "internal_user_yn", length = 1)
    private String internalUserYn;

}