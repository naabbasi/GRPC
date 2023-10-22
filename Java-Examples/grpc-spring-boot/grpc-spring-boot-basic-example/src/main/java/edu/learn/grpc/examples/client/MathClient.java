package edu.learn.grpc.examples.client;

import edu.learn.grpc.examples.math.MathGrpc;
import edu.learn.grpc.examples.math.NumberRequest;
import edu.learn.grpc.examples.math.OperationReply;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Slf4j
@Component
public class MathClient {
    private final MathGrpc.MathFutureStub futureStub;
    public MathClient(ManagedChannel getGrpcChannel) throws InterruptedException {
        try {
            this.futureStub = MathGrpc.newFutureStub(getGrpcChannel);
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            getGrpcChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    private NumberRequest prepareNumberRequest(double number1, double number2) {
        return NumberRequest.newBuilder()
                .setNumber1(number1)
                .setNumber2(number2)
                .build();
    }

    public OperationReply add(double number1, double number2) {
        try {
            return this.futureStub.add(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public OperationReply subtract(double number1, double number2) {
        try {
            return this.futureStub.subtract(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public OperationReply multiply(double number1, double number2) {
        try {
            return this.futureStub.multiply(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public OperationReply divide(double number1, double number2) {
        try {
            return this.futureStub.divide(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
