package it.zancanela.peoplehub.services;

import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.entities.Person;
import it.zancanela.peoplehub.enums.AddressType;
import it.zancanela.peoplehub.exceptions.DataIntegrityViolationException;
import it.zancanela.peoplehub.exceptions.EntityNotFoundException;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static it.zancanela.peoplehub.utils.Assertions.assertThrowsExceptionWithCorrectMessage;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AddressServiceTest {

    private Person person;
    private Address address;
    private List<Address> adresses;

    public abstract AddressService service();
    public abstract PersonService personService();

    private void assertAddress(Address expected, Address result) throws MultipleFailuresError {
        assertAll(
                "Assert that expected Address attributes has been returned"
                , () -> assertNotNull(result)
                , () -> assertEquals(expected.getPublicPlace(), result.getPublicPlace())
                , () -> assertEquals(expected.getNumber(), result.getNumber())
                , () -> assertEquals(expected.getCity(), result.getCity())
                , () -> assertEquals(expected.getState(), result.getState())
                , () -> assertEquals(expected.getZipCode(), result.getZipCode())
                , () -> assertEquals(expected.getAddressType(), result.getAddressType())
                , () -> assertEquals(expected.isMain(), result.isMain())
        );
    }

    @BeforeEach
    @Transactional
    void setUp() {
        person = new Person();
        person.setName("Luis Zancanela");
        person.setBirthDate(LocalDate.parse("1982-04-03"));

        person = personService().save(person);

        address = new Address();
        address.setPublicPlace("Rua 01");
        address.setNumber(100);
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01214-489");
        address.setAddressType(AddressType.HOME);

        adresses = new ArrayList<>(Collections.singletonList(address));
    }

    @Test
    @Transactional
    void addAdresses() {
        service().addAdresses(person.getId(), adresses);

        Person updatedPerson = personService().findById(person.getId());

        assertEquals(1, updatedPerson.getAdresses().size());
    }

    @Test
    @Transactional
    void addAddress() {
        service().addAddress(person.getId(), address);

        Person updatedPerson = personService().findById(person.getId());

        assertEquals(1, updatedPerson.getAdresses().size());
    }

    @Test
    @Transactional
    void findById() {
        service().addAddress(person.getId(), address);
        String id = person.getAdresses().getFirst().getId();

        var result = service().findById(id);

        assertAddress(address, result);
    }

    @Test
    void findAllByPerson() {
        service().addAddress(person.getId(), address);

        var result = service().findAllByPerson(person.getId());

        assertAddress(address, result.getFirst());
    }

    @Test
    @Transactional
    void update() {
        service().addAddress(person.getId(),address);
        String id = person.getAdresses().getFirst().getId();

        Address updateAddress = new Address();
        address.setPublicPlace("Rua 09");
        address.setNumber(5897);
        address.setCity("Niterói");
        address.setState("RJ");
        address.setZipCode("05784-489");
        address.setAddressType(AddressType.COMMERCIAL);

        var result = service().update(id, updateAddress);

        assertAddress(updateAddress, result);
    }

    @Test
    @Transactional
    void setMainAddress() {
        String personId = person.getId();
        service().addAddress(personId,address);
        String addressId = person.getAdresses().getFirst().getId();

        service().setMainAddress(personId, addressId);

        assertTrue(person.getAdresses().getFirst().isMain());
    }

    @Test
    @Transactional
    void setMainAddressThrowsExceptionWhenPersonHasNoAddress() {
        String personId = person.getId();
        String addressId = UUID.randomUUID().toString();

        assertThrowsExceptionWithCorrectMessage(
                () -> service().setMainAddress(personId, addressId),
                DataIntegrityViolationException.class,
                "Person with id [" +
                        personId +
                        "] does not have the address with id [" +
                        addressId +
                        "] linked"
        );
    }

    @Test
    @Transactional
    void setMainAddressThrowsExceptionWhenPersonHasNoProvidedAddress() {
        String personId = person.getId();
        service().addAddress(personId,address);
        String invalidAddressId = UUID.randomUUID().toString();

        assertThrowsExceptionWithCorrectMessage(
                () -> service().setMainAddress(personId, invalidAddressId),
                DataIntegrityViolationException.class,
                "Person with id [" +
                        personId +
                        "] does not have the address with id [" +
                        invalidAddressId +
                        "] linked"
        );
    }
}