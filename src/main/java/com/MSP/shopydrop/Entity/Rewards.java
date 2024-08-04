package com.MSP.shopydrop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rewards_tb")
public class Rewards {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rewards_seq_gen")
    @SequenceGenerator(name = "rewards_seq_gen", sequenceName = "seq_gen", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long points;

    @Column(nullable = false, updatable = false,insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;
}
