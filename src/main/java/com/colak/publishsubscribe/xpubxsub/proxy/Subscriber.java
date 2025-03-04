package com.colak.publishsubscribe.xpubxsub.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Subscriber {

    public static void main() {
        try (ZContext context = new ZContext();
             ZMQ.Socket subscriber = context.createSocket(SocketType.SUB)) {

            subscriber.connect("tcp://localhost:5556"); // Connect to XPUB
            subscriber.subscribe("TopicA".getBytes()); // Subscribe to "TopicA"

            while (true) {
                String message = subscriber.recvStr();
                System.out.println("Received: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
