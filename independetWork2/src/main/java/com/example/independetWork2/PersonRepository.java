package com.example.independetWork2;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    private final List<Person> persons = new ArrayList<>();

    public List<Person> findAll() {
        return persons;
    }

    public Person save(Person person) {
        persons.add(person);
        return person;
    }
}
