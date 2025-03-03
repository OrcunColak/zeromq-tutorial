package com.colak.pubsub;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.TimeUnit;

public class Publisher {

    public static void main() throws InterruptedException {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5556");

            while (true) {
                String message = "Topic1 Hello, Subscribers!";
                System.out.println("Publishing: " + message);
                publisher.send(message, 0);

                // Simulate work
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

}
