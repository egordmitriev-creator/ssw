package com.example.PetServiceWithBD.controller;

import com.example.PetServiceWithBD.exception.InvalidIdException;
import com.example.PetServiceWithBD.exception.PetNotFoundException;
import com.example.PetServiceWithBD.exception.ValidationException;
import com.example.PetServiceWithBD.model.Pet;
import com.example.PetServiceWithBD.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v3/pet")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{petId}")
    public ResponseEntity<?> getPetById(@PathVariable Long petId) {
        try {
            Optional<Pet> pet = petService.getPetById(petId);
            return ResponseEntity.ok(pet);
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //400
        } catch (PetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); //404
        }
    }

    @PostMapping
    public ResponseEntity<?> addPet(@RequestBody Pet pet) {
        try {
            Pet newPet = petService.addPet(pet);
            return ResponseEntity.ok(newPet); // Возвращаем JSON-ответ
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage()); // 422
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePet(@RequestBody Pet pet) {
        try {
            Pet updatedPet = petService.updatePet(pet);
            return ResponseEntity.ok(updatedPet);
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //400
        } catch (PetNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); //404
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage()); //422
        }
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable Long petId) {
        try{
            petService.deletePet(petId);
        } catch (InvalidIdException e) {
            ResponseEntity.badRequest().body(e.getMessage()); //400
        }
    }
}