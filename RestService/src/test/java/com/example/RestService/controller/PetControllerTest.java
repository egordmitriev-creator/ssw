package com.example.RestService.controller;

import com.example.RestService.exception.PetNotFoundException;
import com.example.RestService.exception.ValidationException;
import com.example.RestService.model.Pet;
import com.example.RestService.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void getAllPets() throws Exception {
        // Arrange
        Pet pet1 = new Pet();
        pet1.setId(1L);
        Pet pet2 = new Pet();
        pet2.setId(2L);
        List<Pet> pets = Arrays.asList(pet1, pet2);

        when(petService.getAllPets()).thenReturn(pets);

        // Act & Assert
        mockMvc.perform(get("/api/v3/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
        verify(petService, times(1)).getAllPets();
    }

    @Test
    void getPetById_Success() throws Exception {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);

        when(petService.getPetById(1L)).thenReturn(Optional.of(pet));

        // Act & Assert
        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(petService, times(1)).getPetById(1L);
    }

    @Test
    void getPetById_NotFound() throws Exception {
        // Arrange
        when(petService.getPetById(1L)).thenThrow(new PetNotFoundException("Pet not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isNotFound());
        verify(petService, times(1)).getPetById(1L);
    }

    @Test
    void addPet_Success() throws Exception {
        // Arrange
        Pet validPet = new Pet();
        validPet.setId(1L);
        validPet.setName("Buddy");

        when(petService.addPet(any())).thenReturn(validPet);

        // Act & Assert
        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Buddy"));

        verify(petService).addPet(any());
    }

    @Test
    void addPet_ValidationException() throws Exception {
        // Arrange
        Pet invalidPet = new Pet();
        invalidPet.setName(""); // Пустое имя

        // Мокируем выброс исключения
        when(petService.addPet(any())).thenThrow(new ValidationException("Validation exception"));

        // Act & Assert
        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPet)))
                .andExpect(status().isUnprocessableEntity()); // 422

        verify(petService).addPet(any());
    }

    @Test
    void deletePet_Success() throws Exception {
        // Arrange
        doNothing().when(petService).deletePet(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v3/pet/1"))
                .andExpect(status().isOk());
        verify(petService, times(1)).deletePet(1L);
    }

    @Test
    void deletePet_NotFound() throws Exception {
        // Arrange
        doThrow(new PetNotFoundException("Pet not found")).when(petService).deletePet(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v3/pet/1"))
                .andExpect(status().isNotFound());
        verify(petService, times(1)).deletePet(1L);
    }
}