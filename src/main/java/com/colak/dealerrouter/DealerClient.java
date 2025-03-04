package com.colak.dealerrouter;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DealerClient {

    public static void main() {

        try (ZContext context = new ZContext()) {
            ZMQ.Socket dealer = context.createSocket(SocketType.DEALER);
            dealer.setIdentity("Client1".getBytes(ZMQ.CHARSET)); // Set unique identity
            dealer.connect("tcp://localhost:5555");

            try (ExecutorService executor = Executors.newSingleThreadExecutor()) {

                // Async receive thread
                executor.submit(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        byte[] emptyFrame = dealer.recv(); // Empty delimiter
                        byte[] response = dealer.recv(); // Actual response

                        System.out.println("Received: " + new String(response, ZMQ.CHARSET));
                    }
                });

                // Send multiple requests without waiting
                for (int i = 1; i <= 5; i++) {
                    String message = "Message " + i;
                    dealer.send("", ZMQ.SNDMORE); // Empty frame
                    dealer.send(message);
                    System.out.println("Sent: " + message);
                    Thread.sleep(1000);
                }

                // Let responses come in
                Thread.sleep(5000);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}

