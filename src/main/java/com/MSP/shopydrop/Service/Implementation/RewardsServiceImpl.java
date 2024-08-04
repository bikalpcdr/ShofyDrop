package com.MSP.shopydrop.Service.Implementation;

import com.MSP.shopydrop.Entity.Rewards;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import com.MSP.shopydrop.Service.RewardsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RewardsServiceImpl implements RewardsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Rewards addRewards(Rewards rewards) {
        entityManager.persist(rewards);
        return rewards;
    }

    @Override
    @Transactional
    public Rewards updateRewards(Rewards rewards, Long id) {
        Rewards existingRewards = entityManager.find(Rewards.class, id);

        if (existingRewards == null) {
            throw new ResourceNotFoundException("Rewards", "Id", id);
        }

        existingRewards.setPoints(rewards.getPoints());

        return entityManager.merge(existingRewards);
    }

    @Override
    public Rewards getRewardsById(Long id) {
        Rewards rewards = entityManager.find(Rewards.class, id);
        if (rewards == null) {
            throw new ResourceNotFoundException("Rewards", "Id", id);
        }
        return rewards;
    }

    @Override
    public List<Rewards> getAllRewards() {
        TypedQuery<Rewards> query = entityManager.createQuery("SELECT r FROM Rewards r", Rewards.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean deleteRewards(Long id) {
        Rewards rewards = entityManager.find(Rewards.class, id);
        if (rewards != null) {
            entityManager.remove(rewards);
            return true;
        } else {
            return false;
        }
    }
}
