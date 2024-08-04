package com.MSP.shopydrop.Repository.Implementation;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Repository.UsersRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class UsersRepoImpl implements UsersRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Users> findByEmail(String email) {
        List<Users> users = entityManager.createQuery(
                        "SELECT u FROM Users u WHERE u.email = :email", Users.class)
                .setParameter("email", email)
                .getResultList();
        return users.stream().findFirst();
    }

    @Override
    public Optional<Users> findById(Long id) {
        Users user = entityManager.find(Users.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<Users> findAll() {
        return entityManager.createQuery("SELECT u FROM Users u", Users.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Users save(Users user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Users user = entityManager.find(Users.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
