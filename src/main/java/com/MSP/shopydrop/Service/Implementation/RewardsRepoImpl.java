package com.MSP.shopydrop.Service.Implementation;

import com.MSP.shopydrop.Entity.Rewards;
import com.MSP.shopydrop.Repository.DefaultProcedureRepo;
import com.MSP.shopydrop.Repository.RewardsRepo;

import java.util.List;
import java.util.Optional;

public class RewardsRepoImpl implements RewardsRepo {

    private final DefaultProcedureRepo defaultProcedureRepo;

    public RewardsRepoImpl(DefaultProcedureRepo defaultProcedureRepo) {
        this.defaultProcedureRepo = defaultProcedureRepo;
    }

    @Override
    public Rewards addRewards(Rewards rewards) {
        Object id[] = defaultProcedureRepo.executeWithType("authenticate.cfn_add_edit_users", new Object[][])
        return null;
    }

    @Override
    public Rewards updateRewards(Rewards rewards, Long id) {
        return null;
    }

    @Override
    public Optional<Rewards> getRewardsById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Rewards> getAllRewards() {
        return List.of();
    }

    @Override
    public void deleteRewards(Long id) {

    }
}
