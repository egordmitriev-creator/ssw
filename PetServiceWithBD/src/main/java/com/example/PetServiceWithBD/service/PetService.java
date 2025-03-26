package com.example.PetServiceWithBD.service;

import com.example.PetServiceWithBD.exception.InvalidIdException;
import com.example.PetServiceWithBD.exception.PetNotFoundException;
import com.example.PetServiceWithBD.exception.ValidationException;
import com.example.PetServiceWithBD.model.Pet;
import com.example.PetServiceWithBD.repository.PetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Optional<Pet> getPetById(Long id) {
        if (id == null || id < 0) {
            throw new InvalidIdException("Invalid ID supplied"); // 400
        }

        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new PetNotFoundException("Pet not found with id: " + id); // 404
        }
        return pet;
    }

    @Transactional
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Transactional
    public void deletePet(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdException("Invalid ID supplied"); // 400
        }
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new PetNotFoundException("Pet not found with id: " + id); // 404
        }
        petRepository.deleteById(id);
    }

    @Transactional
    public Pet updatePet(Pet pet) {
        if (pet.getId() == null || pet.getId() <= 0) {
            throw new InvalidIdException("Invalid ID supplied"); //400
        }
        if (!petRepository.findById(pet.getId()).isPresent()) {
            throw new PetNotFoundException("Pet not found with id: " + pet.getId()); //404
        }
        if (pet.getName() == null || pet.getName().isEmpty()) {
            throw new ValidationException("Validation exception: Pet name is required"); // 422
        }
        return petRepository.save(pet);
    }
}