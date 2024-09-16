package com.greenish.web_portal.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenish.web_portal.constant.ErrorStatusCode;
import com.greenish.web_portal.data.response.CustomErrorData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final Long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        Object filterError = request.getAttribute("CUSTOM-FILTER-ERROR");
        final String errorMsg = filterError != null ? (String) filterError : null;

        if (errorMsg != null && !response.isCommitted()) {
            CustomErrorData customErrorData = null;
            if (errorMsg.contains(ErrorStatusCode.JWT_TOKEN_EXPIRED.getValue())) {
                customErrorData = new CustomErrorData(ErrorStatusCode.JWT_TOKEN_EXPIRED.getValue(),
                        ErrorStatusCode.JWT_TOKEN_EXPIRED.getCode());
            } else if (errorMsg.contains(ErrorStatusCode.USER_IP_INVALID.getValue())) {
                Object msg = request.getAttribute("CUSTOM-FILTER-ERROR-MSG");
                final String ipMsg = msg != null ? (String) msg : ErrorStatusCode.USER_IP_INVALID.getValue();
                customErrorData = new CustomErrorData(ipMsg,
                        ErrorStatusCode.USER_IP_INVALID.getCode());
            } else if (errorMsg.contains(ErrorStatusCode.USER_NOT_ACTIVE.getValue())) {
                customErrorData = new CustomErrorData(ErrorStatusCode.USER_NOT_ACTIVE.getValue(),
                        ErrorStatusCode.USER_NOT_ACTIVE.getCode());
            } else if (errorMsg.contains(ErrorStatusCode.NOT_AUTHORIZED_MULTIPLE_LOGIN.getValue())) {
                customErrorData = new CustomErrorData(ErrorStatusCode.NOT_AUTHORIZED_MULTIPLE_LOGIN.getValue(),
                        ErrorStatusCode.NOT_AUTHORIZED_MULTIPLE_LOGIN.getCode());
            } else if (errorMsg.contains(ErrorStatusCode.NOT_AUTHORIZED.getValue())) {
                customErrorData = new CustomErrorData(ErrorStatusCode.NOT_AUTHORIZED.getValue(),
                        ErrorStatusCode.NOT_AUTHORIZED.getCode());
            } else if (errorMsg.contains(ErrorStatusCode.JWT_TOKEN_INVALID.getValue())) {
                customErrorData = new CustomErrorData(ErrorStatusCode.JWT_TOKEN_INVALID.getValue(),
                        ErrorStatusCode.JWT_TOKEN_INVALID.getCode());
            } else {
                customErrorData = new CustomErrorData(ErrorStatusCode.COMMON_SERVER_ERROR.getValue(),
                        ErrorStatusCode.COMMON_SERVER_ERROR.getCode());
            }

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            OutputStream responseStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(responseStream, customErrorData);
            responseStream.flush();
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }
}

