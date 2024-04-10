package it.zancanela.peoplehub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PeopleHubApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        PeopleHubApplication.main(new String[]{});
        assertNotNull(context);
    }

}
