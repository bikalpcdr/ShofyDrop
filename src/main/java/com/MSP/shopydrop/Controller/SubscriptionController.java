package com.MSP.shopydrop.Controller;


import com.MSP.shopydrop.Entity.Subscription;
import com.MSP.shopydrop.Enum.SubscriptionStatus;
import com.MSP.shopydrop.Service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Subscription> addSubscription(@RequestBody Subscription subscription){
        Subscription createdSubscription = subscriptionService.add(subscription);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Subscription>> getSubscriptionByStatus(@PathVariable SubscriptionStatus status){
        List<Subscription> subscriptions = subscriptionService.findBySubscriptionStatus(status);
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id){
        Subscription subscription = subscriptionService.findBySubscriptionId(id);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getSubscriptionByUserId(@PathVariable Long userId){
        List<Subscription> subscriptions = subscriptionService.findSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Subscription>> getAllSubscription(){
        return ResponseEntity.ok().body(this.subscriptionService.findAllSubscriptions());
    }
}
