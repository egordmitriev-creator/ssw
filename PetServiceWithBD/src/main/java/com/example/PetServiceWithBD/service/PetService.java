package com.example.PetServiceWithBD.service;

import com.example.PetServiceWithBD.exception.InvalidIdException;
import com.example.PetServiceWithBD.exception.PetNotFoundException;
import com.example.PetServiceWithBD.exception.ValidationException;
import com.example.PetServiceWithBD.model.Pet;
import com.example.PetServiceWithBD.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Optional<Pet> getPetById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdException("Invalid ID supplied"); // 400
        }

        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new PetNotFoundException("Pet not found with id: " + id); // 404
        }

        return pet;
    }

    public Pet addPet(Pet pet) {
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new ValidationException("Validation exception: Pet name is required");
        }

        if (pet.getId() != null && pet.getId() <= 0) {
            throw new InvalidIdException("Invalid input: ID must be positive");
        }

        return petRepository.save(pet);
    }

    public Pet updatePet(Pet pet) {
        if (pet.getId() == null || pet.getId() <= 0) {
            throw new InvalidIdException("Invalid ID supplied"); // 400
        }

        Optional<Pet> existingPet = petRepository.findById(pet.getId());
        if (existingPet.isEmpty()) {
            throw new PetNotFoundException("Pet not found with id: " + pet.getId()); // 404
        }

        if (pet.getName() == null || pet.getName().isEmpty()) {
            throw new ValidationException("Validation exception: Pet name is required"); // 422
        }

        return petRepository.update(pet);
    }

    public void deletePet(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdException("Invalid pet value: ID must be positive"); // 400
        }

        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new PetNotFoundException("Pet not found with id: " + id); // 404
        }

        petRepository.deleteById(id);
    }
}