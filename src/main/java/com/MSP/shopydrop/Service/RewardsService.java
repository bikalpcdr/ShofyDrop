package com.MSP.shopydrop.Service;

import com.MSP.shopydrop.Entity.Rewards;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RewardsService {

    Rewards addRewards(Rewards rewards);

    Rewards updateRewards(Rewards rewards, Long id);

    Rewards getRewardsById(Long id);

    List<Rewards> getAllRewards();

    boolean deleteRewards(Long id);
}
