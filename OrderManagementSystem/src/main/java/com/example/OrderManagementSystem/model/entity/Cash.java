package com.example.OrderManagementSystem.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cash_payments")
public class Cash extends Payment {
    private float cashAmount;
}