package com.greenish.web_portal.model;

/*import com.cnsbd.carwash.constant.SequenceConstant;
import com.cnsbd.carwash.model.id_generator.UserProfileIdGenerator;
import com.cnsbd.carwash.model.lookup.LLinkedAccountType;
import com.cnsbd.carwash.model.lookup.LProvince;
import com.cnsbd.carwash.model.lookup.LUserType;*/

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenish.web_portal.constant.SequenceConstant;
import com.greenish.web_portal.model.id_generator.UserProfileIdGenerator;
import com.greenish.web_portal.model.lookup.LUserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import com.greenish.web_portal.model.lookup.LUserType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * The persistent class for the USER_PROFILE database table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profile",
        uniqueConstraints = {@UniqueConstraint(name = "user_login_id_idx", columnNames = {"login_id"}),
                @UniqueConstraint(name = "user_email_idx_2", columnNames = {"email"})})
public class UserProfile implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    /*@GeneratedValue(strategy = GenerationType.AUTO)*/
    @GeneratedValue(generator = SequenceConstant.USER_PROFILE_SEQ)
    @GenericGenerator(name = SequenceConstant.USER_PROFILE_SEQ, type = UserProfileIdGenerator.class)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_uuid")
    private String userUuid;

    @Column(name = "first_name", nullable = false, length = 150)
    private String firstName;

    @Column(name = "last_name", length = 150)
    private String lastName;

    @Column(name = "full_name", length = 254)
    private String fullName;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "mobile_no", length = 150)
    private String mobileNo;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "login_id", length = 150)
    private String loginId; // it's username

    @Lob
    @Column(name = "avatar", columnDefinition = "MEDIUMTEXT")
    private String avatar;

    @Column(name = "avatar_size")
    private Long avatarSize;

    @Column(name = "avatar_type", length = 50)
    private String avatarType;

    @Lob
    @Column(name = "attachment", columnDefinition = "MEDIUMTEXT")
    private String attachment;

    @Column(name = "attachment_size")
    private Long attachmentSize;

    @Column(name = "attachment_type", length = 50)
    private String attachmentType;

    @Column(name = "password", length = 254)
    private String password;

    @Column(name = "app_register_yn", length = 1)
    private String appRegisterYn;

    @Column(name = "registration_date", length = 150)
    private LocalDate registrationDate;

    @Column(name = "active_yn", nullable = false, length = 1)
    private String activeYn;

    @Column(name = "linked_user_yn", length = 1)
    private String linkedUserYn;

    @Column(name = "accept_terms_and_conditions_yn", length = 1)
    private String acceptTermsAndConditionsYn;

    @Column(name = "email_verify_yn", length = 1)
    private String emailVerifyYn;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private LUserType LUserType;

    /*@ManyToOne
    @JoinColumn(name = "linked_account_type_id")
    private LLinkedAccountType linkedAccountType;*/

//    @ManyToOne
//    @JoinColumn(name = "center_id")
//    private VehicleCenter vehicleCenter;

    @JsonProperty("insert_by")
    @Column(name = "insert_by")
    private Long insertBy;

    @JsonProperty("insert_date")
    @Column(name = "insert_date", nullable = false)
    private LocalDateTime insertDate;

    @JsonProperty("update_by")
    @Column(name = "update_by")
    private Long updateBy;

    @JsonProperty("update_date")
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "fcm_token", length = 300)
    private String fcmToken;

    /*@ManyToOne
    @JoinColumn(name = "province_id")
    private LProvince lProvince;*/
}