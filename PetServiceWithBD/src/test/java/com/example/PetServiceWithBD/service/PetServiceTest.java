package com.example.PetServiceWithBD.service;

import com.example.PetServiceWithBD.exception.InvalidIdException;
import com.example.PetServiceWithBD.exception.PetNotFoundException;
import com.example.PetServiceWithBD.exception.ValidationException;
import com.example.PetServiceWithBD.model.Pet;
import com.example.PetServiceWithBD.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPets() {
        // Arrange
        Pet pet1 = new Pet();
        pet1.setId(1L);
        Pet pet2 = new Pet();
        pet2.setId(2L);
        List<Pet> pets = Arrays.asList(pet1, pet2);

        when(petRepository.findAll()).thenReturn(pets);

        // Act
        List<Pet> result = petService.getAllPets();

        // Assert
        assertEquals(2, result.size());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void getPetById_Success() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // Act
        Optional<Pet> result = petService.getPetById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void getPetById_InvalidId() {
        // Act & Assert
        assertThrows(InvalidIdException.class, () -> petService.getPetById(0L)); // 400
        verify(petRepository, never()).findById(any());
    }

    @Test
    void getPetById_NotFound() {
        // Arrange
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PetNotFoundException.class, () -> petService.getPetById(1L)); // 404
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void addPet_Success() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        when(petRepository.save(pet)).thenReturn(pet);

        // Act
        Pet result = petService.addPet(pet);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void addPet_ValidationException() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName(""); // Пустое имя

        // Act & Assert
        assertThrows(ValidationException.class, () -> petService.addPet(pet)); // 422
        verify(petRepository, never()).save(any());
    }

    @Test
    void updatePet_Success() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepository.update(pet)).thenReturn(pet);

        // Act
        Pet result = petService.updatePet(pet);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).update(pet);
    }

    @Test
    void updatePet_InvalidId() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(0L); // Неверный ID

        // Act & Assert
        assertThrows(InvalidIdException.class, () -> petService.updatePet(pet)); // 400
        verify(petRepository, never()).findById(any());
        verify(petRepository, never()).update(any());
    }

    @Test
    void updatePet_NotFound() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PetNotFoundException.class, () -> petService.updatePet(pet)); // 404
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, never()).update(any());
    }

    @Test
    void deletePet_Success() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).deleteById(1L);

        // Act
        petService.deletePet(1L);

        // Assert
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePet_InvalidId() {
        // Act & Assert
        assertThrows(InvalidIdException.class, () -> petService.deletePet(0L)); // 400
        verify(petRepository, never()).findById(any());
        verify(petRepository, never()).deleteById(any());
    }

    @Test
    void deletePet_NotFound() {
        // Arrange
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PetNotFoundException.class, () -> petService.deletePet(1L)); // 404
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, never()).deleteById(any());
    }
}