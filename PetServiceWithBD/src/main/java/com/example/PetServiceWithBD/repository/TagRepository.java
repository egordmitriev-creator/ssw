package com.example.PetServiceWithBD.repository;

import com.example.PetServiceWithBD.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
