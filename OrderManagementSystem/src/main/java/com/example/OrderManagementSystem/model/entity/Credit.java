package com.example.OrderManagementSystem.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@DiscriminatorValue("CREDIT")
public class Credit extends Payment {
    private String number;
    private String type;
    private LocalDateTime expDate;
}