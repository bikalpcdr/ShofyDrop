package com.MSP.shopydrop.Security;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Loading user from database by username
        String query = "SELECT u FROM Users u WHERE u.email = :email";
        Optional<Users> userOptional = entityManager.createQuery(query, Users.class)
                .setParameter("email", username)
                .getResultList()
                .stream()
                .findFirst();

        Users user = userOptional.orElseThrow(
                () -> new ResourceNotFoundException("User", "name: " + username, 0L)
        );

        return (UserDetails) user;
    }
}
