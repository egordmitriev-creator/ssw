package com.example.independentWork3.service;

import com.example.independentWork3.model.Institute;
import com.example.independentWork3.model.University;
import com.example.independentWork3.repository.InstituteRepository;
import com.example.independentWork3.repository.UniversityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;
    private final InstituteRepository instituteRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository, InstituteRepository instituteRepository) {
        this.universityRepository = universityRepository;
        this.instituteRepository = instituteRepository;
    }


    public List<University> getAllUniversities() { return universityRepository.findAll(); }

    public Optional<University> getUniversityById(Long id) {
        if (id == null || id < 0) {
            throw new InvalidIdException("Invalid ID supplied");
        }

        Optional<University> person = universityRepository.findById(id);
        if (person.isEmpty()) {
            throw new UniversityNotFoundException("University not found with id: " + id);
        }
        return person;
    }

    @Transactional
    public University addUniversity(University university) {
        if (university.getName() == null || university.getName().isEmpty()) {
            throw new ValidationException("Validation exception: university name is required");
        }

        if(university.getInstitutes() != null) {

            university.getInstitutes().forEach(institute -> {
                if(institute.getId() != null) {

                    Institute existingInstitute = instituteRepository.findById(institute.getId())
                            .orElseThrow(() -> new ValidationException("institute not found with id: " + institute.getId()));

                    existingInstitute.setName(institute.getName());
                    existingInstitute.setPhone(institute.getPhone());
                    instituteRepository.save(existingInstitute);
                } else {

                    instituteRepository.save(institute);
                }
            });
        }

        return universityRepository.save(university);
    }

    @Transactional
    public void deleteUniversity(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdException("Invalid ID supplied");
        }

        if (!universityRepository.existsById(id)) {
            throw new UniversityNotFoundException("University not found with id: " + id);
        }
        universityRepository.deleteById(id);
    }

    @Transactional
    public University updateUniversity(University university) {
        if (university.getId() == null || university.getId() <= 0) {
            throw new InvalidIdException("Invalid ID supplied");
        }

        if (!universityRepository.existsById(university.getId())) {
            throw new UniversityNotFoundException("university not found with id: " + university.getId());
        }

        if (university.getName() == null || university.getName().isEmpty()) {
            throw new ValidationException("Validation exception: university name is required");
        }

        return universityRepository.save(university);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class UniversityNotFoundException extends RuntimeException {
        public UniversityNotFoundException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class InvalidIdException extends RuntimeException {
        public InvalidIdException(String message) {
            super(message);
        }
    }
}
