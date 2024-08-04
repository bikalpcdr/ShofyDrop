package com.MSP.shopydrop.Repository;

import com.MSP.shopydrop.Entity.Users;

import java.util.List;
import java.util.Optional;

public interface UsersRepo{
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(Long id);
    List<Users> findAll();
    Users save(Users user);
    void deleteById(Long id);
}
