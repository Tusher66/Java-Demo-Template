package com.greenish.web_portal.data.response;


import com.greenish.web_portal.constant.ResponseMessageConstants;

public class ResponseSuccessData<T> extends ResponseBaseData<T> {

    public ResponseSuccessData(T data) {
        super(true, 1, ResponseMessageConstants.DATA_FOUND, data);
    }

    public ResponseSuccessData(String message, T data) {
        super(true, 1, message, data);
    }

}
