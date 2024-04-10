package it.zancanela.peoplehub.services.impl;

import it.zancanela.peoplehub.repositories.PersonRepository;
import it.zancanela.peoplehub.services.PersonService;
import it.zancanela.peoplehub.services.PersonServiceTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("Person Service Tests")
@ActiveProfiles("test")
class PersonServiceImplTest extends PersonServiceTest {

    @Inject
    private PersonServiceImpl service;

    @Inject
    private PersonRepository repository;

    @Override
    public PersonService service() {
        return service;
    }

    @BeforeEach
    @Transactional
    void tearDown() {
        repository.deleteAll();
    }
}