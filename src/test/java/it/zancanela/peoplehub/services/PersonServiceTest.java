package it.zancanela.peoplehub.services;

import it.zancanela.peoplehub.entities.Person;
import it.zancanela.peoplehub.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.zancanela.peoplehub.utils.Assertions.assertThrowsExceptionWithCorrectMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public abstract class PersonServiceTest {

    private Person person;
    private List<Person> people;

    public abstract PersonService service();

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setName("Luis Zancanela");
        person.setBirthDate(LocalDate.parse("1982-04-03"));

        people = new ArrayList<>();
        people.add(person);
    }

    @Test
    void savePerson() {
        var result = service().save(person);

        assertEquals(person.getName(), result.getName());
        assertEquals(person.getBirthDate(), result.getBirthDate());
    }

    @Test
    void saveBatch() {
        var result = service().save(people);

        assertEquals(person.getName(), people.getFirst().getName());
        assertEquals(person.getBirthDate(), people.getFirst().getBirthDate());
    }

    @Test
    void findAll() {
        service().save(people);

        Pageable pageable = PageRequest.of(0, 10);

        var result = service().findAll(pageable);

        assertEquals(people.size(), result.getTotalElements());
    }

    @Test
    void findAllByName() {
        service().save(people);

        String name = people.getFirst().getName();
        Pageable pageable = PageRequest.of(0, 10);

        var result = service().findAll(name, pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findById() {
        String id = service().save(person).getId();

        var result = service().findById(id);

        assertEquals(person.getName(), result.getName());
        assertEquals(person.getBirthDate(), result.getBirthDate());
    }

    @Test
    void findByIdWithInvalidIdShoulThrowException() {
        service().save(people);

        String invalidId = UUID.randomUUID().toString();

        assertThrowsExceptionWithCorrectMessage(
                () -> service().findById(invalidId),
                EntityNotFoundException.class,
                "Person with id [" +
                        invalidId +
                        "] not found"
        );
    }

    @Test
    void update() {
        String id = service().save(person).getId();

        Person updatePerson = new Person();
        updatePerson.setName("João Neves");
        updatePerson.setBirthDate(LocalDate.parse("1969-09-03"));

        var result = service().update(id, updatePerson);

        assertEquals(id, result.getId());
        assertEquals(updatePerson.getName(), result.getName());
        assertEquals(updatePerson.getBirthDate(), result.getBirthDate());
    }
}