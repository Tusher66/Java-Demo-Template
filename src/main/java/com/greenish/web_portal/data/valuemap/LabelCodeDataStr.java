package com.greenish.web_portal.data.valuemap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelCodeDataStr {
    @JsonProperty("label")
    private String label;

    @JsonProperty("code")
    private String code;

}