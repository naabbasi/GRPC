package edu.learn.grpc.examples.math;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MathServer {
    private static final Logger logger = Logger.getLogger(MathServer.class.getName());

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new MathImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                MathServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MathServer server = new MathServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class MathImpl extends MathGrpc.MathImplBase {
        @Override
        public void add(NumberRequest request, StreamObserver<OperationReply> responseObserver) {
            double number1 = request.getNumber1();
            double number2 = request.getNumber2();
            double result = number1 + number2;
            OperationReply operationReply = OperationReply.newBuilder().setResult(result).build();
            responseObserver.onNext(operationReply);
            responseObserver.onCompleted();
        }

        @Override
        public void subtract(NumberRequest request, StreamObserver<OperationReply> responseObserver) {
            double number1 = request.getNumber1();
            double number2 = request.getNumber2();
            double result = number1 - number2;
            OperationReply operationReply = OperationReply.newBuilder().setResult(result).build();
            responseObserver.onNext(operationReply);
            responseObserver.onCompleted();
        }

        @Override
        public void multiply(NumberRequest request, StreamObserver<OperationReply> responseObserver) {
            double number1 = request.getNumber1();
            double number2 = request.getNumber2();
            double result = number1 * number2;
            OperationReply operationReply = OperationReply.newBuilder().setResult(result).build();
            responseObserver.onNext(operationReply);
            responseObserver.onCompleted();
        }

        @Override
        public void divide(NumberRequest request, StreamObserver<OperationReply> responseObserver) {
            double number1 = request.getNumber1();
            double number2 = request.getNumber2();
            double result = number1 / number2;
            OperationReply operationReply = OperationReply.newBuilder().setResult(result).build();
            responseObserver.onNext(operationReply);
            responseObserver.onCompleted();
        }
    }
}
