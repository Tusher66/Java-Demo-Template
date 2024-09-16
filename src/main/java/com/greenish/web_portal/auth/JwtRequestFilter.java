package com.greenish.web_portal.auth;


import com.greenish.web_portal.Utils.StringUtils;
import com.greenish.web_portal.auth.dto.JwtUserData;
import com.greenish.web_portal.auth.dto.SecurityUserDetails;
import com.greenish.web_portal.constant.ErrorStatusCode;
import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.exception.UnauthorizedUserException;
import com.greenish.web_portal.serviceImpl.UserProfileDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String APP = "APP";
    public static final String WEB = "WEB";
    public static final String STATUS_FALSE = "\"status\":false";

    @Autowired
    private UserProfileDetailsService userDetailsService;

    /*@Autowired
    private SettingsRepository settingsRepository;*/

    /*@Autowired
    private LogService logService;*/

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static final String[] INTERNAL_WHITE_LABEL_URLS = new String[]{

            WebApiUrlConstants.AUTH_LOGIN_API,
            /*WebApiUrlConstants.API_URI_PREFIX,
            WebApiUrlConstants.SAVE_USER,
            AppsApiUrlConstants.APPS_LOGIN_API,
            AppsApiUrlConstants.APPS_REGISTRATION_API,
            AppsApiUrlConstants.APPS_OTP_VERIFICATION_API,
            AppsApiUrlConstants.APPS_FORGET_PASSWORD_API,
            AppsApiUrlConstants.APPS_RESET_PASSWORD_API,
            AppsApiUrlConstants.RESEND_OPT_API,
            WebApiUrlConstants.PAYMENT_PROCESS,
            WebApiUrlConstants.PAYMENT_URL,
            WebApiUrlConstants.PAYMENT_PRE_LOAD,
            WebApiUrlConstants.PAYMENT_RECEIPT,*/
    };

    public static final String[] LOG_WHITE_LABEL_URLS = new String[]{
            /*WebApiUrlConstants.WORKFLOW_API_NOTIFICATIONS,
            WebApiUrlConstants.ACTIVITY_LOG_API,*/
    };

    public static final String[] LOGIN_URLS = new String[]{
            /*WebApiUrlConstants.AUTH_LOGIN_API,
            AppsApiUrlConstants.APPS_LOGIN_API,*/
    };

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = null;
        JwtUserData jwtUserData = null;
        if (requestTokenHeader == null) {
            requestTokenHeader = request.getHeader("token");
        }
        String requestURI = request.getRequestURI();
        boolean ignoreToken = StringUtils.containsWords(requestURI, INTERNAL_WHITE_LABEL_URLS);

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (!ignoreToken && requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                jwtUserData = jwtTokenUtil.getJwtUserDataFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                //   System.out.println("UNABLE TO GET JWT TOKEN");
                throw new UnauthorizedUserException("UNABLE TO GET JWT TOKEN", 99);
            } catch (ExpiredJwtException | SignatureException e) {
                //  System.out.println("JWT TOKEN HAS EXPIRED");
                request.setAttribute("CUSTOM-FILTER-ERROR", ErrorStatusCode.JWT_TOKEN_INVALID.getValue());
                throw new UnauthorizedUserException("JWT TOKEN HAS EXPIRED OR INVALID", 99);
            }
        }
//        else {
//            // logger.warn("JWT TOKEN DOES NOT BEGIN WITH 'Bearer '");
//            request.setAttribute("CUSTOM-FILTER-ERROR", ErrorStatusCode.NOT_AUTHORIZED.getValue());
//            throw new UnauthorizedUserException(ErrorStatusCode.NOT_AUTHORIZED.getValue(), ErrorStatusCode.NOT_AUTHORIZED.getCode());
//        }
        SecurityUserDetails userDetails = null;
        String userReqIp = request.getRemoteAddr();

        //Once we get the token validate it.
        if (jwtUserData != null && jwtUserData.getUserId() != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            userDetails = this.userDetailsService.loadUserByUsername(jwtUserData.getLoginId());
//            boolean validSingleUserToken = userProfileService.userIdTokenMatchedFound(jwtUserData.getUserId(), jwtToken);

            if (jwtTokenUtil.validateToken(jwtUserData, jwtToken, userDetails)) {
                // if token is valid configure Spring Security to manually set authentication
                if (userDetails.getActiveYn() != null && userDetails.getActiveYn().equals("Y")) {
                    userDetails.setTimeZone(jwtUserData.getTimeZone());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    request.setAttribute("CUSTOM-FILTER-ERROR", ErrorStatusCode.USER_NOT_ACTIVATED.getValue());
                    throw new UnauthorizedUserException(ErrorStatusCode.USER_NOT_ACTIVATED.getValue(), 99);
                }
            } else {
                throw new UnauthorizedUserException("INVALID JWT TOKEN", 99);
            }
        }
        // ------------ CHECK LAST YOURS ACTIVITY AND LOGOUT IF YOURS IDLE FOR 30MIN ---------------
        //        chain.doFilter(request, response);
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

//        long startTime = System.currentTimeMillis();
        chain.doFilter(requestWrapper, responseWrapper);
//        long timeTaken = System.currentTimeMillis() - startTime;

        buildAndSaveApiLogs(userDetails, request, response, requestWrapper, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void buildAndSaveApiLogs(SecurityUserDetails userDetails, HttpServletRequest request, HttpServletResponse response,
                                     ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) {
        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());
        String url = request.getRequestURL().toString();

        if (!StringUtils.containsWords(url, LOG_WHITE_LABEL_URLS)) {
            saveAccessReqResponseLog(request, requestBody, responseBody, url);
        }
    }


    private void saveAccessReqResponseLog(HttpServletRequest request, String requestBody, String responseBody, String url) {

        String accessFrom = request.getHeader("user-agent");
        String apiType = request.getRequestURL().toString().contains("/apps/") ? APP : WEB;
        String clientIp = request.getRemoteAddr();
        boolean isError = responseBody != null && responseBody.contains(STATUS_FALSE);

        /*List<Settings> settings = settingsRepository.findByActiveYn("Y");

        boolean saveWebLog = settings.stream().anyMatch(s -> s.getSettingCode().equals(SettingsCodeType.SAVE_WEB_API_ACCESS_LOG.name()))
                && apiType.equals(WEB);
        boolean saveAppsLog = settings.stream().anyMatch(s -> s.getSettingCode().equals(SettingsCodeType.SAVE_APPS_API_ACCESS_LOG.name()))
                && apiType.equals(APP);
        boolean saveErrorLog = settings.stream().anyMatch(s -> s.getSettingCode().equals(SettingsCodeType.SAVE_API_ERROR_LOG.name()))
                && isError;*/

        /*if (saveWebLog || saveAppsLog || saveErrorLog) {
            Map<String, List<String>> headersMap = Collections.list(request.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            h -> Collections.list(request.getHeaders(h))
                    ));

            String requestHeader = headersMap.toString();
            try {
                Gson gson = new Gson();
                requestHeader = gson.toJson(headersMap);
            } catch (Exception exception) {
                // LOGGER.info("#### Can't convert requestHeader to json object");
            }

            if (isError) {
                apiType = "ERR_" + apiType;
            }

            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader != null && authorizationHeader.length() > 7 ? authorizationHeader.substring(7) : "";

            ApiAccessLogData apiAccessLog = new ApiAccessLogData();
            apiAccessLog.setAccessFrom(accessFrom);
            apiAccessLog.setApiType(apiType);
            apiAccessLog.setClientIp(clientIp);
            apiAccessLog.setRequestBody(requestBody);
            apiAccessLog.setRequestHeader(requestHeader);
            apiAccessLog.setRequestMethod(request.getMethod());
            apiAccessLog.setResponseData(responseBody);
            apiAccessLog.setToken(token);
            apiAccessLog.setUri(url);
            apiAccessLog.setSuccessResponseYn(isError ? "N" : "Y");
            logService.saveApiAccessLog(apiAccessLog);
        }*/

        /*if (StringUtils.containsWords(url, LOGIN_URLS)) {
            LoginJsonData loginJsonData = null;
            try {
                Gson g = new Gson();
                loginJsonData = g.fromJson(requestBody, LoginJsonData.class);
            } catch (JsonSyntaxException ignored) {
                ;
            }

            LoginAccessLogData loginLogData = new LoginAccessLogData();
            loginLogData.setAccessFrom(accessFrom);
            loginLogData.setClientIp(clientIp);
            loginLogData.setResponseData(responseBody);
            loginLogData.setUri(url);
            loginLogData.setSuccessResponseYn(isError ? "N" : "Y");
            if (loginJsonData != null) {
                loginLogData.setUserName(loginJsonData.getUsername());
                loginLogData.setRequestIpAddress(loginJsonData.getIp_address());
                loginLogData.setTimeZone(loginJsonData.getTimeZone());
                loginLogData.setCurrentUtcDateTime(loginJsonData.getCurrentUtcDateTime());
                loginLogData.setEmailAddress(loginJsonData.getEmail());
                loginLogData.setLinkedAccountType(loginJsonData.getLinkedAccountType());
                loginLogData.setZoneDateTime(TimeZoneUtils.getLocalTimeZoneDateTime(loginJsonData.getTimeZone()));
            }

            logService.saveLoginAccessLog(loginLogData);
        }*/
    }

}
