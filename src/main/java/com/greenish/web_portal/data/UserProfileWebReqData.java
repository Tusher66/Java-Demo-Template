package com.greenish.web_portal.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileWebReqData {

    private Long userId;

//    private Long centerId;

    @NotNull(message = "USER TYPE CAN NOT BE EMPTY!")
    private Long userTypeId;

    @NotBlank(message = "USER FIRST NAME CAN NOT BE EMPTY!")
    private String firstName;

    private String lastName;

    @NotBlank(message = "EMAIL CAN NOT BE EMPTY!")
    private String email;

    private String mobileNo;

    private String dob;

    private String password;

//    private FileMetaData fileMetaData;
//
//    private FileMetaData attachFileMetaData;

    private String activeYn;

}
