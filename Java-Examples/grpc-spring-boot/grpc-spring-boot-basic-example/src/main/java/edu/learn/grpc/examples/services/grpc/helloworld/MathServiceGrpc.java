package edu.learn.grpc.examples.services.grpc.helloworld;

import edu.learn.grpc.examples.math.MathGrpc.MathImplBase;
import edu.learn.grpc.examples.math.NumberRequest;
import edu.learn.grpc.examples.math.OperationReply;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MathServiceGrpc extends MathImplBase {
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
