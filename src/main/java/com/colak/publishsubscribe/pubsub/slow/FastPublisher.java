package com.colak.publishsubscribe.pubsub.slow;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.TimeUnit;

public class FastPublisher {

    public static void main() throws InterruptedException {

        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);

            publisher.bind("tcp://*:5556");

            int counter = 1;
            while (true) {
                String message = "Message " + counter;

                // Non-blocking send (drops messages if buffer is full)
                boolean sent = publisher.send(message);

                if (!sent) {
                    System.out.println("Dropped: " + message);
                } else {
                    System.out.println("Sent: " + message);
                }

                counter++;

                // Simulate work
                TimeUnit.MILLISECONDS.sleep(10);
            }
        }
    }
}
