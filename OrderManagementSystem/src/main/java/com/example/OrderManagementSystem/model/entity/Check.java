package com.example.OrderManagementSystem.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "check_payments")
public class Check extends Payment {
    private String name;
    private String bankID;
}