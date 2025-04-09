package com.example.OrderManagementSystem.model.entity;

import com.example.OrderManagementSystem.model.value.Quantity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class OrderDetail {
    @Embedded
    private Quantity quantity;

    private String status;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}