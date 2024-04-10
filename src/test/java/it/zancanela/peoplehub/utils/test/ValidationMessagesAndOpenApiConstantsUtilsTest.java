package it.zancanela.peoplehub.utils.test;

import it.zancanela.peoplehub.utils.ValidationMessagesAndOpenApiConstantsUtils;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ValidationMessagesAndOpenApiConstantsUtilsTest {

    @Test
    void throwExceptionWhenInstance() throws Exception {
        assertThrows(IllegalStateException.class,
                () -> Whitebox.invokeConstructor(ValidationMessagesAndOpenApiConstantsUtils.class));
    }

}