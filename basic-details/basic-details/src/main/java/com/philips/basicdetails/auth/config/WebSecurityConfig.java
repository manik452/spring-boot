package com.philips.basicdetails.auth.config;



import com.philips.basicdetails.auth.service.LogoutHandlerListener;
import com.philips.basicdetails.auth.service.UserSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;


    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsPasswordServiceImpl") AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/resources/**"))
                .requestMatchers(new AntPathRequestMatcher("/otp-page/**"))
                .requestMatchers(new AntPathRequestMatcher("/registration/**"))
                .requestMatchers(new AntPathRequestMatcher("/css/**"))
                .requestMatchers(new AntPathRequestMatcher("/cssv2/**"))
                .requestMatchers(new AntPathRequestMatcher("/fonts/**"))
                .requestMatchers(new AntPathRequestMatcher("/img/**"))
                .requestMatchers(new AntPathRequestMatcher("/imgv2/**"))
                .requestMatchers(new AntPathRequestMatcher("/js/**"))
                .requestMatchers(new AntPathRequestMatcher("/jsv2/**"))
                .requestMatchers(new AntPathRequestMatcher("/pluginsv2/**"))
                .requestMatchers(new AntPathRequestMatcher("/logout"))
                .requestMatchers(new AntPathRequestMatcher("/error/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.ignoringRequestMatchers("/no-csrf"))
                .headers(headers -> headers
                                .cacheControl(HeadersConfigurer.CacheControlConfig::disable)
                                .httpStrictTransportSecurity(hstsConfig -> hstsConfig.includeSubDomains(true).maxAgeInSeconds(10))
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                                .xssProtection(xXssConfig -> xXssConfig.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
//                        .contentSecurityPolicy(contentSecurityPolicyConfig -> contentSecurityPolicyConfig.policyDirectives("script-src 'self'; object-src https://*; child-src 'none'; report-uri /csp-report-endpoint/").reportOnly())
                                .referrerPolicy(referrerPolicyConfig -> referrerPolicyConfig.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN))
                                .permissionsPolicy(permissionsPolicyConfig -> permissionsPolicyConfig.policy("geolocation=(self)"))

                )
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/**").permitAll()
                )
                .formLogin((login) -> login.loginPage("/login").permitAll().successHandler(successHandler()))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .addLogoutHandler(logoutSuccessHandler())

                                /*.logoutSuccessHandler(logoutSuccessHandler())
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .addLogoutHandler(logoutSuccessHandler)
//                        .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))*/


//                        .addLogoutHandler((request, response, authentication) -> )
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                                .expiredUrl("/login")
                )
                .build();
    }

    @Bean
    public UserSuccessHandler successHandler() {
        return new UserSuccessHandler();
    }

    @Bean
    public LogoutHandlerListener logoutSuccessHandler() {
        return new LogoutHandlerListener();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST"));
        return firewall;
    }
}
