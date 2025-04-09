package com.example.OrderManagementSystem.model.value;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quantity implements Measurement {
    private Integer value;
    private String name;
    private String symbol;
}