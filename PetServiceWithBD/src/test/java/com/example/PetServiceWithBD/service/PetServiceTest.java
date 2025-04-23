package com.example.PetServiceWithBD.service;

import com.example.PetServiceWithBD.exception.InvalidIdException;
import com.example.PetServiceWithBD.exception.PetNotFoundException;
import com.example.PetServiceWithBD.model.Pet;
import com.example.PetServiceWithBD.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void addPet_ValidData_ReturnsPet() {
        // Arrange
        Pet pet = new Pet();
        pet.setName("Buddy");
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // Act
        Pet result = petService.addPet(pet);

        // Assert
        assertEquals("Buddy", result.getName());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void getPetById_ExistingId_ReturnsPet() {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // Act
        Optional<Pet> result = petService.getPetById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void deletePet_NonExistingId_ThrowsException() {
        // Arrange
        Long nonExistingId = 1L;
        when(petRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PetNotFoundException.class,
                () -> petService.deletePet(nonExistingId)
        );

        verify(petRepository, never()).deleteById(any());
    }

    @Test
    void deletePet_InvalidId_ThrowsInvalidIdException() {
        // Arrange
        Long invalidId = -1L;

        // Act & Assert
        assertThrows(InvalidIdException.class,
                () -> petService.deletePet(invalidId)
        );
    }
}