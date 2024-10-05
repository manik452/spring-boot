package com.philips.basicdetails.auth.service;


import com.philips.basicdetails.application.model.AdminMenuEntity;
import com.philips.basicdetails.application.model.SystemUserEntity;
import com.philips.basicdetails.common.SystemUserStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsPasswordServiceImpl implements AuthenticationManager {

    @Autowired
    private Environment environment;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();
        SystemUserEntity systemUserEntity = new SystemUserEntity();// authentication.getName();
        if (systemUserEntity == null)
            throw new BadCredentialsException("User ID not found");

        List<AdminMenuEntity> adminMenuEntityList = new ArrayList<>();
        if (activeProfileDev() || activeProfileUat()) {
            this.setAuthority(adminMenuEntityList, AUTHORITIES);
            AUTHORITIES.add(new SimpleGrantedAuthority("authenticated::user"));
            AUTHORITIES.add(new SimpleGrantedAuthority("authenticated::USER"));
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
        }
        if (systemUserEntity.getUserStatus() == SystemUserStatus.ACTIVE.getType()) {
            try {
//                List<AdminMenuEntity> adminMenuEntityList = adminMenuRepository.findByRoleId(user.getUserRole());
                if (activeProfileDev() || activeProfileUat()) {
                    this.setAuthority(adminMenuEntityList, AUTHORITIES);
                    AUTHORITIES.add(new SimpleGrantedAuthority("authenticated::user"));
                    AUTHORITIES.add(new SimpleGrantedAuthority("authenticated::USER"));
                    return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
                }
                /*if (systemUserEntity.getUserStatus() == SystemUserStatus.AUTO_LOCKED.getType()) {
                    Date lockDate = systemUserEntity.getTempLockDate();
                } else if (DateTimeHelper.convertDateToDay(user.getLastLoginDate()) >= applicationCofigProperties.getAccountMaxIdleLoginDay()) {
                    user.setUserStatus(SystemUserStatus.CLOSED.getType());
                    userRepository.save(user);
                    throw new BadCredentialsException("Your Account Is Deactivate");
                }*/
                /*ldapConnection.connect(ldapConfigProperties.getDomainName(), ldapConfigProperties.getPort(), ldapConfigProperties.getRequestReadTimeoutMilliSec());
                ldapConnection.bind(authentication.getName() + ldapConfigProperties.getBindPart(), authentication.getCredentials().toString());
                SearchResult searchResult;
                if (ldapConnection.isConnected()) {
                    if (user.getAdLogin().equals(YesNoStringEnum.YES.getCode()) && user.getAdLoginDataUpdate().equals(YesNoStringEnum.NO.getCode())) {
                        searchResult = ldapConnection.search(new SearchRequest(ldapConfigProperties.getBaseDN(), SearchScope.SUB, Filter.createEqualityFilter("sAMAccountName", authentication.getName()), "*"));
                        if (searchResult != null && searchResult.getResultCode() != null) {
                            try {
                                user.setMobileNo(searchResult.getSearchEntries().get(0).getAttribute("telephonenumber").getValue());
                                user.setUserFullName(searchResult.getSearchEntries().get(0).getAttribute("name").getValue());
                                user.setEmailAddress(searchResult.getSearchEntries().get(0).getAttribute("mail").getValue());
                                user.setAdLoginDataUpdate(YesNoStringEnum.YES.getCode());
                                userRepository.saveAndFlush(user);
                            } catch (Exception e) {
                                remittanceDeclarationLogger.error(e);
                            }
                        }
                    }
                    this.setAuthority(adminMenuEntityList, AUTHORITIES);
                    AUTHORITIES.add(new SimpleGrantedAuthority("authenticated::user"));
                    AUTHORITIES.add(new SimpleGrantedAuthority("authenticated::USER"));
                    return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
                } else {
                    throw new BadCredentialsException("Credentials Did Not Match");
                }*/

            } catch (Exception e) {
                throw new BadCredentialsException("User ID not found");
            }
        } else {
            throw new BadCredentialsException("User not active");
        }
        return (Authentication) new BadCredentialsException("User not active");
    }

    private void setAuthority(List<AdminMenuEntity> adminMenuEntityList, List<GrantedAuthority> AUTHORITIES) {
        for (AdminMenuEntity menu : adminMenuEntityList) {
           /* String menuCode = menu.getSiteMenuLinkEntity().getMenuCode();

            if (menu.getCreatePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "create")));

            if (menu.getModifyPermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "modify")));

            if (menu.getDeletePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "delete")));

            if (menu.getAuthorizePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "authorize")));

            if (menu.getBrowsePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "browse")));*/
        }
    }

    private String getAuthority(String menuCode, String permission) {
        return String.format("%s::%s", menuCode, permission);
    }


    protected boolean activeProfileDev() {
        String[] activeProfile = this.environment.getActiveProfiles();
        if ("dev".equals(activeProfile[0])) {
            return true;
        }
        return false;
    }

    protected boolean activeProfileUat() {
        String[] activeProfile = this.environment.getActiveProfiles();
        if ("uat".equals(activeProfile[0])) {
            return true;
        }
        return false;
    }
}


