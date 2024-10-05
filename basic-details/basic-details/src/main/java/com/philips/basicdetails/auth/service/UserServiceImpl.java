package com.philips.basicdetails.auth.service;



import com.philips.basicdetails.application.model.SystemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl( BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(SystemUserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles(new ArrayList<>(systemUserRoleRepository.findAll()));
//        userRepository.save(user);
    }

    @Override
    public SystemUserEntity findByUsername(String username) {
//        return userRepository.findByUsername(username);
        return new SystemUserEntity();
    }
}
