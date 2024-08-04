package com.MSP.shopydrop.Service.Implementation;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Enum.LoginType;
import com.MSP.shopydrop.Enum.UserType;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import com.MSP.shopydrop.Service.UsersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UsersService {

    private final EntityManager entityManager;

    public UserServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Users addUser(Users users) {
        entityManager.persist(users);
        return users;
    }

    @Override
    @Transactional
    public Users updateUser(Users users, Long id) {
        Users existingUser = entityManager.find(Users.class, id);

        if (existingUser == null) {
            throw new ResourceNotFoundException("User", "Id", id);
        }

        existingUser.setName(users.getName());
        existingUser.setEmail(users.getEmail());
        existingUser.setPassword(users.getPassword());
        existingUser.setUserType(users.getUserType());
        existingUser.setLoginType(users.getLoginType());

        return entityManager.merge(existingUser);
    }

    @Override
    public Users getUserById(Long id) {
        Users user = entityManager.find(Users.class, id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "Id", id);
        }
        return user;
    }

    @Override
    public Users getUserLoginType(LoginType loginType) {
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u WHERE u.loginType = :loginType", Users.class);
        query.setParameter("loginType", loginType);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Users getUserType(UserType userType) {
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u WHERE u.userType = :userType", Users.class);
        query.setParameter("userType", userType);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Users> getAllUsers() {
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u", Users.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        Users user = entityManager.find(Users.class, id);
        if (user != null) {
            entityManager.remove(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Users> getUsersByUserTypeSorted(UserType userType) {
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u WHERE u.userType = :userType ORDER BY u.name", Users.class);
        query.setParameter("userType", userType);
        return query.getResultList();
    }
}
