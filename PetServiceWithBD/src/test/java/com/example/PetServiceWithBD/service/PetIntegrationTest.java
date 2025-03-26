package com.example.PetServiceWithBD.service;

import com.example.PetServiceWithBD.TestContainerConfiguration;
import com.example.PetServiceWithBD.model.Pet;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(TestContainerConfiguration.class)
class PetIntegrationTest {
    @Autowired
    private PetService petService;

    @Test
    void shouldSaveAndRetrievePet(){
        Pet pet = new Pet();
        pet.setName("Yasha");
        pet.setStatus("avaible");

        Pet savePet = petService.addPet(pet);
        Optional<Pet> foundPet = petService.getPetById(savePet.getId());

        assertTrue(foundPet.isPresent());
        assertEquals("Yasha", foundPet.get().getName());
    }
}