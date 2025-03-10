package com.example.PetServiceWithBD.repository;

import com.example.PetServiceWithBD.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PetRepository {
    private final List<Pet> pets = new ArrayList<>();

    public List<Pet> findAll() {
        return pets;
    }

    public Optional<Pet> findById(Long id) {
        return pets.stream().filter(pet -> pet.getId().equals(id)).findFirst();
    }

    public Pet save(Pet pet) {
        pets.add(pet);
        return pet;
    }

    public Pet update(Pet pet) {
        Optional<Pet> existingPet = pets.stream().filter(p -> p.getId().equals(pet.getId())).findFirst();

        if (existingPet.isPresent()) {
            Pet oldPet = existingPet.get();
            oldPet.setName(pet.getName());
            oldPet.setCategory(pet.getCategory());
            oldPet.setTags(pet.getTags());
            oldPet.setStatus(pet.getStatus());
            return oldPet;
        } else {
            throw new RuntimeException("Pet not found");
        }
    }

    public void deleteById(Long id) {
        pets.removeIf(pet -> pet.getId().equals(id));
    }
}