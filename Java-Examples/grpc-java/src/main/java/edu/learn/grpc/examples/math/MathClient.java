package edu.learn.grpc.examples.math;

import io.grpc.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MathClient {
    private static final Logger logger = Logger.getLogger(MathClient.class.getName());

    private final MathGrpc.MathBlockingStub blockingStub;
    private final MathGrpc.MathFutureStub futureStub;

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    public MathClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = MathGrpc.newBlockingStub(channel);
        this.futureStub = MathGrpc.newFutureStub(channel);
    }

    /**
     * Say hello to server.
     */
    public void mathOperations() {
        logger.info("Will try to perform operations on grpc servers ...");
        NumberRequest numberRequest = NumberRequest.newBuilder()
                .setNumber1(5d)
                .setNumber2(5d)
                .build();
        OperationReply operationReply;
        try {
            operationReply = this.futureStub.add(numberRequest).get();
            logger.info("Add result: " + operationReply.getResult());

            operationReply = blockingStub.subtract(numberRequest);
            logger.info("Subtract result: " + operationReply.getResult());

            operationReply = blockingStub.multiply(numberRequest);
            logger.info("Multiply result: " + operationReply.getResult());

            operationReply = blockingStub.divide(numberRequest);
            logger.info("Divide result: " + operationReply.getResult());
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";
        // Allow passing in the user and target strings as command line arguments
        if (args.length > 0) {
            if ("--help".equals(args[0])) {
                System.err.println("Usage: [name [target]]");
                System.err.println("  name    The name you wish to be greeted by. Defaults to User");
                System.err.println("  target  The server to connect to. Defaults to " + target);
                System.exit(1);
            }
        }
        if (args.length > 1) {
            target = args[1];
        }

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        //
        // For the example we use plaintext insecure credentials to avoid needing TLS certificates. To
        // use TLS, use TlsChannelCredentials instead.
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        try {
            MathClient client = new MathClient(channel);
            client.mathOperations();
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
