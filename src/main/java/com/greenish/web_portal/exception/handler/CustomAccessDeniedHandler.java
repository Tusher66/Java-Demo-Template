package com.greenish.web_portal.exception.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenish.web_portal.exception.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        if (!response.isCommitted()) {
            ErrorDetails customErrorData = new ErrorDetails("Access Denied",
                    99);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            OutputStream responseStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(responseStream, customErrorData);
            responseStream.flush();
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
        }
    }
}