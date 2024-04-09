package it.zancanela.peoplehub.utils;

import org.opentest4j.MultipleFailuresError;

import static org.junit.jupiter.api.Assertions.*;

public class Assertions {

    public static void assertThrowsExceptionWithCorrectMessage(Runnable method
            , Class<?> expectedClass
            , String expectedMessage) throws MultipleFailuresError {

        try {
            method.run();
            fail("Function run without throw exception");
        }catch (Exception e) {
            assertAll(
                    "Check if throw exception with correct message"
                    , () -> assertEquals(expectedClass, e.getClass())
                    , () -> assertEquals(expectedMessage, e.getMessage())
            );
        }

    }

}