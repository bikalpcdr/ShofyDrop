package com.MSP.shopydrop.Controller;

import com.MSP.shopydrop.Entity.Rewards;
import com.MSP.shopydrop.Service.RewardsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    private final RewardsService rewardsService;

    public RewardController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @PostMapping("/add")
    public ResponseEntity<Rewards> addRewards(@RequestBody Rewards rewards){
        return ResponseEntity.ok().body(this.rewardsService.addRewards(rewards));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Rewards> updateRewards(@RequestBody Rewards rewards, @PathVariable Long id){
        return ResponseEntity.ok().body(this.rewardsService.updateRewards(rewards, id));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Rewards> getRewardsById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.rewardsService.getRewardsById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Rewards>> getAllRewards(){
        return ResponseEntity.ok().body(this.rewardsService.getAllRewards());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteRewards(@PathVariable Long id){
        return ResponseEntity.ok().body(this.rewardsService.deleteRewards(id));
    }
}
