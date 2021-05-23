package com.hanna.sapeha.app.config;

import com.hanna.sapeha.app.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public abstract class BaseIT {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    private static final MySQLContainer mySQLContainer;

    static {
        mySQLContainer = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql:8.0"))
                .withUsername("test")
                .withPassword("1234")
                .withReuse(true);
        mySQLContainer.start();
    }

    @DynamicPropertySource
    public static void setDataSourceProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
}
