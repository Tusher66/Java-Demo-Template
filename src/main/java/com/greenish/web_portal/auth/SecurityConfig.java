package com.greenish.web_portal.auth;


import com.greenish.web_portal.controller.common.WebApiUrlConstants;
import com.greenish.web_portal.exception.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Value("${cors.allowed-origin}")
    private String corsAllowedOrigin;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials, Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = "passwordEncoder")
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(corsAllowedOrigin);
        // configuration.setAllowedOrigins(List.of(corsAllowedOrigin));
        // configuration.addAllowedMethod("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT"));
        configuration.addAllowedHeader("*");
        // configuration.setAllowedHeaders(List.of("*"));
        // configuration.setAllowCredentials(true);
        // configuration.setMaxAge(Duration.ofSeconds(30));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
//            AppsApiUrlConstants.APPS_LOGIN_API + "/**",
//            WebApiUrlConstants.API_URI_PREFIX + "/**",
            WebApiUrlConstants.AUTH_LOGIN_API + "/**",
//            AppsApiUrlConstants.APPS_REGISTRATION_API + "/**",
//            AppsApiUrlConstants.APPS_OTP_VERIFICATION_API + "/**",
//            AppsApiUrlConstants.APPS_FORGET_PASSWORD_API + "/**",
//            APPS_CHAT + AppsApiUrlConstants.APPS_CHAT_PROFILE_IMG + "**",
//            AppsApiUrlConstants.APPS_RESET_PASSWORD_API + "/**",
//            AppsApiUrlConstants.RESEND_OPT_API + "/**",
//            WebApiUrlConstants.PAYMENT_PROCESS + "/**",
//            WebApiUrlConstants.PAYMENT_URL + "/**",
//            WebApiUrlConstants.PAYMENT_PRE_LOAD + "/**",
//            WebApiUrlConstants.PAYMENT_RECEIPT + "/**",
//            WebApiUrlConstants.VEHICLE_SERVICE_BOOKING_PAYMENT_API + "/**",
//            WebApiUrlConstants.USER_MEMBERSHIP_PAYMENT + "/**",
//            WebApiUrlConstants.PAYMENT_LOG + "/**",
//            WebApiUrlConstants.TEST_PAYMENT_PRE_LOAD + "/**",
//            WebApiUrlConstants.TEST_PAYMENT_PAY_LOAD + "/**",
//            WebApiUrlConstants.TEST_APPLE_PAY + "/**",
//            AppsApiUrlConstants.APP_TEMPLATE_DETAILS_BY_ID + "/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // spring
                // .cors(cors -> cors.disable())

                // vega
                .cors(Customizer.withDefaults()) // by default uses a Bean by the name of corsConfigurationSource

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .requestMatchers(WebApiUrlConstants.ROLE_API + "/**").hasRole("2001")
//                        .requestMatchers(WebApiUrlConstants.USER_TYPE_ROLE_API + "/**").hasRole("2002")
//                        .requestMatchers(WebApiUrlConstants.VEHICLE_CENTER_API + "/**").hasRole("2003")
//                        .requestMatchers(WebApiUrlConstants.TAX_FEE_RULE_API + "/**").hasRole("2004")
//                            .requestMatchers(WebApiUrlConstants.VEHICLE_SERVICE_API + "/**").hasRole("2005") // resolve
//                            .requestMatchers(WebApiUrlConstants.MEMBERSHIP_API + "/**").hasRole("2007") //resolve
//                        .requestMatchers(WebApiUrlConstants.OFFER_SAVE_UPDATE_API + "/**").hasRole("2008")
//                            .requestMatchers(WebApiUrlConstants.SLOT_INFO_API + "/**").hasRole("2010")
//                        .requestMatchers(WebApiUrlConstants.WORKFLOW_SETUP_API + "/**").hasRole("2019")
//                        .requestMatchers(WebApiUrlConstants.SCHEDULE_TASK_SETUP + "/**").hasRole("2020")
//                        .requestMatchers(WebApiUrlConstants.SETTINGS_SETUP + "/**").hasRole("2020")
//
//                            .requestMatchers(WebApiUrlConstants.VEHICLE_SERVICE_BOOKING_LIST_CENTER_WISE_API + "/**").hasRole("3001") //web
//                        .requestMatchers(WebApiUrlConstants.VEHICLE_SERVICE_BOOKING_CANCEL_API + "/**").hasRole("3001")
//                        .requestMatchers(WebApiUrlConstants.BOOKING_SEARCH_API + "/**").hasRole("3003")
//
//                        .requestMatchers(WebApiUrlConstants.PENDING_VEHICLE_SERVICE_BOOKING_LIST_API + "/**").hasRole("4001")
//                        .requestMatchers(WebApiUrlConstants.PENDING_VEHICLE_SERVICE_BOOKING_HISTORY_API + "/**").hasRole("4002")
//
//                        .requestMatchers(WebApiUrlConstants.USER_PROFILE_WEB_API + "/**").hasRole("5001")
//                        .requestMatchers(WebApiUrlConstants.SHIFT_SETUP_API + "/**").hasRole("5002")
//                        .requestMatchers(WebApiUrlConstants.SHIFT_MANAGEMENT_API + "/**").hasRole("5003")
//                        .requestMatchers(WebApiUrlConstants.CLONE_SLOT_INFO + "/**").hasRole("5004")
//
//
//                        .requestMatchers(WebApiUrlConstants.CLIENT_INFO_API + "/**").hasRole("6001")
//                            .requestMatchers(WebApiUrlConstants.VEHICLE_INFO_API + "/**").hasRole("6002")
//                        .requestMatchers(WebApiUrlConstants.USER_MEMBERSHIP_API + "/**").hasRole("6002")
//
//                        .requestMatchers(WebApiUrlConstants.API_PURCHASE + "/**").hasRole("7002")
//
//                        .requestMatchers(WebApiUrlConstants.REPORT_MISCELLANEOUS_PDF + "/**").hasRole("8005")
//
//                        .requestMatchers(WebApiUrlConstants.WORKFLOW_API_DASHBOARD + "/**").hasRole("9001")

                        .anyRequest().authenticated()
                )

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling((exHandling) -> exHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler())
                );

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
