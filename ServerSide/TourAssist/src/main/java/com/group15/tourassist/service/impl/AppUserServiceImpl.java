package com.group15.tourassist.service.impl;

import com.group15.tourassist.core.entity.AppUser;
import com.group15.tourassist.repository.IAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl {

    @Autowired
    private IAppUserRepository appUserRepository;

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }
}
