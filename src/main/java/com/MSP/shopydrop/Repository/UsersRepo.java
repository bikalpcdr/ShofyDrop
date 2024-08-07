package com.MSP.shopydrop.Repository;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Enum.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);

    Optional<Users> findByEmail(String email);

    List<Users> findByUserType(UserType userType);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.userType = :userType")
    Integer countUsersByUserType(@Param("userType") UserType userType);
}
