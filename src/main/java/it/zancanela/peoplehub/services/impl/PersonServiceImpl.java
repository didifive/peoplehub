package it.zancanela.peoplehub.services.impl;

import it.zancanela.peoplehub.entities.Person;
import it.zancanela.peoplehub.exceptions.EntityNotFoundException;
import it.zancanela.peoplehub.repositories.PersonRepository;
import it.zancanela.peoplehub.services.PersonService;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Person> save(List<Person> people) {
        return repository.saveAll(people);
    }

    @Override
    public Page<Person> findAll(String name, Pageable pageable) {
        return repository.findAllByNameContains(name, pageable);
    }

    @Override
    public Person findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person with id [" +
                        id +
                        "] not found"));
    }

    @Override
    public Person update(String id, Person person) {
        Person existingPerson = this.findById(id);

        existingPerson.setName(person.getName());
        existingPerson.setBirthDate(person.getBirthDate());

        return save(existingPerson);
    }
}
