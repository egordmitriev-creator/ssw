package com.example.independentWork3.controller;

import com.example.independentWork3.model.University;
import com.example.independentWork3.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/universities")
public class UniversityController {
    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public List<University> getAllUniversities() { return universityService.getAllUniversities(); }

    @GetMapping("/{university_Id}")
    public ResponseEntity<University> getUniversityById(@PathVariable Long university_Id) {
        return ResponseEntity.of(universityService.getUniversityById(university_Id));
    }

    @PostMapping
    public University addUniversity(@RequestBody University university) {
        return universityService.addUniversity(university);
    }

    @PutMapping
    public University updateUni(@RequestBody University university) {
        return universityService.updateUniversity(university);
    }

    @DeleteMapping("/{university_Id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long university_Id) {
        universityService.deleteUniversity(university_Id);
        return ResponseEntity.noContent().build();
    }
}
