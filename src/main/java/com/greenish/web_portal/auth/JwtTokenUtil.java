package com.greenish.web_portal.auth;


import com.greenish.web_portal.auth.dto.JwtUserData;
import com.greenish.web_portal.auth.dto.SecurityUser;
import com.greenish.web_portal.exception.UnauthorizedUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final Long serialVersionUID = -2550185165626007488L;
    // public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;
    // public static final long JWT_TOKEN_VALIDITY = (365 * 24) * 60 * 60;
    public static final long JWT_TOKEN_SHORTLY = 24 * 60 * 60;
    public static final int TOKEN_EXPIRE_TIME_11PM = 23;
    public static final int TOKEN_EXPIRE_AFTER_10HR = 10;

    public static final String USER_ID = "userId";
    public static final String LOGIN_ID = "loginId";
    public static final String USER_TYPE_ID = "userTypeId";
    public static final String CLIENT_ID = "clientId";
    public static final String TIME_ZONE = "timeZone";
    public static final String EMAIL = "email";
    public static final String USER_UUID = "userUuid";

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public JwtUserData getJwtUserDataFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        JwtUserData jwtUserData;
        if (claims.get("userId") != null) {
            jwtUserData = new JwtUserData();
            jwtUserData.setUserId(claims.get(USER_ID) != null ? new Long(claims.get(USER_ID).toString()) : null);
            jwtUserData.setLoginId(claims.get(LOGIN_ID) != null ? claims.get(LOGIN_ID).toString().toUpperCase() : null);
            jwtUserData.setUserTypeId(claims.get(USER_TYPE_ID) != null ? new Long(claims.get(USER_TYPE_ID).toString()) : null);
            jwtUserData.setClientId(claims.get(CLIENT_ID) != null ? new Long(claims.get(CLIENT_ID).toString()) : null);
            jwtUserData.setUserUuid(claims.get(USER_UUID) != null ? claims.get(USER_UUID).toString() : null);
            jwtUserData.setEmail(claims.get(EMAIL) != null ? claims.get(EMAIL).toString() : null);
            jwtUserData.setTimeZone(claims.get(TIME_ZONE) != null ? claims.get(TIME_ZONE).toString() : null);
        } else {
            throw new UnauthorizedUserException("INVALID JWT TOKEN", 99);
        }
        return jwtUserData;
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    private Boolean ignoreTokenExpiration(String token) {
//        // here you specify tokens, for that the expiration is ignored
//        return false;
//    }

    public String generateToken(SecurityUser securityUser, String timeZone) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, securityUser.getUserId());
        claims.put(LOGIN_ID, securityUser.getLoginId().toUpperCase());
        claims.put(USER_TYPE_ID, securityUser.getUserTypeId());
        claims.put(USER_UUID, securityUser.getUserUuid());
        claims.put(EMAIL, securityUser.getEmail());
        claims.put(CLIENT_ID, securityUser.getClientId());
        claims.put(TIME_ZONE, timeZone);

        return doGenerateIssueAccessToken(claims, securityUser.getLoginId());
        /*return doGenerateToken(claims, securityUser.getLoginId());*/
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getTokenExpireDateAfter10hr()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Date getTokenExpireDate() {
        Date expireDate;

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (currentHour > 22) { // current date is after 10pm expire date will be next day 11:59PM
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_YEAR, 1); // Add 1 day to the current date

            calendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, now.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
        }

        calendar.set(Calendar.HOUR_OF_DAY, TOKEN_EXPIRE_TIME_11PM);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        expireDate = calendar.getTime();
        return expireDate;
    }

    public Date getTokenExpireDateAfter10hr() {
        Date expireDate = new Date();
        // Create a Calendar instance and set the current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expireDate);

        // Add 10 hours to the calendar
        calendar.add(Calendar.HOUR_OF_DAY, TOKEN_EXPIRE_AFTER_10HR);
        return calendar.getTime();
    }

    private String doGenerateIssueAccessToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_SHORTLY * 1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

//    public Boolean canTokenBeRefreshed(String token) {
//        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
//    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    public Boolean validateToken(JwtUserData jwtUserData, String token, SecurityUser userDetails) {
        return (jwtUserData != null && jwtUserData.getUserId() != null
                && jwtUserData.getUserId().equals(userDetails.getUserId())
                && !isTokenExpired(token));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
