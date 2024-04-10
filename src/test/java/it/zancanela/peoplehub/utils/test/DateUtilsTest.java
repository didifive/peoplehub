package it.zancanela.peoplehub.utils.test;

import it.zancanela.peoplehub.exceptions.BadRequestBodyException;
import it.zancanela.peoplehub.exceptions.PeopleHubException;
import it.zancanela.peoplehub.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DateUtilsTest {

    @Test
    void returnLocalDate() {
        LocalDate date = DateUtils.stringToLocalDate("2021-02-01");

        assertInstanceOf(LocalDate.class, date);
    }

    @Test
    void throwExceptionWhenStringIsInvalid() {
        assertThrows(PeopleHubException.class,
                () -> DateUtils.stringToLocalDate("20-02-01"));
    }

    @Test
    void throwExceptionWhenInstance() throws Exception {
        assertThrows(IllegalStateException.class,
                () -> Whitebox.invokeConstructor(DateUtils.class));
    }
}