package com.MSP.shopydrop.Service;

import com.MSP.shopydrop.Entity.Subscription;
import com.MSP.shopydrop.Enum.SubscriptionStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {

    // Add a new subscription
    Subscription add(Subscription subscription);

    // Find a subscription by its status
    List<Subscription> findBySubscriptionStatus(SubscriptionStatus status);

    // Find a subscription by its ID
    Subscription findBySubscriptionId(Long id);

    // Find subscriptions by User ID
    List<Subscription> findSubscriptionsByUserId(Long userId);

    // Get all subscriptions
    List<Subscription> findAllSubscriptions();
}
