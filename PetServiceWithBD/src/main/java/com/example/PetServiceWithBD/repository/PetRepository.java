package com.example.PetServiceWithBD.repository;

import com.example.PetServiceWithBD.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}