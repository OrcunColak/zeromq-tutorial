package com.colak.pubsub.slow;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.TimeUnit;

public class SlowSubscriber {

    public static void main() throws InterruptedException {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);

            // Controls how many messages it can buffer before dropping messages.
            // Only store 1 message in the subscriber queue
            subscriber.setRcvHWM(1);

            subscriber.connect("tcp://localhost:5556");

            // Subscribe to all messages
            subscriber.subscribe("");

            while (true) {
                String message = subscriber.recvStr();
                System.out.println("SLOW Subscriber Received: " + message);

                // Slow processing
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }
}
