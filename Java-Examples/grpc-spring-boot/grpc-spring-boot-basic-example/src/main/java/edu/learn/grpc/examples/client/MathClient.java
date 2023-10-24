package edu.learn.grpc.examples.client;

import edu.learn.grpc.examples.math.MathGrpc;
import edu.learn.grpc.examples.math.NumberRequest;
import edu.learn.grpc.examples.math.OperationReply;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MathClient {
    private static ManagedChannel grpcChannel;

    private MathGrpc.MathFutureStub getFutureStub() {
        if(grpcChannel == null){
            MathClient.grpcChannel = this.createGrpcChannel();
        } else if(grpcChannel.isShutdown()){
            MathClient.grpcChannel = this.createGrpcChannel();
        }

        return MathGrpc.newFutureStub(MathClient.grpcChannel);
    }

    public ManagedChannel createGrpcChannel() {
        return Grpc.newChannelBuilder("localhost:9090", InsecureChannelCredentials.create()).build();
    }

    public void shutdownGrpcChannel() throws InterruptedException {
        // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
        // resources the channel should be shut down when it will no longer be used. If it may be used
        // again leave it running.
        MathClient.grpcChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    private NumberRequest prepareNumberRequest(double number1, double number2) {
        return NumberRequest.newBuilder()
                .setNumber1(number1)
                .setNumber2(number2)
                .build();
    }

    public OperationReply add(double number1, double number2) {
        try {
            return this.getFutureStub().add(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public OperationReply subtract(double number1, double number2) {
        try {
            return this.getFutureStub().subtract(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public OperationReply multiply(double number1, double number2) {
        try {
            return this.getFutureStub().multiply(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public OperationReply divide(double number1, double number2) {
        try {
            return this.getFutureStub().divide(this.prepareNumberRequest(number1, number2)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
