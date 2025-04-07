package com.example.OrderManagementSystem.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("CASH")
public class Cash extends Payment {
    private float cashTendered;
}