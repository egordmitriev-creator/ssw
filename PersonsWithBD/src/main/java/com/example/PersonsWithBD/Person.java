package com.example.PersonsWithBD;

import jakarta.persistence.*;

@Entity // Указывает, что это сущность JPA
@Table(name = "persons") // Указывает имя таблицы в базе данных
public class Person {
    @Id // Указывает, что это первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID
    private Long id;

    @Column(nullable = false) // Указывает, что поле не может быть null
    private String name;

    @Column(nullable = false) // Указывает, что поле не может быть null
    private Long age;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getAge() { return age; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(Long age) { this.age = age; }
}
