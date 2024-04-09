package it.zancanela.peoplehub.services;

import it.zancanela.peoplehub.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PersonService {
    List<Person> save (List<Person> people);
    default Person save(Person person) {
        return this.save(List.of(person)).getFirst();
    }
    Page<Person> findAll(String name, Pageable pageable);
    default Page<Person> findAll(Pageable pageable) {
        return findAll("",pageable);
    }
    Person findById(String id);

    Person update(String id, Person person);

}
