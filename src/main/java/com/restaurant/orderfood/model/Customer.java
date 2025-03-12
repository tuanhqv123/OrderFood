package com.restaurant.orderfood.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "points", nullable = false)
    private Integer points = 0;

    @Column(name = "tier", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerTier tier = CustomerTier.REGULAR;

    public enum CustomerTier {
        REGULAR, SILVER, GOLD
    }
}