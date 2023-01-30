package com.miniproject.threestarhotel.repository;

import com.miniproject.threestarhotel.entity.JwtTokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepos extends JpaRepository<JwtTokenBlacklist, Long> {

    JwtTokenBlacklist findByJwtToken(String token);
}
