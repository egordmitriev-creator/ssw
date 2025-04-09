package com.example.OrderManagementSystem.model.value;

import jakarta.persistence.Embeddable;
import lombok.Data;

public interface Measurement {
    String getName();
    String getSymbol();
}
