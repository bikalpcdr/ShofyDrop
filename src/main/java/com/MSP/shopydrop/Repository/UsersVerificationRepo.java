package com.MSP.shopydrop.Repository;

import com.MSP.shopydrop.Entity.UsersVerification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UsersVerificationRepo extends JpaRepository<UsersVerification, Long> {
    Optional<UsersVerification>findByToken(String token);

    @Query("SELECT uv FROM UsersVerification uv where uv.users.id = :userId ORDER BY uv.createdAt DESC")
    List<UsersVerification> findAllTokensByUser(@Param("userId") Long userId);

    void deleteByUserId(Long id);
}
