package com.MSP.shopydrop.Repository;

import com.MSP.shopydrop.Entity.Rewards;

import java.util.List;
import java.util.Optional;

public interface RewardsRepo {
    Rewards addRewards(Rewards rewards);

    Rewards updateRewards(Rewards rewards, Long id);

    Optional<Rewards> getRewardsById(Long id);

    List<Rewards> getAllRewards();

    void deleteRewards(Long id);
}
