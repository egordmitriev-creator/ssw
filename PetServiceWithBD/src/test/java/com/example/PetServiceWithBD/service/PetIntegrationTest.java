package com.example.PetServiceWithBD.service;

import com.example.PetServiceWithBD.TestContainerConfiguration;
import com.example.PetServiceWithBD.exception.PetNotFoundException;
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

    @Test
    @Sql(scripts = "/test-data.sql")
    void shouldDeletePet() {
        Optional<Pet> petBeforeDelete = petService.getPetById(1L);
        assertTrue(petBeforeDelete.isPresent(), "Pet should exist before deletion");
        petService.deletePet(1L);
        assertThrows(PetNotFoundException.class, () -> petService.getPetById(1L),
                "Pet should not be found after deletion");
    }
}