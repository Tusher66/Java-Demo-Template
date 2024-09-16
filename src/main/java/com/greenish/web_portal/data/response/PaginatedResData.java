package com.greenish.web_portal.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResData<T> extends ResponseBaseStatusData {
    @JsonProperty("data")
    private PaginationBaseData<T> data;

    @JsonProperty("request")
    private RequestBaseData request;

}
