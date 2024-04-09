package it.zancanela.peoplehub.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private final Person person = new Person();
    private final Address address = new Address();
    private final Address address2 = new Address();

    @BeforeEach
    void setUp() {
        address.setMain(false);
        address.setPublicPlace("Rua 01");
        address2.setMain(false);
        address2.setPublicPlace("Rua 02");
        person.setAdresses(List.of(address, address2));
    }

    @Test
    void getMainAddress() {
        address2.setMain(true);

        var result = person.getMainAddress();

        assertTrue(result.isPresent());
        assertTrue(result.orElse(new Address()).isMain());
        assertEquals(address2.getPublicPlace(),
                result.orElse(new Address()).getPublicPlace());
    }

    @Test
    void getMainAddressEmpty() {
        var result = person.getMainAddress();

        assertTrue(result.isEmpty());
    }

    @Test
    void getMainAddressEvenIfHasMoreThanOneMain() {
        address.setMain(true);
        address2.setMain(true);

        var result = person.getMainAddress();

        assertTrue(result.isPresent());
        assertInstanceOf(Address.class,result.get());
    }

    @Test
    void getMainAddressEmptyWhenPersonNasNoAddress() {
        person.setAdresses(null);

        var result = person.getMainAddress();

        assertTrue(result.isEmpty());
    }
}