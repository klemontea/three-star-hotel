package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.entity.JwtTokenBlacklist;
import com.miniproject.threestarhotel.repository.JwtTokenRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenBlacklistService {

    @Autowired
    JwtTokenRepos jwtTokenRepos;

    public boolean isBlacklist(String token) {
        return jwtTokenRepos.findByJwtToken(token) != null;
    }

    public void addJwtTokenBlacklist(String token) {
        jwtTokenRepos.save(new JwtTokenBlacklist(token));
    }
}
