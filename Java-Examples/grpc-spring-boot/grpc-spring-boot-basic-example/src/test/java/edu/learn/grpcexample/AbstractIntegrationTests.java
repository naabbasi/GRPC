package edu.learn.grpcexample;

import edu.learn.grpc.examples.GrpcExampleApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Supplier;

@Slf4j
@SpringBootConfiguration
@SpringBootTest(classes = GrpcExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTests {
    @Container
    protected static PostgreSQLContainer<?> POSTGRESQL_TEST_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2-alpine"))
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("Password1")
            .withExposedPorts(5432);
    //.withCopyFileToContainer(MountableFile.forClasspathResource("/schema.sql"), "/docker-entrypoint-initdb.d/") : null;

    @LocalServerPort
    static int localServerPort;

    @DynamicPropertySource
    public static void setupEnvironment(DynamicPropertyRegistry registry) {
        Startables.deepStart(POSTGRESQL_TEST_CONTAINER).join();

        log.info("Local Server Port: {}", localServerPort);
        Supplier<Object> URL = () -> String.format("jdbc:postgresql://%s:%d/%s",
                POSTGRESQL_TEST_CONTAINER.getHost(),
                POSTGRESQL_TEST_CONTAINER.getFirstMappedPort(),
                POSTGRESQL_TEST_CONTAINER.getDatabaseName());

        Supplier<Object> DB_TYPE = () -> "postgresql";
        registry.add("spring.datasource.url", URL);
        registry.add("spring.datasource.username", POSTGRESQL_TEST_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_TEST_CONTAINER::getPassword);
        registry.add("spring.jpa.database", DB_TYPE);
    }
}