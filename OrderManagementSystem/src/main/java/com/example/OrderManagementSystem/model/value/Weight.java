package com.example.OrderManagementSystem.model.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class Weight {
    private BigDecimal value;
    @Embedded
    private Measurement measurement;
}
