package it.zancanela.peoplehub.services.impl;

import it.zancanela.peoplehub.repositories.AddressRepository;
import it.zancanela.peoplehub.services.AddressService;
import it.zancanela.peoplehub.services.AddressServiceTest;
import it.zancanela.peoplehub.services.PersonService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("Address Service Tests")
@ActiveProfiles("test")
class AddressServiceImplTest extends AddressServiceTest {

    @Inject
    private AddressServiceImpl service;

    @Inject
    private PersonServiceImpl personService;

    @Inject
    private AddressRepository repository;

    @BeforeEach
    @Transactional
    void tearDown() {
        repository.deleteAll();
    }

    @Override
    public AddressService service() {
        return service;
    }

    @Override
    public PersonService personService() {
        return personService;
    }
}