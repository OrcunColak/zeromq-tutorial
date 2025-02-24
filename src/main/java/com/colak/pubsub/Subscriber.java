package com.colak.pubsub;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Subscriber {

    public static void main() {

        try (ZContext context = new ZContext()) {
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5556");

            // Subscribe to all messages (empty string) or a specific topic (e.g., "Topic1")
            subscriber.subscribe("Topic1".getBytes(ZMQ.CHARSET));

            while (true) {
                String message = subscriber.recvStr();
                System.out.println("Received: " + message);
            }
        }
    }
}
