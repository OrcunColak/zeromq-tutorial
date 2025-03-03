package com.colak.dealer;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class DealerServer {

    public static void main() {

        try (ZContext context = new ZContext()) {
            ZMQ.Socket router = context.createSocket(SocketType.ROUTER);
            router.bind("tcp://*:5555");
            System.out.println("Server is running...");

            while (!Thread.currentThread().isInterrupted()) {
                // ROUTER requires reading the identity first
                byte[] identity = router.recv(); // Client identity
                byte[] emptyFrame = router.recv(); // Empty delimiter
                byte[] request = router.recv(); // Actual message

                String receivedMessage = new String(request, ZMQ.CHARSET);
                System.out.println("Received: " + receivedMessage + " from " + new String(identity, ZMQ.CHARSET));

                // Respond asynchronously
                String response = "Reply to: " + receivedMessage;
                router.send(identity, ZMQ.SNDMORE); // Send back to the same client
                router.send("", ZMQ.SNDMORE); // Empty frame
                router.send(response); // Response message
            }
        }
    }
}

