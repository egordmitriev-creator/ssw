package com.example.independetWork2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest // Поднимает весь контекст Spring
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockitoBean // Создает mock-объект и регистрирует его в Spring Context
    private PersonRepository personRepository;

    @Test
    public void testGetAllPersons() {
        // Подготовка данных
        Person person1 = new Person();
        person1.setId(1L);
        person1.setName("John Doe");
        person1.setAge(21l);

        Person person2 = new Person();
        person2.setId(2L);
        person2.setName("Jane Doe");
        person2.setAge(22L);

        List<Person> persons = Arrays.asList(person1, person2);

        // Мокируем вызов репозитория
        when(personRepository.findAll()).thenReturn(persons);

        // Вызываем метод сервиса
        List<Person> result = personService.getAllPersons();

        // Проверяем результат
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());

        // Проверяем, что метод репозитория был вызван
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testAddPerson() {
        // Подготовка данных
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setAge(22L);

        // Мокируем вызов репозитория
        when(personRepository.save(any(Person.class))).thenReturn(person);

        // Вызываем метод сервиса
        Person result = personService.addPerson(person);

        // Проверяем результат
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(22L, result.getAge());

        // Проверяем, что метод репозитория был вызван
        verify(personRepository, times(1)).save(any(Person.class));
    }
}