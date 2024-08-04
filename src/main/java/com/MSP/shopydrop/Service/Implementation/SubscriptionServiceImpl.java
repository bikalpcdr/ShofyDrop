package com.MSP.shopydrop.Service.Implementation;

import com.MSP.shopydrop.Entity.Subscription;
import com.MSP.shopydrop.Enum.SubscriptionStatus;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import com.MSP.shopydrop.Service.SubscriptionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Subscription add(Subscription subscription) {
        entityManager.persist(subscription);
        return subscription;
    }

    @Override
    public List<Subscription> findBySubscriptionStatus(SubscriptionStatus status) {
        TypedQuery<Subscription> query = entityManager.createQuery(
                "SELECT s FROM Subscription s WHERE s.subscriptionStatus = :status", Subscription.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public Subscription findBySubscriptionId(Long id) {
        Subscription subscription = entityManager.find(Subscription.class, id);
        if (subscription == null) {
            throw new ResourceNotFoundException("Subscription", "id", id);
        }
        return subscription;
    }

    @Override
    public List<Subscription> findSubscriptionsByUserId(Long userId) {
        TypedQuery<Subscription> query = entityManager.createQuery(
                "SELECT s FROM Subscription s WHERE s.user.id = :userId", Subscription.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Subscription> findAllSubscriptions() {
        TypedQuery<Subscription> query = entityManager.createQuery("SELECT s FROM Subscription s", Subscription.class);
        return query.getResultList();
    }
}
