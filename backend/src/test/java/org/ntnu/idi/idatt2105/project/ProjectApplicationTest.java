package org.ntnu.idi.idatt2105.project;

import org.junit.jupiter.api.Test;
import org.ntnu.idi.idatt2105.project.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ProjectApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;
    @MockBean
    private FileStorageService fileStorageService;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }
}

