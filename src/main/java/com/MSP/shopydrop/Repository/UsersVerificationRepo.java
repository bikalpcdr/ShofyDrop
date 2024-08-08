package com.MSP.shopydrop.Repository;

import com.MSP.shopydrop.Entity.UsersVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UsersVerificationRepo extends JpaRepository<UsersVerification, Long> {
    Optional<UsersVerification>findByToken(String token);

    // void deleteByUserId(Long userId);
}
