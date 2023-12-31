package edu.learn.grpc.examples;

import edu.learn.grpc.examples.client.MathClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class GrpcExampleApplication {
	@Bean
	public CommandLineRunner init(MathClient mathClient) {
		return args -> {
            log.info("Add: {}", mathClient.add(5,5));
            log.info("Subtract: {}", mathClient.subtract(5,5));
            log.info("Multiply: {}", mathClient.multiply(5,5));
            log.info("Divide: {}", mathClient.divide(5,5));
			mathClient.shutdownGrpcChannel();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(GrpcExampleApplication.class, args);
	}

}
