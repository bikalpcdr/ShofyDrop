package com.MSP.shopydrop.Repository;

import com.MSP.shopydrop.Entity.Subscription;
import com.MSP.shopydrop.Enum.SubscriptionStatus;
import java.util.List;

public interface SubscriptionRepo{
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
