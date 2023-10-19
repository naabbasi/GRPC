package edu.learn.grpc.examples.services.grpc.helloworld;

import edu.learn.grpc.examples.helloworld.GreeterGrpc.GreeterImplBase;
import edu.learn.grpc.examples.helloworld.HelloReply;
import edu.learn.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreeterServiceGrpc extends GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        var name = String.format("Return by server:: Hello, ", request.getName());
        var helloReply = HelloReply.newBuilder().setMessage(name).build();
        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
    }    
}
