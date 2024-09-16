package com.greenish.web_portal.data.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenish.web_portal.data.response.ResponseBaseStatusData;
import com.greenish.web_portal.data.user.UserResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MenusData extends ResponseBaseStatusData {
    /*/access/menus*/

    @JsonProperty("user")
    private UserResponseData user;

    @JsonProperty("data")
    private List<MenusDetailsData> menus;

}
