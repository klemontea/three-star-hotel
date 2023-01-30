package com.miniproject.threestarhotel.repository;

import com.miniproject.threestarhotel.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepos extends JpaRepository<OtpEntity, String> {

    Optional<OtpEntity> findOtpByEmail(String email);
}
