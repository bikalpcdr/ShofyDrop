package com.MSP.shopydrop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promocode_seq_gen")
    @SequenceGenerator(name = "promocode_seq_gen", sequenceName = "seq_gen", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(length = 50)
    private String code;

    private BigDecimal discount;

    private Date startDate;

    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "reward_id")
    private Rewards reward;

    @PrePersist
    protected void onCreate() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        createdAt = currentTime;
        updatedAt = currentTime;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
