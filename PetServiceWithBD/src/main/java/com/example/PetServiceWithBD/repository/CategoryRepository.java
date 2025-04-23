package com.example.PetServiceWithBD.repository;

import com.example.PetServiceWithBD.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
