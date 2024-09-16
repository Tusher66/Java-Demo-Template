package com.greenish.web_portal.data.menu;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenish.web_portal.data.response.RequestBaseData;
import com.greenish.web_portal.data.response.ResponseBaseStatusData;
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
public class MainMenuPermissionRespData extends ResponseBaseStatusData {
    /*main-menu/permission/2*/

    @JsonProperty("data")
    private List<MainMenuPermissionDetailsData> menus;

    @JsonProperty("request")
    private RequestBaseData request;

}
