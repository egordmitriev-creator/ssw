package com.example.independentWork3.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "universities")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "university_institutes",
            joinColumns = @JoinColumn(name = "university_id"),
            inverseJoinColumns = @JoinColumn(name = "institute_id")
    )
    private List<Institute> institutes;

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setInstitutes(List<Institute> institutes) { this.institutes = institutes; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public List<Institute> getInstitutes() { return institutes; }
}
