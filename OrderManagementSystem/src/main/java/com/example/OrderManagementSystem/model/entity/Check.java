package com.example.OrderManagementSystem.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("CHECK")
public class Check extends Payment {
    private String name;
    private String bankID;
}