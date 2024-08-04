package com.MSP.shopydrop.Repository.Implementation;

import com.MSP.shopydrop.Entity.Subscription;
import com.MSP.shopydrop.Enum.SubscriptionStatus;
import com.MSP.shopydrop.Repository.SubscriptionRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class SubscriptionRepoImpl implements SubscriptionRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Subscription add(Subscription subscription) {
        entityManager.persist(subscription);
        return subscription;
    }

    @Override
    public List<Subscription> findBySubscriptionStatus(SubscriptionStatus status) {
        return entityManager.createQuery(
                        "SELECT s FROM Subscription s WHERE s.subscriptionStatus = :status", Subscription.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public Subscription findBySubscriptionId(Long id) {
        return null;
    }

    @Override
    public List<Subscription> findSubscriptionsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Subscription> findAllSubscriptions() {
        return List.of();
    }
}
