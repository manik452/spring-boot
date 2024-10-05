package com.philips.basicdetails.auth.service;

import com.philips.basicdetails.application.model.SystemUserEntity;

public interface UserService {
    void save(SystemUserEntity user);

    SystemUserEntity findByUsername(String username);
}
