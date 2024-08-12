package com.MSP.shopydrop.Repository;

import com.MSP.shopydrop.Entity.Users;
import java.util.List;
import java.util.Optional;

public interface UsersRepo {
    Optional<Users> findById(Long id);

    Optional<Users> findByEmail(String email);

    List<Users> getAllUsers();

    String saveUser(Users users);

    void deleteUsers(Long id);
}
