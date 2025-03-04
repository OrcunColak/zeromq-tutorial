package com.colak.publishsubscribe.pubsub.normal;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Subscriber {

    public static void main() {

        try (// Create a single-threaded scheduler
             ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
             ZContext context = new ZContext();
             ZMQ.Socket subscriber = context.createSocket(SocketType.SUB)) {

            subscriber.connect("tcp://localhost:5556");

            // Subscribe to all messages (empty string) or a specific topic (e.g., "Topic1")
            subscriber.subscribe("Topic1".getBytes(ZMQ.CHARSET));

            // Schedule a task every 5 seconds
            scheduler.scheduleAtFixedRate(
                    Subscriber::runScheduledTask, 0, 5, TimeUnit.SECONDS
            );

            while (true) {
                String message = subscriber.recvStr(ZMQ.NOBLOCK);
                if (message != null) {
                    System.out.println("Received: " + message);
                }
            }
        }
    }

    private static void runScheduledTask() {
        System.out.println("Running scheduled task at " + System.currentTimeMillis());
    }
}
