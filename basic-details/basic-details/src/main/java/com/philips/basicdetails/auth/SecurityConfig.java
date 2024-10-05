package com.philips.basic_details.auth;


/*

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PhoneNumberAuthenticationProvider phoneNumberAuthenticationProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(phoneNumberAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(phoneNumberAuthenticationProvider)
                .authorizeRequests()
                .antMatchers("/login/phone").permitAll()
                .anyRequest().authenticated();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(phoneNumberAuthenticationProvider);
    }
    @Bean
    public PhoneNumberAuthenticationFilter phoneNumberAuthenticationFilter() throws Exception {
        PhoneNumberAuthenticationFilter filter = new PhoneNumberAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}*/
