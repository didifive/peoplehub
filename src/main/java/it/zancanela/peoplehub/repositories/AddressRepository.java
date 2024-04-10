package it.zancanela.peoplehub.repositories;

import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findAllByPerson(Person person);
}
