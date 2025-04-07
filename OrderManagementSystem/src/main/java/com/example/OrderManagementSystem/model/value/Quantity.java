package com.example.OrderManagementSystem.model.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
@Embeddable
public class Quantity {
    private int value;
    @Embedded
    private Measurement measurement;
}
