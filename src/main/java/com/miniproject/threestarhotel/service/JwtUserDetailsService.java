package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.entity.UserEntity;
import com.miniproject.threestarhotel.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepos userRepos;

    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {

        UserEntity userEntity = userRepos.findByEmail(email);

        if (userEntity != null) {
            return new User(userEntity.getEmail(),
                    userEntity.getEncryptPassword(), new ArrayList<>());

        } else {
            throw new UsernameNotFoundException(
                    "No username with such email exist!");
        }
    }
}
