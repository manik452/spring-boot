package com.philips.basicdetails.auth.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class LogoutHandlerListener implements LogoutHandler {
/*
    private final SystemUserRepository systemUserRepository;
    private final ActivityHistoryRepository activityHistoryRepository;

*/

    private boolean invalidateHttpSession = true;
    private boolean clearAuthentication = true;


    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.notNull(request, "HttpServletRequest required");
        if (invalidateHttpSession) {
            HttpSession session = request.getSession(false);
            if (session != null) {
//                logger.debug("Invalidating session: " + session.getId());
                session.invalidate();
            }
        }

        if (clearAuthentication) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(null);
        }


        String username = (String) authentication.getName();
       /* SystemUserEntity user = systemUserRepository.findByUsername(username);

        ActivityHistoryEntity activityHistoryEntity = new ActivityHistoryEntity(0,user.getId(), new Date(), ActivityEnum.LOGOUT.getCode(), ActivityEnum.LOGOUT.getStatusName(),"");
        activityHistoryRepository.save(activityHistoryEntity);*/

        SecurityContextHolder.clearContext();
    }
}
