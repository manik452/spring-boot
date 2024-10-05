package com.philips.basicdetails.auth.service;



import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {



    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        String username = (String) authenticationFailureBadCredentialsEvent.getAuthentication().getPrincipal();

/*SystemUserEntity user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Username Not Found");

        user.setNumberOfBadLogin(user.getNumberOfBadLogin()+1);
        if(user.getNumberOfBadLogin()>=3){
            user.setUserStatus(SystemUserStatus.AUTO_LOCKED.getType());
            user.setTempLockDate(new Date());
        }
        userRepository.save(user);*/


    }
}

