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
public class ValueTextData {
    @JsonProperty("value")
    private Long value;

    @JsonProperty("text")
    private String text;

}
