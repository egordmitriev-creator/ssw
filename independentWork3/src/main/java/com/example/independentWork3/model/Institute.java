package com.example.independentWork3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "institutes")
public class Institute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
}
