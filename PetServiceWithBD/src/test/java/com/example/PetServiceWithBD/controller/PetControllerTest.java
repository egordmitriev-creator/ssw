package com.example.PetServiceWithBD.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.PetServiceWithBD.exception.InvalidIdException;
import com.example.PetServiceWithBD.exception.PetNotFoundException;
import com.example.PetServiceWithBD.model.Pet;
import com.example.PetServiceWithBD.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.NoSuchElementException;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(petController)
                .build();
    }

    @Test
    void getPetById_ExistingId_ReturnsPet() throws Exception {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        when(petService.getPetById(1L)).thenReturn(Optional.of(pet));

        // Act & Assert
        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void addPet_InvalidData_ReturnsBadRequest() throws Exception {
        when(petService.addPet(any(Pet.class)))
                .thenThrow(new InvalidIdException("Invalid ID supplied"));

        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": -1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid ID supplied"));
    }
}