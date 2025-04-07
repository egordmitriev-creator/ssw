package com.example.OrderManagementSystem.model.value;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Measurement {
    public String name;
    public String symbol;
}
