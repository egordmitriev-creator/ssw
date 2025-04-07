package com.example.OrderManagementSystem.model.entity;

import com.example.OrderManagementSystem.model.value.Weight;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Weight shippingWeight;

    private String description;
}