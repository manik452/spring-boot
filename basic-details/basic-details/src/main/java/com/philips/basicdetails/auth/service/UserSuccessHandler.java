package com.philips.basicdetails.auth.service;


import com.philips.basicdetails.application.model.UserLoginInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class UserSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    //    private static final Logger LOG = LoggerFactory.getLogger(SystemUserSuccessHandler.class);
    private final GrantedAuthority adminAuthority = new SimpleGrantedAuthority(
            "ROLE_ADMIN");
//    private final SystemUserRepository systemUserRepository;
//    private final MenuService menuService;
//    private final BankBranchService bankBranchService;
//    private final ActivityHistoryRepository activityHistoryRepository;


//    public SystemUserSuccessHandler(SystemUserRepository systemUserRepository, MenuService menuService, BankBranchService bankBranchService, ActivityHistoryRepository activityHistoryRepository) {
//        this.systemUserRepository = systemUserRepository;
//        this.menuService = menuService;
//        this.bankBranchService = bankBranchService;
//        this.activityHistoryRepository = activityHistoryRepository;
//
//    }



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (!isAdminAuthority(authentication)) {
            String userID =authentication.getPrincipal().toString();
//            SystemUserEntity systemUserEntity = systemUserRepository.findByUsername(userID);

            UserLoginInfo systemUserLoginInfo = new UserLoginInfo();
            systemUserLoginInfo.setUserName(userID);
            /*
             * Update Last Login Date
             * */


            String targetUrl = super.determineTargetUrl(request, response);
            if (StringUtils.isBlank(targetUrl) || StringUtils.equals(targetUrl, "/")) {
                targetUrl = "/home";
            }
            request.getSession().setAttribute("loginInfo", systemUserLoginInfo);
            clearAuthenticationAttributes(request);

            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, targetUrl);
//            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            // we invalidating the session for the admin user.
            invalidateSession(request, response);
        }
        clearAuthenticationAttributes(request);
    }

    protected void invalidateSession(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getSession().invalidate();
        redirectStrategy.sendRedirect(request, response, "/login");
    }

    protected boolean isAdminAuthority(final Authentication authentication) {
        return CollectionUtils.isNotEmpty(authentication.getAuthorities())
                && authentication.getAuthorities().contains(adminAuthority);
    }
}