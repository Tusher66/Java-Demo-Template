package com.greenish.web_portal.data.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleData {

    @JsonProperty("roleId")
    private Long roleId;

    @JsonProperty("roleName")
    @NotBlank(message = "ROLE NAME CAN NOT BE EMPTY!")
    @Size(max = 100, message = "name text length should not be more then 50 character(s)")
    private String roleName;

    @JsonProperty("activeYn")
    private String activeYn; // Y

    @JsonProperty("updateBy")
    // @NotNull(message = "UPDATE BY CAN NOT BE EMPTY!")
    private Long updateBy;

}