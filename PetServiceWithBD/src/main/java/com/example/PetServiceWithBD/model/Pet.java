package com.example.PetServiceWithBD.model;

import java.util.List;

public class Pet {
    private Long id;
    private String name;
    private Category category;
    private List<Tag> tags;
    private String status;

    public Long getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public List<Tag> getTags() { return tags; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(Category category) { this.category = category; }
    public void setTags(List<Tag> tags) { this.tags = tags; }
    public void setStatus(String status) { this.status = status; }
}
