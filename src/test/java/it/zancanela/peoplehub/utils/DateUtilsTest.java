package it.zancanela.peoplehub.utils;

import it.zancanela.peoplehub.exceptions.BadRequestBodyException;
import org.junit.jupiter.api.Test;
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
        assertThrows(BadRequestBodyException.class,
                () -> DateUtils.stringToLocalDate("20-02-01"));
    }

}