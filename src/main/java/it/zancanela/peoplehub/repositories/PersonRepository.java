package it.zancanela.peoplehub.repositories;

import it.zancanela.peoplehub.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
    Page<Person> findAll(Pageable pageable);
    Page<Person> findAllByNameContains(String nome, Pageable pageable);
}
