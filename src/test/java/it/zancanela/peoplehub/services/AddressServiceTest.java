package it.zancanela.peoplehub.services;

import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.entities.Person;
import it.zancanela.peoplehub.enums.AddressType;
import it.zancanela.peoplehub.exceptions.DataIntegrityViolationException;
import it.zancanela.peoplehub.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static it.zancanela.peoplehub.utils.Assertions.assertThrowsExceptionWithCorrectMessage;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AddressServiceTest {

    private Person person;
    private Address address;
    private Address address2;
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

        address2 = new Address();
        address2.setPublicPlace("Rua 09");
        address2.setNumber(5897);
        address2.setCity("Niterói");
        address2.setState("RJ");
        address2.setZipCode("25784-489");
        address2.setAddressType(AddressType.COMMERCIAL);

        adresses = new ArrayList<>(Arrays.asList(address, address2));
    }

    @Test
    @Transactional
    void addAddressWhenPersonHasNoAddress() {
        service().addAddress(person.getId(), address);

        Person updatedPerson = personService().findById(person.getId());

        assertEquals(1, updatedPerson.getAdresses().size());
    }

    @Test
    @Transactional
    void addAddressWhenPersonHasAddress() {
        service().addAddress(person.getId(), address);

        service().addAddress(person.getId(), address2);

        Person updatedPerson = personService().findById(person.getId());

        assertEquals(2, updatedPerson.getAdresses().size());
    }

    @Test
    @Transactional
    void addAdresses() {
        service().addAdresses(person.getId(), adresses);

        Person updatedPerson = personService().findById(person.getId());

        assertEquals(2, updatedPerson.getAdresses().size());
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
    @Transactional
    void findByIdWithInvalidIdShoulThrowException() {
        String invalidId = UUID.randomUUID().toString();

        assertThrowsExceptionWithCorrectMessage(
                () -> service().findById(invalidId),
                EntityNotFoundException.class,
                "Address with id [" +
                        invalidId +
                        "] not found"
        );

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
        service().addAddress(person.getId(), address);
        String id = person.getAdresses().getFirst().getId();

        Address updateAddress = new Address();
        updateAddress.setPublicPlace("Rua 09");
        updateAddress.setNumber(5897);
        updateAddress.setCity("Niterói");
        updateAddress.setState("RJ");
        updateAddress.setZipCode("05784-489");
        updateAddress.setAddressType(AddressType.COMMERCIAL);

        var result = service().update(id, updateAddress);

        assertAddress(updateAddress, result);
    }

    @Test
    @Transactional
    void setMainAddress() {
        String personId = person.getId();
        service().addAddress(personId, address);
        String addressId = person.getAdresses().getFirst().getId();

        service().setMainAddress(addressId);

        assertTrue(person.getAdresses().getFirst().isMain());
    }

    @Test
    @Transactional
    void setMainAddressWhenPersonHasTwoAddress() {
        String personId = person.getId();
        service().addAdresses(personId, adresses);
        String addressId = person.getAdresses().getFirst().getId();

        service().setMainAddress(addressId);

        assertEquals(2, person.getAdresses().size());
        assertTrue(person.getAdresses().getFirst().isMain());
        assertFalse(person.getAdresses().getLast().isMain());
    }

    @Test
    @Transactional
    void setMainAddressThrowsExceptionWhenAddressNoHasLinkedPerson() {
        String personId = person.getId();
        service().addAdresses(personId, adresses);
        Address addressWithoutPerson = person.getAdresses().getFirst();
        addressWithoutPerson.setPerson(null);


        assertThrowsExceptionWithCorrectMessage(
                () -> service().setMainAddress(addressWithoutPerson.getId()),
                DataIntegrityViolationException.class,
                "The address with id [" +
                        address.getId() +
                        "] does not have a linked person"
        );
    }
}