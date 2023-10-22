package edu.learn.grpcexample;

import edu.learn.grpc.examples.GrpcExampleApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootConfiguration
@SpringBootTest(classes = GrpcExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrpcTests extends AbstractIntegrationTests {
    @Test
    public void test() {
        Assertions.assertThat(true).isTrue();
    }
}
